<script lang="ts">
  import { onDestroy, onMount } from 'svelte';
  import { writable } from 'svelte/store';
  import type { CalendarOptions } from 'svelte-fullcalendar';
  import FullCalendar from 'svelte-fullcalendar';
  import daygridPlugin from '@fullcalendar/daygrid';
  import interactionPlugin from '@fullcalendar/interaction';

  interface MonthInfo {
    monthTotalTime: number;
    monthTotalDistance: number;
    dayList: DayInfo[];
  }

  interface DayInfo {
    day: number;
    isRun: boolean;
    isDrawing: boolean;
    dayTotalTime: number;
    dayTotalDistance: number;
    drawingList: Drawing[];
  }

  interface Drawing {
    drawingId: number;
    drawingName: string;
    drawingTime: number;
    drawingDistance: number;
  }
  let date = writable(new Date());
  let activeStartDate = writable(new Date());
  let monthInfo = writable<MonthInfo | null>(null);
  let selectedDay = writable(null);
  let options: CalendarOptions;

  function getEventColor(day) {
    if (day.isDrawing) {
      return 'yellow';
    } else if (day.isRun) {
      return 'green';
    }
    return 'gray';
  }

  // Handle clicks on events specifically
  function handleEventClick(arg) {
    selectedDay.set(arg.event.extendedProps);
  }

  onMount(() => {
    monthInfo.set({
      monthTotalTime: 78061,
      monthTotalDistance: 32553,
      dayList: [
        {
          day: 13,
          isRun: true,
          isDrawing: false,
          dayTotalTime: 4120,
          dayTotalDistance: 2612,
          drawingList: [
            {
              drawingId: 123,
              drawingName: '마루는 강쥐',
              drawingTime: 620,
              drawingDistance: 503,
            },
            {
              drawingId: 112,
              drawingName: '고래 그림',
              drawingTime: 546,
              drawingDistance: 806,
            },
            {
              drawingId: 154,
              drawingName: '거북이 그림',
              drawingTime: 442,
              drawingDistance: 336,
            },
          ],
        },
      ],
    });
  });
  $: if ($monthInfo) {
    options = {
      initialView: 'dayGridMonth',
      plugins: [daygridPlugin, interactionPlugin],
      eventClick: handleEventClick,
      events: $monthInfo.dayList.map((day) => ({
        date: new Date(new Date().getFullYear(), new Date().getMonth(), day.day),
        title: day.isDrawing ? 'Drawing' : 'Run',
        color: getEventColor(day),
        extendedProps: day,
      })),
      locale: 'ko',
      selectable: true,
    };
  }
</script>

<FullCalendar {options} />
{#if $monthInfo}
  <h2>Month Details:</h2>
  <p>Total Time: {$monthInfo.monthTotalTime} seconds</p>
  <p>Total Distance: {$monthInfo.monthTotalDistance} meters</p>
{/if}
{#if $selectedDay}
  <div>
    <h2>Day Details:</h2>
    <p>Total Time: {$selectedDay.dayTotalTime} seconds</p>
    <p>Total Distance: {$selectedDay.dayTotalDistance} meters</p>
    <h3>Drawings:</h3>
    <ul>
      {#each $selectedDay.drawingList as drawing}
        <li>
          {drawing.drawingName} - Time: {drawing.drawingTime}, Distance: {drawing.drawingDistance}
        </li>
      {/each}
    </ul>
  </div>
{/if}
