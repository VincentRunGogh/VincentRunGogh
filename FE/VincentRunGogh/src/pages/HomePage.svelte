<script lang="ts">
  import { getWeeklyInfo } from '@/api/userApi2';
  import Tabbar from '@/components/tabbar/Tabbar.svelte';
  import { Chart, Card, A, Button, Dropdown, DropdownItem } from 'flowbite-svelte';
  import { onMount } from 'svelte';
  import Swal from 'sweetalert2';

  let isLoad: boolean = false;

  // 유형별 정보 더미
  let dummyGroupInfo: {
    averageDayRuntime: number;
    averageDayDistance: number;
    averageSpeed: number;
    myDayRuntime: number;
    myDayDistance: number;
    mySpeed: number;
    gender: number;
    age: number;
  } = {
    // 평균 운동량 600초, 400미터, 4km/h
    // 더미 운동량 1200초, 800미터, 8km/h
    // 더미 정보 20세 남자
    averageDayRuntime: 600,
    averageDayDistance: 400,
    averageSpeed: 4,
    myDayRuntime: 1200,
    myDayDistance: 800,
    mySpeed: 8,
    gender: 0,
    age: 20,
  };

  // 홈 문구 대사
  // 내 그룹 설정
  let groupIn: string = '';
  if (!dummyGroupInfo.gender) {
    groupIn = `${Math.floor(dummyGroupInfo.age / 10) * 10}대 남자`;
  } else {
    groupIn = `${Math.floor(dummyGroupInfo.age / 10) * 10}대 여자`;
  }
  let welcomeWordList: string[] = [
    '오운완했나요?',
    `${groupIn}는 오늘 평균 ${Math.floor(dummyGroupInfo.averageDayRuntime / 60)}분 운동했습니다!`,
    `${groupIn}는 오늘 평균 ${Math.floor(dummyGroupInfo.averageDayDistance / 1000)}km 뛰었습니다!`,
    `오늘은 ${Math.floor(dummyGroupInfo.myDayRuntime / 60)}분 뛰었군요!`,
    `오늘은 ${parseFloat((dummyGroupInfo.myDayDistance / 1000).toFixed(2))}km를 뛰었군요!`,
  ];

  let randomNum: number = Math.floor(Math.random() * welcomeWordList.length);
  console.log(randomNum);

  // 진행중 드로잉 더미
  let dummyDrawingList: {
    drawingId: number;
    title: string;
    artImage: string;
    drawingImage: string;
    updated: string;
  }[] = [
    {
      drawingId: 1,
      title: '런닝 제목1',
      artImage: 'http://via.placeholder.com/150x150',
      drawingImage: 'http://via.placeholder.com/150x150/FFFF00',
      updated: '2024-07-15',
    },
    {
      drawingId: 2,
      title: '런닝 제목2',
      artImage: 'http://via.placeholder.com/150x150',
      drawingImage: 'http://via.placeholder.com/150x150/FFFF00',
      updated: '2024-08-22',
    },
    {
      drawingId: 3,
      title: '런닝 제목3',
      artImage: 'http://via.placeholder.com/150x150',
      drawingImage: 'http://via.placeholder.com/150x150/FFFF00',
      updated: '2024-06-30',
    },
  ];

  // 진행중인 드로잉 클릭
  async function clickOngoingDrawing(drawingId: number) {
    // try {
    //     let data = await fetchData(); // AJAX 요청 함수 호출
    //     // 요청 성공 후 모달 열기
    //     Swal.fire({
    //         title: '모달 제목',
    //         text: data.message, // AJAX 요청으로 받은 데이터를 모달에 표시
    //         showCancelButton: true,
    //         confirmButtonText: '확인',
    //         preConfirm: () => {
    //             // 확인 버튼 클릭 시 실행할 코드
    //             return new Promise((resolve) => {
    //                 // 추가 처리 코드 (예: 다른 AJAX 요청 등)
    //                 resolve();
    //             });
    //         }
    //     });
    // } catch (error) {
    //     // 오류 처리
    //     Swal.fire('오류', 'AJAX 요청에 실패했습니다.', 'error');
    // }
  }

  // 주간 운동량
  // 이전 7일 계산
  let today: Date = new Date();
  let dateList: string[] = [];

  for (let i = 6; i >= 0; i--) {
    const pastDate = new Date();
    pastDate.setDate(today.getDate() - i);

    const month = pastDate.getMonth() + 1; // 월은 0부터 시작하므로 +1
    const day = pastDate.getDate();

    dateList.push(`${month}/${day}`);
  }

  // 주간 운동량 더미
  let dummyWeeklyinfo: {
    distance: number[];
    time: number[];
  } = {
    distance: [1234, 2345, 2030, 3040, 4050, 2020, 3030],
    time: [600, 700, 800, 654, 1065, 1624, 845],
  };
  // 요청받기
  let weeklyInfo: {
    distance: number[];
    time: number[];
  } = {
    distance: [],
    time: [],
  };

  async function getWeekly() {
    try {
      let responseWeekly = await getWeeklyInfo();
      weeklyInfo = responseWeekly.data;
      options = {
        ...options,
        series: [
          {
            name: '운동 거리',
            color: '#1A56DB',
            data: [
              { x: dateList[0], y: weeklyInfo.distance[0] },
              { x: dateList[1], y: weeklyInfo.distance[1] },
              { x: dateList[2], y: weeklyInfo.distance[2] },
              { x: dateList[3], y: weeklyInfo.distance[3] },
              { x: dateList[4], y: weeklyInfo.distance[4] },
              { x: dateList[5], y: weeklyInfo.distance[5] },
              { x: dateList[6], y: weeklyInfo.distance[6] },
            ],
          },
          {
            name: '운동 시간',
            color: '#FDBA8C',
            data: [
              { x: dateList[0], y: weeklyInfo.time[0] },
              { x: dateList[1], y: weeklyInfo.time[1] },
              { x: dateList[2], y: weeklyInfo.time[2] },
              { x: dateList[3], y: weeklyInfo.time[3] },
              { x: dateList[4], y: weeklyInfo.time[4] },
              { x: dateList[5], y: weeklyInfo.time[5] },
              { x: dateList[6], y: weeklyInfo.time[6] },
            ],
          },
        ],
      };
    } catch (error) {
      {
        throw error;
      }
    }
  }

  // 주간 차트 옵션
  let options = {
    colors: ['#1A56DB', '#FDBA8C'],
    series: [
      {
        name: '운동 거리',
        color: '#1A56DB',
        data: [
          { x: dateList[0], y: 0 },
          { x: dateList[1], y: 0 },
          { x: dateList[2], y: 0 },
          { x: dateList[3], y: 0 },
          { x: dateList[4], y: 0 },
          { x: dateList[5], y: 0 },
          { x: dateList[6], y: 0 },
        ],
      },
      {
        name: '운동 시간',
        color: '#FDBA8C',
        data: [
          { x: dateList[0], y: 0 },
          { x: dateList[1], y: 0 },
          { x: dateList[2], y: 0 },
          { x: dateList[3], y: 0 },
          { x: dateList[4], y: 0 },
          { x: dateList[5], y: 0 },
          { x: dateList[6], y: 0 },
        ],
      },
    ],
    chart: {
      type: 'bar',
      width: '100%',
      height: '120%',
      fontFamily: 'Inter, sans-serif',
      toolbar: {
        show: false,
      },
    },
    plotOptions: {
      bar: {
        horizontal: false,
        columnWidth: '75%',
        borderRadiusApplication: 'end',
        borderRadius: 4,
      },
    },
    tooltip: {
      shared: true,
      intersect: false,
      style: {
        fontFamily: 'Inter, sans-serif',
      },
      y: {
        formatter: function (val: number, { seriesIndex }) {
          if (seriesIndex === 0) {
            return parseFloat((val / 1000).toFixed(2)) + 'km'; // '운동 거리'에 km 단위 추가
          } else if (seriesIndex === 1) {
            return Math.floor(val / 60) + '분'; // '운동 시간'에 분 단위 추가
          }
          return val;
        },
      },
    },
    states: {
      hover: {
        filter: {
          type: 'darken',
          value: 1,
        },
      },
    },
    stroke: {
      show: true,
      width: 0,
      colors: ['transparent'],
    },
    grid: {
      show: false,
      strokeDashArray: 4,
      padding: {
        left: 2,
        right: 2,
        top: -14,
      },
    },
    dataLabels: {
      enabled: false,
    },
    legend: {
      show: false,
    },
    xaxis: {
      floating: false,
      labels: {
        show: true,
        style: {
          fontFamily: 'Inter, sans-serif',
          cssClass: 'text-xs font-normal fill-gray-500 dark:fill-gray-400',
        },
      },
      axisBorder: {
        show: false,
      },
      axisTicks: {
        show: false,
      },
    },
    yaxis: {
      show: false,
    },
    fill: {
      opacity: 1,
    },
  };

  onMount(async () => {
    await getWeekly();
    isLoad = true;
  });
</script>

<!-- 차트 째보기 -->

<div id="homepage-body">
  <div id="homepage-header">
    <p>유저 이름님!</p>
    <h3 class="font-bold">{welcomeWordList[randomNum]}</h3>
  </div>
  <div id="homepage-drawing">
    <p>진행 중인 드로잉</p>
    <div id="drawing-list">
      {#each dummyDrawingList as drawing}
        <Card
          class="m-2 mt-5 mb-5 text-xs p-0 "
          size="xs"
          on:click={() => clickOngoingDrawing(drawing.drawingId)}
        >
          <img class="rounded-t-lg" src="http://via.placeholder.com/100x100" alt="" />
          <div class="p-1">
            <h5 class="mb-2 font-bold tracking-tight text-gray-900 dark:text-white">
              {drawing.title}
            </h5>
            <p class="font-normal text-gray-700 dark:text-gray-400 leading-tight">
              {drawing.updated}
            </p>
          </div>
        </Card>
      {/each}
    </div>
  </div>
  <div id="homepage-chart">
    <Card class="w-80">
      {#if isLoad}
        <Chart {options} />
      {/if}
    </Card>
  </div>
  <div id="homepage-tabbar">
    <Tabbar />
  </div>
</div>

<style>
  #homepage-body {
    justify-self: center;
    text-align: center;
    width: 100%;
    max-width: 1024px;
    margin: 0;
    background: var(--white-bg-color, #f9f8ef);
    display: flex;
    flex-direction: column;
  }

  #homepage-header {
    justify-self: left;
    height: 25vh;
    padding: 15vh 0vh 10vh 0vh;
  }

  #homepage-drawing {
    height: 30vh;
    padding-top: 0%;
  }

  #drawing-list {
    display: flex;
    height: 90%;
    justify-content: space-around;
    align-items: center;
  }

  #homepage-chart {
    height: 30vh;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  #homepage-tabbar {
    height: 15vh;
  }
</style>
