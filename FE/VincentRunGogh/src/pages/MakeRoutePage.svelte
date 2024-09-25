<script lang="ts">
  import { onMount } from 'svelte';
  import L, { latLng } from 'leaflet';
  import 'leaflet/dist/leaflet.css';
  import 'leaflet-draw';
  import 'leaflet-draw/dist/leaflet.draw.css';
  import Swal from 'sweetalert2';
  import { push } from 'svelte-spa-router';
  import BackButton from '@/components/buttons/BackButton.svelte';
  import { Button, Input } from 'flowbite-svelte';
  // Chapter 2
  import html2canvas from 'html2canvas';
  import { Canvg, SVGElement } from 'canvg';
  import { makeRoute, sendArtLine } from '@/api/makeRouteApi';

  //진행 상태 변수
  let isChapterOne = true;

  let map: L.Map;
  let currentPolyline: L.Polyline | null = null;
  let routePolyline: L.Polyline | null = null;
  let currentLatLngs: L.LatLng[] = [];
  let isDrawing: boolean = false;
  let isMouseDown: boolean = false;
  let isTouchDown: boolean = false;
  let isLocked: boolean = false;
  let isFixed: boolean = false;
  let showControls: boolean = true;
  let northWest: L.LatLng | null = null;
  let southEast: L.LatLng | null = null;
  let drawForm: Record<string, any> = {};
  let skip: number = 0;
  let shortenLatLngs: { lat: number; lng: number }[] = [];
  let latLngMessageList: string[] = [
    '지도를 움직여 원하는 장소를 고르신 후 고정해주세요',
    '지도 내에서 한붓그리기로 원하는 그림을 표현해보세요!',
    '위 아트를 기반으로 루트를 생성할까요?',
  ];
  let latLngMessage: string = latLngMessageList[0];

  //초기 렌더링
  onMount(() => {
    //지도 생성
    map = L.map('map', { zoomControl: false }).setView([36.3593, 127.3416], 16);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 19,
      attribution: '© OpenStreetMap',
      crossOrigin: 'anonymous',
    }).addTo(map);

    //지도 재계산
    setTimeout(() => {
      map.invalidateSize();
    }, 100);
  });

  // Chapter 1 -----------------------------------------------/

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
  function unlockMap(condition: boolean) {
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
    latLngMessage = latLngMessageList[1];
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
      latLngMessage = latLngMessageList[2];
    }
  }

  //마우스 움직일 때

  function onMouseMove(e) {
    if ((isDrawing && isMouseDown) || isTouchDown) {
      if (!currentPolyline) {
        currentLatLngs = [e.latlng];
        currentPolyline = new L.Polyline(currentLatLngs, {
          color: 'blue',
        }).addTo(map);
      } else {
        currentLatLngs.push(e.latlng);
        currentPolyline.setLatLngs(currentLatLngs);
      }
    }
  }

  // 그리기 종료 시 저장
  function stopAndSavePolyline() {
    stopDrawing();
    isFixed = false;
    unlockMap(false);
    for (let v in currentLatLngs) {
      if (skip % 5 === 0) {
        let dot = {
          lat: currentLatLngs[v].lat,
          lng: currentLatLngs[v].lng,
        };
        shortenLatLngs.push(dot);
      }
      skip++;
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
      stopAndSavePolyline();
    }
  }

  // 터치 시작할 때
  function onTouchStart() {
    if (isDrawing) {
      isTouchDown = true;
    }
  }

  // 터치 움직일 때
  function onTouchMove(e) {
    const touch = e.touches[0]; // 터치 위치 가져오기
    const latlng = map.mouseEventToLatLng(touch); // 터치 좌표를 지도 좌표로 변환
    onMouseMove({ latlng }); // 마우스 움직임과 동일하게 처리
  }

  // 터치 종료할 때
  function onTouchEnd() {
    if (isDrawing && isTouchDown) {
      isTouchDown = false;
      stopAndSavePolyline(); // 중복 로직 함수로 분리
    }
  }

  //다시그리기
  function redraw() {
    if (currentPolyline) {
      map.removeLayer(currentPolyline);
      currentPolyline = null;
    }
    if (routePolyline) {
      map.removeLayer(routePolyline);
      routePolyline = null;
    }
    currentLatLngs = [];
    latLngMessage = latLngMessageList[0];
    showControls = true;
    isChapterOne = true;
    skip = 0;
    shortenLatLngs = [];
  }

  //다음 - 결과물 객체
  async function next() {
    drawForm = {
      positionList: shortenLatLngs,
      leftLat: northWest.lat,
      leftLng: northWest.lng,
      rightLat: southEast.lat,
      rightLng: southEast.lng,
    };
    Swal.fire({
      title: '루트를 생성중입니다...',
      showConfirmButton: false,
      didOpen: async () => {
        // 루트생성 API 요청
        try {
          let routeResponse = await sendArtLine(drawForm);
          routePolyline = new L.Polyline(routeResponse.data.positionList, {
            color: 'red',
          }).addTo(map);
          Swal.close();
        } catch (error) {
          {
            throw error;
          }
        }
      },
    }).then((result) => {
      // 지도에 받은거 그리기
      isChapterOne = false;
    });
  }

  onMount(() => {
    map.on('mousemove', onMouseMove);
    map.on('mousedown', onMouseDown);
    map.on('mouseup', onMouseUp);
    map.getContainer().addEventListener('touchstart', onTouchStart, { passive: false });
    map.getContainer().addEventListener('touchmove', onTouchMove, { passive: false });
    map.getContainer().addEventListener('touchend', onTouchEnd, { passive: false });
  });

  // Chapter 2 -----------------------------------------------/

  let isConfirm: boolean = false;
  let isSubmit: boolean = false;
  let inputName = '';
  let routeInfo: {
    routeId: number;
    artImage: string;
    distance: number;
    predictTime: number;
  } | null = null;
  // 루트 확인후 진행
  function routeConfirm() {
    isConfirm = true;
  }

  //캡쳐 함수

  //백 전송용 이미지정보 변수
  let finalImage: string | null = null;

  function mapCapture(target: string) {
    map.invalidateSize();
    return new Promise(async (resolve, reject) => {
      // 사용 변수 지정
      let svgData = null;
      let mapData = null;
      let svgSizeX = null;
      let svgSizeY = null;

      // SVG 캡쳐
      const svg: any = document.querySelector(`${target} svg`); // SVG를 선택합니다.
      // SVG 사이즈
      svgSizeX = svg.width.baseVal.value;
      svgSizeY = svg.height.baseVal.value;

      const svgString = new XMLSerializer().serializeToString(svg);

      // canvas 및 context 생성
      const svgCanvas = document.createElement('canvas');
      const svgCtx: any = svgCanvas.getContext('2d');

      // Canvg를 이용해 SVG를 캔버스에 그립니다
      const canvg = await Canvg.fromString(svgCtx, svgString);
      await canvg.render(); // 렌더링 완료될 때까지 기다림

      // 캔버스의 데이터를 base64로 변환
      svgData = svgCanvas.toDataURL('image/png');

      // svg 숨기기
      svg.style.display = 'none';

      // 지도 캡쳐
      const mapCanvas = await html2canvas(document.querySelector(target), {
        useCORS: true,
        scale: 1,
      });
      mapData = mapCanvas.toDataURL('image/png');

      // svg 다시보이기
      if (svg) {
        svg.style.display = '';
      }

      const finalCanvas = document.createElement('canvas');
      finalCanvas.width = 350;
      finalCanvas.height = 350;

      const ctx = finalCanvas.getContext('2d');

      // 지도, svg 모두 불러오기

      const mapImg = new Image();
      const svgImg = new Image();
      mapImg.src = mapData;
      svgImg.src = svgData;

      mapImg.onload = () => {
        svgImg.onload = () => {
          //중심 기준 자르기
          const cropX = (mapCanvas.width - 350) / 2;
          const cropY = (mapCanvas.height - 350) / 2;
          const svgCropX = (svgSizeX - 350) / 2;
          const svgCropY = (svgSizeY - 350) / 2;

          //지도 그리기
          ctx.drawImage(mapImg, cropX, cropY, 350, 350, 0, 0, 350, 350);
          ctx.drawImage(svgImg, svgCropX, svgCropY, 350, 350, 0, 0, 350, 350);

          //결과
          finalImage = finalCanvas.toDataURL('image/png');
          document.querySelector('#final').src = finalImage;
        };
      };
      resolve(finalImage);
    });
  }

  // 이름 확인 -> 사진찍고 저장하고 마무리
  async function nameConfirm() {
    // 캡쳐하기
    isSubmit = true;
    mapCapture('#map')
      .then(async () => {
        console.log(finalImage);
        await submitRoute();
      })
      .catch((error) => {
        {
          throw error;
        }
      });
  }
  // 저장하기

  async function submitRoute() {
    let makeRouteForm = {
      title: inputName,
      artImage: finalImage.split(',')[1],
    };

    try {
      let responseRoute = await makeRoute(makeRouteForm);
      routeInfo = responseRoute.data;
    } catch (error) {
      {
        throw error;
      }
    }
  }
  // 마무리하기
</script>

<div id="makeroute-header" class="flex justify-center items-center">
  <BackButton />
  <h2>루트 생성</h2>
</div>
<div id="make-route">
  {#if !isSubmit}
    <div id="map"></div>
  {:else}
    <img id="final" src="" alt="" />
  {/if}
  <!-- 첫 제출 전까지 -->
  <div id="makeroute-footer" class="flex flex-col items-center justify-center">
    {#if isChapterOne}
      {#if isFixed}
        <div
          class="fixed left-1/2 transform -translate-x-1/2 text-red-500 font-bold bg-yellow-300 px-2 py-1"
          style="top: 12vh;"
        >
          지도가 고정되었습니다.
        </div>
      {/if}
      <div>
        <p>{latLngMessage}</p>
      </div>

      <div id="controls">
        {#if showControls && !isFixed}
          <Button on:click={startDrawing}>고정하기</Button>
        {/if}
        {#if showControls && isFixed}
          <Button on:click={returnMap}>고정해제</Button>
        {/if}

        {#if !showControls}
          <Button on:click={redraw}>다시그리기</Button>
          <Button on:click={next}>다음</Button>
        {/if}
      </div>
    {/if}
    <!-- 제출 후 -->
    {#if !isChapterOne}
      <div>
        {#if !isConfirm && !isSubmit}
          <p>위 루트로 진행하시겠습니까?</p>
          <Button on:click={redraw}>다시만들기</Button>
          <Button on:click={routeConfirm}>다음</Button>
        {/if}
        {#if isConfirm && !isSubmit}
          <label for="route-name">루트의 이름을 지어주세요!</label><br />
          <Input bind:value={inputName} type="text" name="route-name" id="route-name" />
          <Button on:click={nameConfirm}>다음</Button>
        {/if}
      </div>
      {#if isSubmit && routeInfo}
        <h3>{inputName}</h3>
        <p>{routeInfo.distance} / {routeInfo.predictTime}</p>
        <Button on:click={() => push('/')}>홈으로</Button>
      {/if}
    {/if}
  </div>
</div>

<style>
  #makeroute-header {
    z-index: 3000;
    position: fixed;
    height: 10vh;
    width: 100%;
    background: linear-gradient(
      to bottom,
      rgba(255, 255, 255, 1) 80%,
      rgba(255, 255, 255, 0.6) 90%,
      rgba(255, 255, 255, 0) 100%
    );
  }

  #make-route {
    display: flex;
    flex-direction: column;
    place-items: center;
  }

  #map {
    height: 100vh;
    width: 100vw;
    max-width: 100%;
    pointer-events: auto;
    touch-action: auto;
  }

  #controls {
    margin: 10px;
  }

  #makeroute-footer {
    z-index: 3000;
    position: fixed;
    bottom: 0px;
    height: 20vh;
    width: 100%;
    background: linear-gradient(
      to top,
      rgba(255, 255, 255, 1) 80%,
      rgba(255, 255, 255, 0.6) 90%,
      rgba(255, 255, 255, 0) 100%
    );
  }

  #final {
    padding-top: 20vh;
  }
</style>
