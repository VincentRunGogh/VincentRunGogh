<script lang="ts">
  import { onMount } from 'svelte';
  import { location, querystring } from 'svelte-spa-router';

  import DrawingInfoBox from '@/components/calendar/DrawingInfoBox.svelte';

  interface DrawingInfo {
    title: string;
    routeImage: string;
    drawingDetailList: DrawingDetail[];
  }
  interface DrawingDetail {
    drawingDetailId: number;
    drawingDetailImage: string;
    drawingDetailDistance: number;
    drawingDetailTime: number;
    drawingDetailSpeed: number;
    drawingDetailCreateTime: string;
  }

  let drawingId = $querystring?.split('&')[0].split('=')[1];
  let date = $querystring?.split('&')[1].split('=')[1];
  let drawingDetail: DrawingInfo;

  onMount(() => {
    console.log(drawingDetail, date);
    //TODO - 드로잉 상세 조회 api 연결
    drawingDetail = {
      title: '마루는 강쥐',
      routeImage: 'https:~',
      drawingDetailList: [
        {
          drawingDetailId: 1,
          drawingDetailImage: 'https:~',
          drawingDetailDistance: 1,
          drawingDetailTime: 1,
          drawingDetailSpeed: 1,
          drawingDetailCreateTime: '2024-06-19 19-20-57',
        },
      ],
    };
  });
</script>

<div>
  <h1>{drawingDetail?.title}</h1>
  <img src={drawingDetail?.routeImage} alt="routeImage" />
  <ul>
    {#if drawingDetail?.drawingDetailList}
      {#each drawingDetail.drawingDetailList as detail}
        <li>
          <p>{detail.drawingDetailCreateTime}</p>
          <DrawingInfoBox
            time={detail.drawingDetailTime}
            averagePace={detail.drawingDetailSpeed}
            distance={detail.drawingDetailDistance}
            imgSrc={detail.drawingDetailImage}
          />
        </li>
      {/each}
    {/if}
  </ul>
</div>
