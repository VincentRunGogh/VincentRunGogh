<script lang="ts">
  import { Card } from 'flowbite-svelte';

  export let title: string;
  export let distance: number;
  export let time: number;
  export let image: string;
  export let isRoute: boolean;
</script>

<div id="route-result">
  <h2 id="result-title">
    {#if isRoute}
      루트 저장 완료!
    {:else}
      드로잉 저장 완료!
    {/if}
  </h2>
  <Card>
    <div id="canvas-img">
      <!-- SVG 프레임 -->
      <svg
        id="frame-svg"
        xmlns="http://www.w3.org/2000/svg"
        width="330"
        height="330"
        viewBox="0 0 370 370"
        fill="none"
      >
        <!-- 프레임 모양을 그리는 부분 -->
        <path d="M22.2695 346.728H369L346.73 368.994H0L22.2695 346.728Z" fill="#AD9D8D" />
        <path
          d="M22.2671 346.728L22.2671 0.00057665L-5.37221e-06 19.0858L-5.24521e-06 368.997L22.2671 346.728Z"
          fill="#AD9D8D"
        />
        <path
          d="M300.605 367.417C308.558 364.766 322.872 349.924 345.14 367.417L367.405 346.727H130.42H22.266L0.00371167 369.001C7.95625 366.35 14.3143 349.923 36.5814 367.416C44.5339 364.765 55.6674 349.923 77.9345 367.416C85.8871 364.765 100.202 349.923 122.469 367.416C130.421 364.765 144.736 349.924 167.003 367.417C174.955 364.766 189.27 349.922 211.537 367.414C219.49 364.764 233.804 349.924 256.071 367.417C264.024 364.766 278.338 349.924 300.605 367.417Z"
          fill="url(#paint0_linear_1029_931)"
        />
        <path
          d="M0.00125062 268.793C7.95378 266.143 11.1325 302.204 0.000171401 313.342C5.3017 321.819 12.7246 344.82 0.00371167 369.001L22.266 346.727L22.2679 0L0.00371167 19.0861C7.95625 16.4355 11.1323 36.5771 0 47.715C7.95253 45.0644 11.136 79.5204 0.00371167 90.6582C7.95625 88.0077 11.1325 122.464 0.000171401 133.602C7.95271 130.951 11.1342 168.595 0.00185584 179.733C7.95439 177.082 11.136 213.122 0.00371167 224.26C7.95625 221.609 11.1335 257.655 0.00125062 268.793Z"
          fill="url(#paint1_linear_1029_931)"
        />

        <!-- 클리핑 패스 정의 -->
        <clipPath id="clipShape">
          <!-- 여기서 프레임의 클리핑 영역을 정의합니다 -->
          <rect x="10" y="10" width="369" height="369" />
        </clipPath>

        <defs>
          <linearGradient
            id="paint0_linear_1029_931"
            x1="365.817"
            y1="-2.53864e-05"
            x2="0.00423596"
            y2="368.998"
            gradientUnits="userSpaceOnUse"
          >
            <stop stop-color="white" />
            <stop offset="1" stop-color="#CCCCCC" />
          </linearGradient>
          <linearGradient
            id="paint1_linear_1029_931"
            x1="183.703"
            y1="5.45097e-06"
            x2="0.0035463"
            y2="368.995"
            gradientUnits="userSpaceOnUse"
          >
            <stop stop-color="white" />
            <stop offset="1" stop-color="#D1D1D1" />
          </linearGradient>
        </defs>
      </svg>
      <!-- 이미지 -->
      <img id="map-image" src={image} alt="" />
    </div>
    <div class="mb-3">
      <h2 id="result-title-card">{title}</h2>
    </div>
    <div class="flex justify-around mb-3">
      <div>
        루트 총 길이 <br /> <br />
        <span class="font-bold">{distance}km</span>
      </div>
      {#if isRoute}
        <div>
          예상 소요 시간 <br /> <br />
          <span class="font-bold">{Math.floor(time / 60)}분</span>
        </div>
      {:else}
        <div>
          소요 시간 <br /> <br />
          <span class="font-bold">{Math.floor(time / 60)}분</span>
        </div>
      {/if}
    </div>
  </Card>
</div>

<style>
  #canvas-img {
    position: relative;
    width: 100%;
    height: auto;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  #map-image {
    position: absolute;
    bottom: 16px;
    left: 6px;
    width: 97.5%;
    height: 97.5%;
    object-fit: cover; /* 이미지를 프레임 안에 잘 맞추기 위해 사용 */
    clip-path: url(#clipShape); /* SVG 프레임의 빈 공간에 이미지를 배치 */
    z-index: 0;
  }

  #frame-svg {
    width: 100%;
    height: auto;
    position: relative;
    z-index: 1;
  }

  #route-result {
    z-index: 1001;
    width: 80%;
    height: 60%;
    margin-top: 25%;
    text-align: center;
  }

  #result-title {
    font-size: 20px;
    font-weight: bold;
    margin-bottom: 5%;
    margin-top: 5%;
  }

  #result-title-card {
    font-size: 20px;
    font-weight: bold;
    margin-bottom: 10%;
    margin-top: 10%;
  }
</style>
