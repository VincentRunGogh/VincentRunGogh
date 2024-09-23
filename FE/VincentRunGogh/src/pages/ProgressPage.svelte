<script lang="ts">
  import { onMount } from 'svelte';
  import { Chart, registerables } from 'chart.js';
  import { Select } from 'flowbite-svelte';
  import type { SelectOptionType } from 'flowbite-svelte';

  let selectedYear: number = new Date().getFullYear();
  let yearItem: SelectOptionType<any>[] = [
    { value: '2024', name: '2024' },
    { value: '2023', name: '2023' },
  ];

  let chartType = 'walking';
  let chart = null;

  onMount(() => {
    //TODO - api 연결 드로잉 활동 정보 조회 /api/v1/myhealth/drawings?year={year}
    const ctx = document.getElementById('chart').getContext('2d');
    chart = new Chart(ctx, {
      type: 'doughnut',
      data: {
        labels: ['Underweight', 'Normal', 'Overweight', 'Obese'],
        datasets: [
          {
            data: [18.5, 24.9, 29.9, 40],
            backgroundColor: ['blue', 'green', 'orange', 'red'],
            needleValue: bmi, // 바늘을 위한 값 설정
          },
        ],
      },
      options: {
        circumference: 180,
        rotation: -90,
        plugins: {
          tooltip: { enabled: false },
        },
      },
    });
  });
  $: chart && ((chart.data.datasets[0].needleValue = bmi), chart.update());
</script>

<Select items={yearItem} bind:value={selectedYear} />
<canvas id="chart"></canvas>
