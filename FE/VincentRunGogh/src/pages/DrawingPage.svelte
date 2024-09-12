<script>
  import { onMount } from 'svelte';

  import L from 'leaflet';
  import { MapToolbar, MapGUI } from '@map';
  import currPosSvg from '@/assets/svg/currPos.svg';

  let currPos;
  let map;
  let time; //현재시간
  let elapsedTime = 0; // 경과 시간 (초 단위)
  let timerIntervalId; // 1초마다 타이머 업데이트하는 setInterval의 ID
  let trackingIntervalId; // 10초마다 위치를 추적하는 setInterval의 ID  let trackingActive = false; // 트래킹이 활성화되어 있는지 확인하는 플래그
  let startPos;
  let startTime;

  let isLockScreen = false; //화면 잠금 여부

  const posList = [
    [36.3528192, 127.3102336],
    [36.35, 127.31],
    [36.36, 127.3102336],
    [36.37, 127.3102336],
  ];

  function createMap() {
    let m = L.map('map', { preferCanvas: true });
    m.locate({ setView: true, maxZoom: 16 });

    L.tileLayer('https://{s}.basemaps.cartocdn.com/rastertiles/voyager/{z}/{x}/{y}{r}.png', {
      attribution: `&copy;<a href="https://www.openstreetmap.org/copyright" target="_blank">OpenStreetMap</a>,
	        &copy;<a href="https://carto.com/attributions" target="_blank">CARTO</a>`,
      subdomains: 'abcd',
      // maxZoom: 16,
      // minZoom: 13,
    }).addTo(m);

    return m;
  }
  let toolbar = L.control({ position: 'topright' });
  let toolbarComponent;
  toolbar.onAdd = (map) => {
    let div = L.DomUtil.create('div');
    toolbarComponent = new MapToolbar({
      target: div,
      props: {},
    });

    toolbarComponent.$on('click-reset', () => {
      map.setView(currPos, 5, { animate: true });
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
  let guiComponent;
  gui.onAdd = (map) => {
    let div = L.DomUtil.create('div');
    guiComponent = new MapGUI({
      target: div,
      props: {},
    });

    guiComponent.$on('click-pause', ({ detail }) => {
      toggleTracking(detail);
    });
    guiComponent.$on('click-mute', ({ detail }) => {});
    guiComponent.$on('click-lockScreen', ({ detail }) => {
      isLockScreen = detail;
    });

    return div;
  };

  gui.onRemove = () => {
    if (guiComponent) {
      guiComponent.$destroy();
      guiComponent = null;
    }
  };

  let markers = new Map();

  function markerIcon() {
    let html = `<div class="map-marker"><img src=${currPosSvg}></div></div>`;
    return L.divIcon({
      html,
      className: 'map-marker',
    });
  }

  function createMarker(loc) {
    let icon = markerIcon();
    let marker = L.marker(loc, { icon });
    return marker;
  }

  function createLines() {
    return L.polyline(posList, { color: '#E4E', opacity: 0.5 });
  }

  let markerLayers;
  let lineLayers;
  function mapAction() {
    map = createMap();
    toolbar.addTo(map);
    gui.addTo(map);
    lineLayers = createLines();
    lineLayers.addTo(map);

    return {
      destroy: () => {
        map.remove();
        gui.remove();
        map = null;
      },
    };
  }

  function resizeMap() {
    if (map) {
      map.invalidateSize();
    }
  }

  function toggleTracking(trackingActive) {
    // 트래킹이 활성화되지 않은 경우 (트래킹 처음 시작 또는 재개 시)
    if (!trackingActive) {
      // 지도에서 현재 위치 찾기
      map.locate({ setView: true, maxZoom: 16 });

      map.on('locationfound', (e) => {
        const { lat, lng } = e.latlng;
        console.log(`Current position: ${lat}, ${lng}`);

        // 최초 위치 설정
        if (!startPos) {
          startPos = { lat, lng };
          console.log(`Starting position: ${startPos.lat}, ${startPos.lng}`);
        }

        posList.push({ lat, lng }); // 위치 목록에 추가
        L.marker([lat, lng]).addTo(map); // 지도에 마커 추가
      });

      map.on('locationerror', (e) => {
        console.error(e.message);
      });

      // 시작 시간을 설정, 새로 시작할 때마다 갱신
      if (!startTime) {
        startTime = new Date();
      }

      // 1초마다 경과 시간을 업데이트하는 타이머
      timerIntervalId = setInterval(() => {
        elapsedTime++;
        console.log(`Elapsed time: ${elapsedTime} seconds`);
      }, 1000); // 1초마다 실행

      // 10초마다 위치를 추적하는 setInterval
      trackingIntervalId = setInterval(() => {
        map.locate(); // 위치 추적
        console.log('Location updated');
      }, 10000); // 10초마다 실행

      trackingActive = true; // 트래킹 활성화 상태
      console.log('Tracking started or resumed');
    } else {
      // 트래킹이 활성화된 상태일 때 (일시정지)
      clearInterval(timerIntervalId); // 타이머 중지
      clearInterval(trackingIntervalId); // 위치 추적 중지
      timerIntervalId = null;
      trackingIntervalId = null;
      trackingActive = false; // 트래킹 비활성화 상태
      console.log('Tracking paused');
    }
  }
  function clickLockScreen() {
    isLockScreen = !isLockScreen; // 잠금 상태를 반전
    console.log('Lock Screen:', isLockScreen);
  }
  onMount(() => {
    mapAction();
    toggleTracking(); // 트래킹 시작 또는 재개
  });
</script>

<svelte:window on:resize={resizeMap} />

<div class="map-container" style="height:100vh; width:100%;">
  <div id="map" class="map" style="height:100%;width:100%" />
</div>
<div class="lock-overlay" class:is-active={isLockScreen}>
  <div class="overlay-content">
    <!-- 이곳에 잠금 상태에서 보여질 추가 UI 요소를 넣을 수 있습니다. -->
    <p>Screen is locked. Only the button below can unlock it.</p>

    <!-- 오버레이 상에서 클릭이 가능한 버튼 (잠금 해제 버튼) -->
    <button on:click={clickLockScreen} class="unlock-btn"> Unlock Screen </button>
  </div>
</div>

<style>
  /* Lock Overlay: 화면 잠금을 위한 오버레이 */
  .lock-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background: rgba(0, 0, 0, 0.3); /* 투명 오버레이 */
    display: none;
    justify-content: center;
    align-items: center;
    z-index: 9999;
  }

  /* 잠금 상태에서 오버레이 활성화 */
  .lock-overlay.is-active {
    display: flex;
    pointer-events: auto;
  }

  /* 오버레이 위의 컨텐츠 스타일 */
  .overlay-content {
    background: rgba(255, 255, 255, 0.9);
    padding: 1rem 2rem;
    border-radius: 0.5rem;
    text-align: center;
  }

  /* 잠금 해제 버튼 */
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

  /* 기본적으로는 클릭 이벤트를 차단 */
  .lock-overlay {
    pointer-events: none;
  }

  /* 잠금 해제 버튼만 클릭 가능하도록 설정 */
  .lock-overlay.is-active .unlock-btn {
    pointer-events: auto;
  }

  /* 잠금/해제 버튼 */
  .lock-btn {
    background-color: purple;
    color: white;
    border: none;
    padding: 0.5rem 1rem;
    font-size: 1rem;
    cursor: pointer;
    border-radius: 0.25rem;
  }
</style>
