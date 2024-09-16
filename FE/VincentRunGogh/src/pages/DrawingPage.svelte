<script lang="ts">
  import { onMount } from 'svelte';
  import L, { Map as LeafletMap, Marker, Polyline } from 'leaflet';
  import Swal from 'sweetalert2';
  import { get } from 'svelte/store';

  import { MapToolbar, MapGUI } from '@map';
  import currPosSvg from '@/assets/svg/currPos.svg';
  import { isLockScreen, isPause } from '@/stores/drawingStore';
  import type { LatLng, LatLngExpression } from 'leaflet';  // 타입은 type-only import로 가져옴

  $: $isLockScreen = $isLockScreen;
  $: $isPause = $isPause;

  let currPos: LatLng | null = null;
  let map: LeafletMap | null = null;
  let time: Date | null = null; // 현재시간
  let elapsedTime: number = 0; // 경과 시간 (초 단위)
  let timerIntervalId: number | null = null; // 1초마다 타이머 업데이트하는 setInterval의 ID
  let trackingIntervalId: number | null = null; // 10초마다 위치를 추적하는 setInterval의 ID
  let startPos: LatLng | null = null;
  let startTime: Date | null = null;

  const posList: LatLngExpression[] = [
    [36.3528192, 127.3102336],
    [36.35, 127.31],
    [36.36, 127.3102336],
    [36.37, 127.3102336],
  ];

  function createMap(): LeafletMap {
    const m = L.map('map', { preferCanvas: true });
    m.locate({ setView: true, maxZoom: 16 });

    L.tileLayer('https://{s}.basemaps.cartocdn.com/rastertiles/voyager/{z}/{x}/{y}{r}.png', {
      attribution: `&copy;<a href="https://www.openstreetmap.org/copyright" target="_blank">OpenStreetMap</a>,
      &copy;<a href="https://carto.com/attributions" target="_blank">CARTO</a>`,
      subdomains: 'abcd',
    }).addTo(m);

    return m;
  }

  let toolbar = L.control({ position: 'topright' });
  let toolbarComponent: MapToolbar | null = null;
  toolbar.onAdd = (map:LeafletMap) => {
    const div = L.DomUtil.create('div');
    toolbarComponent = new MapToolbar({
      target: div,
      props: {},
    });

    toolbarComponent.$on('click-reset', () => {
      if (currPos) map.setView(currPos, 16, { animate: true });
    });

    return div;
  };

  toolbar.onRemove = () => {
    if (toolbarComponent) {
      toolbarComponent.$destroy();
      toolbarComponent = null;
    }
  };

  let gui = L.control({ position: 'bottomleft' });
  let guiComponent: MapGUI | null = null;
  gui.onAdd = (map) => {
    const div = L.DomUtil.create('div');
    guiComponent = new MapGUI({
      target: div,
      props: { isLockScreen },
    });

    guiComponent.$on('click-pause', () => {
      isPause.update((value) => !value);
      toggleTracking();
    });

    guiComponent.$on('click-lockScreen', () => {
      isLockScreen.update((value) => !value); // store 값을 업데이트
    });

    return div;
  };

  gui.onRemove = () => {
    if (guiComponent) {
      guiComponent.$destroy();
      guiComponent = null;
    }
  };

  let markers = new Map<string, Marker>();

  function markerIcon(): L.DivIcon {
    const html = `<div class="map-marker"><img src=${currPosSvg}/></div>`;
    return L.divIcon({
      html,
      className: 'map-marker',
    });
  }

  function createMarker(loc: LatLngExpression): Marker {
    const icon = markerIcon();
    return L.marker(loc, { icon });
  }

  function createLines(): Polyline {
    return L.polyline(posList, { color: '#E4E', opacity: 0.5 });
  }

  let markerLayers: Marker[] = [];
  let lineLayers: Polyline;

  function mapAction() {
    map = createMap();
    toolbar.addTo(map);
    gui.addTo(map);
    lineLayers = createLines();
    lineLayers.addTo(map);

    return {
      destroy: () => {
        if (map) {
          map.remove();
          gui.remove();
          map = null;
        }
      },
    };
  }

  function resizeMap() {
    if (map) {
      map.invalidateSize();
    }
  }

  function toggleTracking() {
    const trackingActive = get(isPause);
    if (!trackingActive) {
      map?.locate({ setView: true, maxZoom: 16 });

      map?.on('locationfound', (e: L.LocationEvent) => {
        const { lat, lng } = e.latlng;
        currPos = new L.LatLng(lat, lng);

        if (!startPos) {
          startPos = new L.LatLng(lat, lng);
        }

        posList.push([lat, lng]);
        L.marker([lat, lng]).addTo(map);
      });

      map?.on('locationerror', (e: L.ErrorEvent) => {
        console.error(e.message);
      });

      if (!startTime) {
        startTime = new Date();
      }

      timerIntervalId = window.setInterval(() => {
        elapsedTime++;
      }, 1000);

      trackingIntervalId = window.setInterval(() => {
        map?.locate();
      }, 10000);
    } else {
      if (timerIntervalId) {
        clearInterval(timerIntervalId);
        timerIntervalId = null;
      }

      if (trackingIntervalId) {
        clearInterval(trackingIntervalId);
        trackingIntervalId = null;
      }
    }
  }

  function clickLockScreen() {
    $isLockScreen = !$isLockScreen;
  }

  onMount(() => {
    mapAction();
    toggleTracking();
  });
</script>

<svelte:window on:resize={resizeMap} />

<div class="map-container" style="height:100vh; width:100%;">
  <div id="map" class="map" style="height:100%;width:100%;" />
</div>
<div class="lock-overlay" class:is-active={$isLockScreen}>
  <div class="overlay-content">
    <button on:click={clickLockScreen} class="unlock-btn"> </button>
  </div>
</div>

<style>
  /* 지도와 버튼을 포함한 컨테이너 */

  .lock-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background: rgba(0, 0, 0, 0.5);
    display: none;
    justify-content: center;
    align-items: center;
    z-index: 9999;
    pointer-events: none;
  }

  .lock-overlay.is-active {
    display: flex;
    pointer-events: auto;
  }

  .overlay-content {
    background: rgba(255, 255, 255, 0.9);
    padding: 1rem 2rem;
    border-radius: 0.5rem;
    text-align: center;
  }

  .unlock-btn {
    background-color: purple;
    color: white;
    border: none;
    padding: 0.5rem 1rem;
    font-size: 1rem;
    cursor: pointer;
    border-radius: 0.25rem;
    margin-top: 1rem;
  }
</style>
