<script lang="ts">
  import { onMount } from 'svelte';
  import { createEventDispatcher } from 'svelte';
  import {
    isLockScreen,
    isPause,
    elapsedTime,
    totalDistance,
    currentPace,
  } from '@/stores/drawingStore';
  import { get } from 'svelte/store';
  import { formatSecToMMSS } from '@/utils/formatter';

  const dispatch = createEventDispatcher<{
    'click-pause': boolean;
    'click-mute': boolean;
    'click-lockScreen': boolean;
  }>();
  $: $isLockScreen = $isLockScreen;
  $: $isPause = $isPause;

  let isMute = false;
  let hasRoute = false;

  function clickPause() {
    dispatch('click-pause', $isPause);
  }

  function clickMute() {
    isMute = !isMute;
    dispatch('click-mute', isMute);
  }

  function clickLockScreen() {
    dispatch('click-lockScreen', $isLockScreen);
  }
</script>

<div class="flex items-center justify-center">
  <div class="relative bg-white shadow-lg rounded-lg p-4">
    <button
      class="absolute top-0 left-1/2 transform -translate-x-1/2 -translate-y-1/2 bg-yellow-500 p-2 rounded-full focus:outline-none"
      on:click={clickPause}
      aria-label="Pause Button"
    >
      <!-- Pause icon -->
      <svg
        xmlns="http://www.w3.org/2000/svg"
        class="h-6 w-6"
        fill="none"
        viewBox="0 0 24 24"
        stroke="currentColor"
      >
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 9v6m4-6v6" />
      </svg>
    </button>

    <div class="flex items-center justify-between">
      <button
        class="text-green-500 focus:outline-none"
        on:click={clickMute}
        aria-label="Mute Button"
      >
        <!-- Mute icon -->
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="h-8 w-8"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
        >
          {#if isMute}
            <path
              fill="#FF4040"
              fill-rule="evenodd"
              d="M17.47 9.47a.75.75 0 0 1 1.06 0L20 10.94l1.47-1.47a.75.75 0 0 1 1.06 1.06L21.061 12l1.47 1.47a.75.75 0 0 1-1.061 1.06L20 13.06l-1.47 1.47a.75.75 0 0 1-1.06-1.06L18.94 12l-1.47-1.47a.75.75 0 0 1 0-1.06m-4.433-6.074c1.163-.767 2.713.068 2.713 1.461v14.286c0 1.394-1.55 2.228-2.713 1.461l-6-3.955a.25.25 0 0 0-.137-.042H4a2.75 2.75 0 0 1-2.75-2.75v-3.714A2.75 2.75 0 0 1 4 7.393h2.9a.25.25 0 0 0 .138-.041z"
              clip-rule="evenodd"
            />
          {:else}
            <path
              fill="currentColor"
              fill-rule="evenodd"
              d="M18.97 6.97a.75.75 0 0 1 1.06 0l-.53.53l.53-.53h.001l.001.002l.003.002l.007.007l.02.02l.062.069c.05.057.12.138.201.241A6.87 6.87 0 0 1 21.75 11.5a6.87 6.87 0 0 1-1.425 4.189a5 5 0 0 1-.264.31l-.02.02l-.006.007l-.003.002v.001h-.001l-.51-.508l.51.51 a.75.75 0 1 1-1.061-1.061l.53.53l-.53-.53h-.001v.001l-.002.001l.005-.005l.033-.036q.048-.052.139-.167a5.37 5.37 0 0 0 .448-5.843a5 5 0 0 0-.448-.685a3 3 0 0 0-.172-.203l-.005-.005a.75.75 0 0 1 .003-1.058m-5.933-3.574c1.163-.767 2.713.068 2.713 1.461v14.286c0 1.394-1.55 2.228-2.713 1.461l-6-3.955a.25.25 0 0 0-.137-.042H4a2.75 2.75 0 0 1-2.75-2.75v-3.714A2.75 2.75 0 0 1 4 7.393h2.9a.25.25 0 0 0 .138-.041z"
              clip-rule="evenodd"
            />
          {/if}
        </svg>
      </button>
    </div>
    <div class="flex items-center justify-between">
      <button
        class="text-green-500 focus:outline-none"
        on:click={clickLockScreen}
        aria-label="Mute Button"
      >
        <!-- lock icon -->
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="h-8 w-8"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
        >
          <defs>
            <mask id="ipSUnlock0">
              <g fill="none" stroke-linejoin="round" stroke-width="4">
                <rect width="34" height="22" x="7" y="22.048" fill="#fff" stroke="#fff" rx="2" />
                <path
                  stroke="#fff"
                  stroke-linecap="round"
                  d="M14 22v-7.995c-.005-5.135 3.923-9.438 9.086-9.954S32.967 6.974 34 12.006"
                />
                <path stroke="#000" stroke-linecap="round" d="M24 30v6" />
              </g>
            </mask>
          </defs>
        </svg>
      </button>
    </div>

    <div class="text-center mt-12">
      <div class="text-4xl font-bold text-gray-800">{formatSecToMMSS($elapsedTime)}</div>
      <div class="mt-4 space-x-4 flex justify-center items-center">
        <!-- Display dynamic data in an organized manner -->
        <div class="text-sm text-gray-500">
          이동거리: <span class="text-lg font-medium text-gray-700"
            >{Math.round($totalDistance * 10) / 10} km</span
          >
        </div>
        <div class="text-sm text-gray-500">
          페이스: <span class="text-lg font-medium text-gray-700"
            >{Math.round(3.4 * 10) / 10} km/h</span
          >
        </div>
        {#if hasRoute}
          <div class="text-sm text-gray-500">
            남은 거리: <span class="text-lg font-medium text-gray-700"
              >{Math.round(5.6 * 10) / 10} km</span
            >
          </div>
        {/if}
      </div>
    </div>
  </div>
</div>

<style>
  /* Tailwind CSS already handles a lot of styling for you, but you can add custom styles here */
</style>
