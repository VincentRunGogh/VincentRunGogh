<script>
  import { onMount } from 'svelte';
  import { Chart, registerables } from 'chart.js';

  const centerLabelPlugin = {
    id: 'centerLabel',
    afterDraw(chart) {
      const {
        ctx,
        chartArea: { top, bottom, left, right, width, height },
      } = chart;
      ctx.save();
      ctx.font = '16px Arial';
      ctx.textAlign = 'center';
      ctx.textBaseline = 'middle';
      ctx.fillStyle = 'black';
      ctx.fillText(
        chart.data.datasets[0].needleValue.toFixed(1),
        left + width / 2,
        top + height / 2
      );
      ctx.restore();
    },
  };
  const needlePlugin = {
    id: 'needle',
    afterDatasetsDraw(chart, args, options) {
      const {
        ctx,
        config,
        data,
        chartArea: { top, bottom, left, right, width, height },
      } = chart;
      const needleValue = data.datasets[0].needleValue;
      const cx = left + width / 2;
      const cy = top + height / 2;
      const radius = Math.min(width, height) / 2;

      // 계산된 위치에 바늘 그리기
      ctx.save();
      ctx.translate(cx, cy);
      ctx.rotate((Math.PI / 180) * (needleValue * 2 - 180)); // 바늘 회전 계산
      ctx.beginPath();
      ctx.moveTo(0, -3);
      ctx.lineTo(radius - 10, 0);
      ctx.lineTo(0, 3);
      ctx.fillStyle = 'black';
      ctx.fill();
      ctx.restore();
    },
  };
  Chart.register(...registerables);
  // Chart.register(...registerables, needlePlugin, centerLabelPlugin);

  export let bmi = 22.5; // 예제 BMI 값

  let chart = null;
  onMount(() => {
    const ctx = document.getElementById('bmiChart').getContext('2d');
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
      plugins: [
        {
          // afterDraw: (chart) => {
          //   var needleValue = chart.chart.config.data.datasets[0].needleValue;
          //   var dataTotal = chart.chart.config.data.datasets[0].data.reduce((a, b) => a + b, 0);
          //   var angle = Math.PI + (1 / dataTotal) * needleValue * Math.PI;
          //   var ctx = chart.chart.ctx;
          //   var cw = chart.chart.canvas.offsetWidth;
          //   var ch = chart.chart.canvas.offsetHeight;
          //   var cx = cw / 2;
          //   var cy = ch - 6;
          //   ctx.translate(cx, cy);
          //   ctx.rotate(angle);
          //   ctx.beginPath();
          //   ctx.moveTo(0, -3);
          //   ctx.lineTo(ch - 20, 0);
          //   ctx.lineTo(0, 3);
          //   ctx.fillStyle = 'rgb(0, 0, 0)';
          //   ctx.fill();
          //   ctx.rotate(-angle);
          //   ctx.translate(-cx, -cy);
          //   ctx.beginPath();
          //   ctx.arc(cx, cy, 5, 0, Math.PI * 2);
          //   ctx.fill();
          // },
        },
      ],
    });
  });
  $: chart && ((chart.data.datasets[0].needleValue = bmi), chart.update());
</script>

<canvas id="bmiChart" width="400" height="200"></canvas>
