<script>
  import { onMount } from 'svelte';
  import L from 'leaflet';
  import 'leaflet/dist/leaflet.css';
  import 'leaflet-draw';
  import 'leaflet-draw/dist/leaflet.draw.css';
  import './map.css';

  let map;
  let currentPolyline = null;
  let currentLatLngs = [];
  let isDrawing = false;
  let isMouseDown = false;
  let isLocked = false;
  let showControls = true;
  let northWest = null;
  let southEast = null;
  let latLngMessage = '';

  onMount(() => {
    map = L.map('map').setView([36.3593, 127.3416], 16);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 19,
      attribution: '© OpenStreetMap'
    }).addTo(map);
    
    setTimeout(() => {
      map.invalidateSize(); 
    }, 100);
  });

  function lockMap() {
    isLocked = true;
    isDrawing = true;
    showControls = false;
    map.dragging.disable();
    map.scrollWheelZoom.disable();
    map.touchZoom.disable();
    map.doubleClickZoom.disable();
    map.boxZoom.disable();

    const bounds = map.getBounds();
    northWest = bounds.getNorthWest();
    southEast = bounds.getSouthEast();
  }

  function unlockMap() {
    isLocked = false;
    isDrawing = false;
    showControls = true;
    map.dragging.enable();
    map.scrollWheelZoom.enable();
    map.touchZoom.enable();
    map.doubleClickZoom.enable();
    map.boxZoom.enable();
  }

  function startDrawing() {
    isDrawing = true;
    lockMap();
  }

  function stopDrawing() {
    if (isDrawing) {
      isDrawing = false;
      latLngMessage = '여기에 문구가 나타납니다';
      showControls = false;
    }
  }

  function onMouseMove(e) {
    if (isDrawing && isMouseDown) {
      if (!currentPolyline) {
        currentLatLngs = [e.latlng];
        currentPolyline = new L.Polyline(currentLatLngs, { color: 'blue' }).addTo(map);
      } else {
        currentLatLngs.push(e.latlng);
        currentPolyline.setLatLngs(currentLatLngs);
      }
    }
  }

  function onMouseDown() {
    if (isDrawing) {
      isMouseDown = true;
    }
  }

  function onMouseUp() {
    if (isDrawing && isMouseDown) {
      isMouseDown = false;
      currentPolyline.setLatLngs(currentLatLngs);
      currentPolyline.redraw();
      stopDrawing();
    }
  }

  function redraw() {
    if (currentPolyline) {
      map.removeLayer(currentPolyline);
      currentPolyline = null;
    }
    currentLatLngs = [];
    unlockMap();
  }

  function next() {
    const drawForm = {
      PositionLIst: currentLatLngs.map(latlng => ({
        lat: latlng.lat,
        lng: latlng.lng
      })),
      leftLat:northWest.lat,
      leftLng:northWest.lng,
      rightLat:southEast.lat,
      rightLng:southEast.lng,
    };
    console.log('결과 객체:', drawForm);
  }

  onMount(() => {
    map.on('mousemove', onMouseMove);
    map.on('mousedown', onMouseDown);
    map.on('mouseup', onMouseUp);
  });
</script>

<div id="controls">
  {#if showControls}
    <button on:click={startDrawing}>고정하기</button>
  {/if}

  {#if !showControls}
    <button on:click={redraw}>다시그리기</button>
    <button on:click={next}>다음</button>
  {/if}
</div>

<div id="map"></div>

<div>
  <h2>좌상단: {northWest ? `${northWest.lat}, ${northWest.lng}` : 'N/A'}</h2>
  <h2>우하단: {southEast ? `${southEast.lat}, ${southEast.lng}` : 'N/A'}</h2>
  <p>{latLngMessage}</p>
</div>

<style>
  #map { 
    height: 60vh;
    width: 85vw;
    max-width: 100%;
  }
  #controls { 
    margin: 10px; 
  }
  button {
    margin: 5px;
  }
</style>
