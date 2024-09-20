<script lang="ts">
  import { Card, Button, Toggle } from 'flowbite-svelte';

  import DrawingSummaryInfo from '@components/calendar/DrawingSummaryInfo.svelte';
  export let time: number;
  export let averagePace: number;
  export let distance: number;
  export let imgSrc: string;
</script>

<div>
  <div class="space-y-4">
    <Card img={imgSrc} horizontal size="md">
      <h5 class="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
        Noteworthy technology acquisitions 2021
      </h5>
      <p class="mb-3 font-normal text-gray-700 dark:text-gray-400 leading-tight">
        Here are the biggest enterprise technology acquisitions of 2021 so far, in reverse
        chronological order.
      </p>
    </Card>
  </div>
  <img src={imgSrc} alt="드로잉 캡쳐 이미지" />
  <DrawingSummaryInfo {time} {averagePace} {distance} />
</div>

<style>
</style>
