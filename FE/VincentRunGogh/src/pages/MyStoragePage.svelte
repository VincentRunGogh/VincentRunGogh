<script lang="ts">
  import Tabbar from '@/components/tabbar/Tabbar.svelte';
  import { Card, Button, Carousel, Tabs, TabItem } from 'flowbite-svelte';
  import { replace } from 'svelte-spa-router';
  import { PaletteOutline, HeartSolid, TrashBinSolid } from 'flowbite-svelte-icons';
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
      id: 11,
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
      id: 12,
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
      id: 13,
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
      id: 14,
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
      id: 15,
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
      id: 16,
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
      id: 17,
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
      id: 18,
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
      id: 19,
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
      id: 20,
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

  const dummyLikedList = [
    {
      id: 21,
      nickname: 'test1',
      profile: '/default.png',
      drawingImage: 'http://via.placeholder.com/150x150',
      artImage: 'http://via.placeholder.com/150x150/FF7ED0',
      comment: '런닝 후 상쾌해요!',
      likeCount: 43,
      isLiked: true,
      distance: 5000,
      time: 1200,
      created: null,
      distanceFromUser: 1200,
    },
    {
      id: 22,
      nickname: 'test1',
      profile: '/default.png',
      drawingImage: 'http://via.placeholder.com/150x150',
      artImage: 'http://via.placeholder.com/150x150/FF7ED0',
      comment: '오늘도 완주 성공!',
      likeCount: 67,
      isLiked: true,
      distance: 7000,
      time: 3400,
      created: null,
      distanceFromUser: 3500,
    },
    {
      id: 23,
      nickname: 'test2',
      profile: '/default.png',
      drawingImage: 'http://via.placeholder.com/150x150',
      artImage: 'http://via.placeholder.com/150x150/FF7ED0',
      comment: '조금 힘들었어요',
      likeCount: 25,
      isLiked: true,
      distance: 3000,
      time: 2000,
      created: null,
      distanceFromUser: 2200,
    },
    {
      id: 24,
      nickname: 'test2',
      profile: '/default.png',
      drawingImage: 'http://via.placeholder.com/150x150',
      artImage: 'http://via.placeholder.com/150x150/FF7ED0',
      comment: '기록 갱신!',
      likeCount: 89,
      isLiked: true,
      distance: 8000,
      time: 4500,
      created: null,
      distanceFromUser: 1800,
    },
    {
      id: 25,
      nickname: 'test3',
      profile: '/default.png',
      drawingImage: 'http://via.placeholder.com/150x150',
      artImage: 'http://via.placeholder.com/150x150/FF7ED0',
      comment: '날씨가 좋았어요',
      likeCount: 53,
      isLiked: true,
      distance: 6000,
      time: 3200,
      created: null,
      distanceFromUser: 4100,
    },
    {
      id: 26,
      nickname: 'test3',
      profile: '/default.png',
      drawingImage: 'http://via.placeholder.com/150x150',
      artImage: 'http://via.placeholder.com/150x150/FF7ED0',
      comment: '조금 더 뛸걸!',
      likeCount: 32,
      isLiked: true,
      distance: 4000,
      time: 2800,
      created: null,
      distanceFromUser: 3000,
    },
    {
      id: 27,
      nickname: 'test4',
      profile: '/default.png',
      drawingImage: 'http://via.placeholder.com/150x150',
      artImage: 'http://via.placeholder.com/150x150/FF7ED0',
      comment: '즐거운 시간!',
      likeCount: 75,
      isLiked: true,
      distance: 9000,
      time: 5100,
      created: null,
      distanceFromUser: 3600,
    },
    {
      id: 28,
      nickname: 'test4',
      profile: '/default.png',
      drawingImage: 'http://via.placeholder.com/150x150',
      artImage: 'http://via.placeholder.com/150x150/FF7ED0',
      comment: '다음엔 더 잘할래요!',
      likeCount: 22,
      isLiked: true,
      distance: 4500,
      time: 3700,
      created: null,
      distanceFromUser: 2800,
    },
    {
      id: 29,
      nickname: 'test5',
      profile: '/default.png',
      drawingImage: 'http://via.placeholder.com/150x150',
      artImage: 'http://via.placeholder.com/150x150/FF7ED0',
      comment: '너무 힘들어요',
      likeCount: 48,
      isLiked: true,
      distance: 3500,
      time: 2900,
      created: null,
      distanceFromUser: 4900,
    },
    {
      id: 30,
      nickname: 'test5',
      profile: '/default.png',
      drawingImage: 'http://via.placeholder.com/150x150',
      artImage: 'http://via.placeholder.com/150x150/FF7ED0',
      comment: '잘 뛴 것 같아요',
      likeCount: 16,
      isLiked: true,
      distance: 1000,
      time: 350,
      created: null,
      distanceFromUser: 1500,
    },
  ];

  // 게시글 검색 조건
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

  // 게시글 삭제
  async function deleteArticle(article: object) {
    // 삭제여부 모달
    Swal.fire({
      title: '정말로 삭제하시겠습니까?',
      text: '삭제 후 복구가 불가능합니다.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: '삭제',
      cancelButtonText: '취소',
    }).then((result) => {
      // 여기서 API 삭제
      if (result.isConfirmed) {
        Swal.fire({
          title: '삭제 완료!',
          text: '등록한 게시글을 삭제하였습니다',
          icon: 'success',
        });
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
    <Tabs tabStyle="underline">
      <TabItem open>
        <div slot="title" class="flex items-center gap-2">
          <PaletteOutline size="md" />
          내가 만든 루트
        </div>
        {#each dummyArticleList as article}
          {#if article.distanceFromUser <= range * 1000}
            <Card class="mb-5">
              <div class="flex">
                <img src={article.profile} alt="" style="width: 50px;" />
                <p>{article.nickname}</p>
                <button on:click={() => deleteArticle(article)}>
                  <TrashBinSolid size="md" color="red" />
                </button>
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
      </TabItem>
      <TabItem>
        <div slot="title" class="flex items-center gap-2">
          <HeartSolid size="md" />
          찜한 루트
        </div>
        {#each dummyLikedList as article}
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
      </TabItem>
    </Tabs>
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
