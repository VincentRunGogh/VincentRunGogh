<script lang="ts">
  import Tabbar from '@/components/tabbar/Tabbar.svelte';
  import { Card, Button, Carousel } from 'flowbite-svelte';
  import { replace } from 'svelte-spa-router';
  import Swal from 'sweetalert2';

  let dummyArticleList: {
    id: number;
    nickname: string;
    profile: string;
    drawingImage: string;
    artImage: string;
    comment: string;
    likeCount: number;
    isLiked: boolean;
    distance: number;
    time: number;
    created: any;
    distanceFromUser: number;
  }[] = [
    {
      id: 1,
      nickname: 'test1',
      profile: '/default.png',
      drawingImage: 'http://via.placeholder.com/150x150',
      artImage: 'http://via.placeholder.com/150x150/FFFF00',
      comment: '런닝 후 상쾌함이 느껴졌어요! 추천합니다.',
      likeCount: 34,
      isLiked: true,
      distance: 3200,
      time: 1500,
      created: null,
      distanceFromUser: 1800,
    },
    {
      id: 2,
      nickname: 'test2',
      profile: '/default.png',
      drawingImage: 'http://via.placeholder.com/150x150',
      artImage: 'http://via.placeholder.com/150x150/FFFF00',
      comment: '오늘의 런닝 정말 기분 좋았어요.',
      likeCount: 58,
      isLiked: false,
      distance: 5000,
      time: 2200,
      created: null,
      distanceFromUser: 2400,
    },
    {
      id: 3,
      nickname: 'test1',
      profile: '/default.png',
      drawingImage: 'http://via.placeholder.com/150x150',
      artImage: 'http://via.placeholder.com/150x150/FFFF00',
      comment: '힘든 런닝이었지만 후련하네요.',
      likeCount: 21,
      isLiked: true,
      distance: 4100,
      time: 1700,
      created: null,
      distanceFromUser: 1300,
    },
    {
      id: 4,
      nickname: 'test2',
      profile: '/default.png',
      drawingImage: 'http://via.placeholder.com/150x150',
      artImage: 'http://via.placeholder.com/150x150/FFFF00',
      comment: '상쾌한 아침 런닝 최고입니다.',
      likeCount: 76,
      isLiked: false,
      distance: 2900,
      time: 2000,
      created: null,
      distanceFromUser: 2200,
    },
    {
      id: 5,
      nickname: 'test1',
      profile: '/default.png',
      drawingImage: 'http://via.placeholder.com/150x150',
      artImage: 'http://via.placeholder.com/150x150/FFFF00',
      comment: '런닝 후 기분이 정말 좋아졌어요.',
      likeCount: 43,
      isLiked: true,
      distance: 3500,
      time: 1900,
      created: null,
      distanceFromUser: 1600,
    },
    {
      id: 6,
      nickname: 'test2',
      profile: '/default.png',
      drawingImage: 'http://via.placeholder.com/150x150',
      artImage: 'http://via.placeholder.com/150x150/FFFF00',
      comment: '지친 몸에 활력을 주는 런닝이었습니다.',
      likeCount: 64,
      isLiked: false,
      distance: 4800,
      time: 2100,
      created: null,
      distanceFromUser: 2500,
    },
    {
      id: 7,
      nickname: 'test1',
      profile: '/default.png',
      drawingImage: 'http://via.placeholder.com/150x150',
      artImage: 'http://via.placeholder.com/150x150/FFFF00',
      comment: '운동 후 상쾌한 기분이 좋네요.',
      likeCount: 32,
      isLiked: true,
      distance: 3900,
      time: 2300,
      created: null,
      distanceFromUser: 1900,
    },
    {
      id: 8,
      nickname: 'test2',
      profile: '/default.png',
      drawingImage: 'http://via.placeholder.com/150x150',
      artImage: 'http://via.placeholder.com/150x150/FFFF00',
      comment: '런닝 후 피로가 풀리는 느낌이었습니다.',
      likeCount: 55,
      isLiked: false,
      distance: 4300,
      time: 2600,
      created: null,
      distanceFromUser: 2700,
    },
    {
      id: 9,
      nickname: 'test1',
      profile: '/default.png',
      drawingImage: 'http://via.placeholder.com/150x150',
      artImage: 'http://via.placeholder.com/150x150/FFFF00',
      comment: '런닝 덕분에 하루가 상쾌해졌어요!',
      likeCount: 29,
      isLiked: true,
      distance: 4700,
      time: 2800,
      created: null,
      distanceFromUser: 2100,
    },
    {
      id: 10,
      nickname: 'test2',
      profile: '/default.png',
      drawingImage: 'http://via.placeholder.com/150x150',
      artImage: 'http://via.placeholder.com/150x150/FFFF00',
      comment: '오랜만에 런닝 후 기분이 너무 좋았습니다.',
      likeCount: 83,
      isLiked: false,
      distance: 5500,
      time: 3000,
      created: null,
      distanceFromUser: 3000,
    },
  ];

  let range: number = 5;

  function searchCondition() {
    Swal.fire({
      title: '검색 범위를 조절해주세요!',
      html: `
    <input type="range" min="1" max="10" step="1" class="w-full h-2 bg-gray-200 rounded-lg cursor-pointer" id="range-input" value="${range}">
    <div id="range-value">반경 ${range}km 까지 검색합니다.</div>
  `,
      showCancelButton: true,
      confirmButtonText: '확인',
      cancelButtonText: '취소',
      preConfirm: () => {
        return range; // 범위 값을 반환
      },
      didOpen: () => {
        const input = document.getElementById('range-input') as HTMLInputElement;
        const rangeValue = document.getElementById('range-value');

        // 범위 값 업데이트 이벤트 리스너
        input.addEventListener('input', () => {
          range = Number(input.value); // 범위 값 업데이트
          if (rangeValue) {
            rangeValue.innerText = `반경 ${range}km 까지 검색합니다.`; // 문구 업데이트
          }
        });
      },
    }).then((result) => {
      if (result.isConfirmed) {
        console.log(`선택된 범위: ${range}`); // 확인 시 범위 값 출력
        // 여기서 range 변수를 사용하여 추가 로직을 구현
      }
    });
  }
</script>

<div id="community-body">
  <div id="community-header">
    <div id="community-logo">
      <img src="/4.png" alt="logo" style="height: 100%;" />
    </div>
    <div id="community-title">
      <h1 style="font-size: 20px; font-weight: bold;">커뮤니티</h1>
    </div>
    <!-- svelte-ignore a11y-click-events-have-key-events -->
    <!-- svelte-ignore a11y-interactive-supports-focus -->
    <div id="community-mystorage" role="button" on:click={() => replace('/community/mystorage')}>
      <img src="/66.png" alt="logo" style="height: 100%;" />
    </div>
  </div>
  <div id="community-content">
    <button on:click={searchCondition}>검색 반경 설정</button>
    <p>반경 {range}km내의 루트만 표시됩니다.</p>
    {#each dummyArticleList as article}
      {#if article.distanceFromUser <= range * 1000}
        <Card class="mb-5">
          <div class="flex">
            <img src={article.profile} alt="" style="width: 50px;" />
            <p>{article.nickname}</p>
          </div>
          <div class="max-w-4xl">
            <Carousel
              images={[
                { src: article.drawingImage, alt: 'drawingImage' },
                { src: article.artImage, alt: 'artImage' },
              ]}
              let:Indicators
            >
              <Indicators />
            </Carousel>
          </div>
          <p class="mb-3 font-normal text-gray-700 dark:text-gray-400 leading-tight">
            {article.comment}
          </p>
          <p class="mb-3 font-normal text-gray-700 dark:text-gray-400 leading-tight">
            <span>나와의 거리 {article.distanceFromUser / 1000}km</span>
            <span>총 길이 {article.distance / 1000}km</span>
          </p>
          <div>
            {#if article.isLiked}
              찜!
            {/if}
            <Button color={article.isLiked ? 'red' : 'blue'}>
              {article.isLiked ? '좋아요 취소' : '좋아용'}
            </Button>
            {article.likeCount}
          </div>
        </Card>
      {/if}
    {/each}
  </div>
  <div id="community-tabbar">
    <Tabbar />
  </div>
</div>

<style>
  #community-body {
    justify-self: center;
    text-align: center;
    width: 100%;
    max-width: 1024px;
    margin: 0;
    background: var(--white-bg-color, #f9f8ef);
    display: flex;
    flex-direction: column;
  }

  #community-header {
    height: 10vh;
    display: flex;
    justify-content: space-between;
  }

  #community-logo {
    width: 15%;
  }

  #community-title {
    width: 40%;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  #community-mystorage {
    width: 15%;
  }

  #community-content {
    height: 75vh;
    width: 100%;
    overflow-y: scroll;
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  #community-content::-webkit-scrollbar {
    display: none;
  }

  #community-tabbar {
    height: 15vh;
  }
  .carousel-indicators button {
    background-color: transparent; /* 배경을 투명하게 설정 */
    border: none; /* 불필요한 테두리 제거 */
    box-shadow: none; /* 네모 그림자가 있으면 제거 */
  }

  .carousel-indicators button.active {
    background-color: gray; /* 활성화된 Indicator의 색상 */
  }
</style>
