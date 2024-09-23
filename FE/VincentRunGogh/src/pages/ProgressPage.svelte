<script lang="ts">
  import { onMount } from 'svelte';
  import { Chart } from 'svelte-chartjs';
  import {
    Chart as ChartJS,
    Tooltip,
    Legend,
    BarElement,
    PointElement,
    LineElement,
    CategoryScale,
    LinearScale,
    LineController,
    BarController,
  } from 'chart.js';
  import { writable, derived } from 'svelte/store';
  import { Select } from 'flowbite-svelte';
  import type { SelectOptionType } from 'flowbite-svelte';

  ChartJS.register(
    LinearScale,
    CategoryScale,
    BarElement,
    PointElement,
    LineElement,
    Legend,
    Tooltip,
    LineController,
    BarController
  );

  let selectedYear: number = new Date().getFullYear();
  let yearItem: SelectOptionType<any>[] = [
    { value: '2024', name: '2024' },
    { value: '2023', name: '2023' },
  ];
  const onChangeYear = () => {
    console.log('change year');
    //TODO - api 연결
    activeCategory.update(() => 'walk');
  };

  //SECTION - 차트
  let activeCategory = writable('walk');
  let categoryData = writable({
    walkList: [11, 22, 33, 44, 55, 66, 77, 88, 99, 100, 110, 120],
    distanceList: [11, 22, 33, 44, 55, 66, 77, 88, 99, 100, 110, 120],
    timeList: [11, 22, 33, 44, 55, 66, 77, 88, 99, 100, 110, 120],
    completedRouteDrawingList: [2, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12],
    completedFreeDrawingList: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12],
    totalWalk: 123456,
    totalDistance: 123456,
    totalTime: 123456,
    totalCompletedDrawing: 123,
  });
  const labels = Array.from({ length: 12 }, (_, i) => `${i + 1}월`);
  let chartKey = writable(0);

  const chartData = derived(activeCategory, ($activeCategory) => {
    // 차트 데이터 생성
    let datasets = [];
    switch ($activeCategory) {
      case 'walk':
        datasets = [
          {
            label: '걸음 수',

            data: $categoryData.walkList,
            borderColor: 'blue',
            backgroundColor: 'rgba(0, 0, 255, 0.5)',
          },
        ];
        break;
      case 'distance':
        datasets = [
          {
            label: '이동 거리',
            data: $categoryData.distanceList,
            borderColor: 'green',
            backgroundColor: 'rgba(0, 128, 0, 0.5)',
          },
        ];
        break;
      case 'time':
        datasets = [
          {
            label: '활동 시간',
            data: $categoryData.timeList,
            borderColor: 'red',
            backgroundColor: 'rgba(255, 0, 0, 0.5)',
          },
        ];
        break;
      case 'completed':
        datasets = [
          {
            label: '완료된 루트 그리기',
            data: $categoryData.completedRouteDrawingList,
            borderColor: 'yellow',
            backgroundColor: 'rgba(255, 255, 0, 0.5)',
          },
          {
            label: '완료된 자유 그리기',
            data: $categoryData.completedFreeDrawingList,
            borderColor: 'purple',
            backgroundColor: 'rgba(128, 0, 128, 0.5)',
          },
        ];

        break;
    }
    return { labels, datasets };
  });

  let options = {
    scales: {
      y: {
        beginAtZero: true,
        // display: false,
        stacked: true,
      },
      x: {
        // display: false,
        stacked: true,
      },
    },
    plugins: {
      legend: {
        display: false,
      },
      tooltip: {
        enabled: true,
        usePointStyle: true,
        callbacks: {
          // To change title in tooltip
          label: (context) => {
            var label = context.dataset.label;
            var value = context.formattedValue;
            if (label === '활동 시간') return label + ' : ' + value + ' 초';
            else if (label === '이동 거리') return label + ' : ' + value + ' M';
            else return label + ' : ' + value + ' 번';
          },
        },
      },
    },
  };
  onMount(() => {
    //TODO - api 연결 드로잉 활동 정보 조회 /api/v1/myhealth/drawings?year={year}
  });
</script>

<Select items={yearItem} bind:value={selectedYear} on:change={onChangeYear} />
{#if $activeCategory === 'completed'}
  <Chart type="bar" {options} data={$chartData} />
{:else}
  <Chart type="line" {options} data={$chartData} />
{/if}

<div>
  <button class="categoryBtn" on:click={() => activeCategory.set('walk')}>
    <div class="categoryTitle">
      <span>걸음수</span>
      <span class="chartSum">{$categoryData.totalWalk}</span>
      <span>번</span>
    </div>
  </button>
  <button class="categoryBtn" on:click={() => activeCategory.set('distance')}>
    <div class="categoryTitle">
      <span>거리</span><span class="chartSum">{$categoryData.totalDistance}</span>
      <span>M</span>
    </div>
  </button>
  <button class="categoryBtn" on:click={() => activeCategory.set('time')}>
    <div class="categoryTitle">
      <span>시간</span><span class="chartSum">{$categoryData.totalTime}</span>
      <span>초</span>
    </div>
  </button>
  <button class="categoryBtn" on:click={() => activeCategory.set('completed')}>
    <div class="categoryTitle">
      <span>완성</span><span class="chartSum">{$categoryData.totalCompletedDrawing}</span>
      <span>번</span>
    </div>
  </button>
</div>

<style>
  .categoryBtn {
    width: 162.429px;
    height: 98.526px;
    flex-shrink: 0;
    border-radius: 20px;
    background: #fff;
  }

  .categoryTitle {
    width: 49.33px;
    height: 11.184px;
    flex-shrink: 0;
  }
  .categoryTitle span {
    color: var(--gray-color-100, #bebaba);
    font-family: Roboto;
    font-size: 18px;
    font-style: normal;
    font-weight: 300;
    line-height: normal;
  }
  .chartSum {
    color: var(--gray-label-color, #868282);
    font-family: Roboto;
    font-size: 24px;
    font-style: normal;
    font-weight: 700;
    line-height: normal;
  }
</style>
