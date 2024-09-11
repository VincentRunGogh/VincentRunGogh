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
  let isFixed = false;
  let showControls = true;
  let northWest = null;
  let southEast = null;
  let latLngMessage = '';

  //초기 렌더링
  onMount(() => {
    //지도 생성
    map = L.map('map').setView([36.3593, 127.3416], 16);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 19,
      attribution: '© OpenStreetMap'
    }).addTo(map);
    //지도 재계산
    setTimeout(() => {
      map.invalidateSize(); 
    }, 100);
  });

  //지도 잠그기
  function lockMap() {
    isLocked = true;
    isDrawing = true;
    map.dragging.disable();
    map.scrollWheelZoom.disable();
    map.touchZoom.disable();
    map.doubleClickZoom.disable();
    map.boxZoom.disable();

    const bounds = map.getBounds();
    northWest = bounds.getNorthWest();
    southEast = bounds.getSouthEast();
  }

  //지도 잠금 풀기
  function unlockMap(condition) {
    isLocked = false;
    isDrawing = false;
    
    showControls = condition;
    map.dragging.enable();
    map.scrollWheelZoom.enable();
    map.touchZoom.enable();
    map.doubleClickZoom.enable();
    map.boxZoom.enable();
  }

  //그리기 시작
  function startDrawing() {
    isDrawing = true;
    isFixed = true;
    lockMap();
  }

  //고정해제
  function returnMap() {
    isDrawing = false;
    isFixed = false;
    unlockMap(true);
  }

  //그리기 종료
  function stopDrawing() {
    if (isDrawing) {
      isDrawing = false;
      latLngMessage = '위 아트로 진행하시겠습니까?';
    }
  }

  //마우스 움직일 때
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

  // 클릭했을 때
  function onMouseDown() {
    if (isDrawing) {
      isMouseDown = true;
    }
  }
  // 마우스 클릭 뗐을 때
  function onMouseUp() {
    if (isDrawing && isMouseDown) {
      isMouseDown = false;
      currentPolyline.setLatLngs(currentLatLngs);
      currentPolyline.redraw();
      stopDrawing();
      isFixed = false;
      unlockMap(false);
    }
  }

  //다시그리기
  function redraw() {
    if (currentPolyline) {
      map.removeLayer(currentPolyline);
      currentPolyline = null;
    }
    currentLatLngs = [];
    latLngMessage = '';
    showControls = true;
  }

  //다음 - 결과물 객체
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
{#if isFixed}
  <p>지도가 고정되었습니다.</p>
{/if}

<div id="map"></div>

<div>
  <p>{latLngMessage}</p>
</div>

<div id="controls">
  {#if showControls && !isFixed}
    <button on:click={startDrawing}>고정하기</button>
  {/if}
  {#if showControls && isFixed}
    <button on:click={returnMap}>고정해제</button>
  {/if}

  {#if !showControls}
    <button on:click={redraw}>다시그리기</button>
    <button on:click={next}>다음</button>
  {/if}
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
