<script lang="ts">
  import { link } from 'svelte-spa-router';
  import { BottomNav, BottomNavItem, Tooltip, Skeleton, ImagePlaceholder } from 'flowbite-svelte';
  import {
    HomeSolid,
    WalletSolid,
    AdjustmentsVerticalOutline,
    UserCircleSolid,
    PlusOutline,
  } from 'flowbite-svelte-icons';

  let dummyDrawingList: { name: string; date: any }[] = [
    { name: '1번드로잉', date: '2024-09-04' },
    { name: '2번드로잉', date: '2024-09-05' },
    { name: '3번드로잉', date: '2024-09-06' },
  ];
</script>

<div id="homepage-body">
  <div id="homepage-header">
    <p>유저 이름</p>
    <h2>오운완했나요?</h2>
  </div>
  <div id="homepage-drawing">
    <p>진행 중인 드로잉</p>
    <div id="drawing-list">
      {#each dummyDrawingList as drawing}
        <div id="ongoing-drawing">
          <div id="route-image">
            <img src="http://via.placeholder.com/150x150" alt="" />
          </div>
          <div id="route-info">
            <p>{drawing.name}</p>
            <p>{drawing.date}</p>
          </div>
        </div>
      {/each}
    </div>
  </div>
  <div id="homepage-chart">
    <div>차트가보여질거여요</div>
  </div>
  <div id="homepage-tabbar">
    <Skeleton class="py-4" />
    <ImagePlaceholder class="pb-20" />

    <BottomNav position="absolute" navType="application" classInner="grid-cols-5">
      <BottomNavItem btnName="Home" appBtnPosition="left">
        <HomeSolid
          class="w-6 h-6 mb-1 text-gray-500 dark:text-gray-400 group-hover:text-primary-600 dark:group-hover:text-primary-500"
        />
        <Tooltip arrow={false}>Home</Tooltip>
      </BottomNavItem>
      <BottomNavItem btnName="Wallet" appBtnPosition="middle">
        <WalletSolid
          class="w-6 h-6 mb-1 text-gray-500 dark:text-gray-400 group-hover:text-primary-600 dark:group-hover:text-primary-500"
        />
        <Tooltip arrow={false}>Wallet</Tooltip>
      </BottomNavItem>
      <div class="flex items-center justify-center">
        <BottomNavItem
          btnName="Create new item"
          appBtnPosition="middle"
          btnClass="inline-flex items-center justify-center w-10 h-10 font-medium bg-primary-600 rounded-full hover:bg-primary-700 group focus:ring-4 focus:ring-primary-300 focus:outline-none dark:focus:ring-primary-800"
        >
          <PlusOutline class="text-white" />
          <Tooltip arrow={false}>Create new item</Tooltip>
        </BottomNavItem>
      </div>
      <BottomNavItem btnName="Settings" appBtnPosition="middle">
        <AdjustmentsVerticalOutline
          class="w-6 h-6 mb-1 text-gray-500 dark:text-gray-400 group-hover:text-primary-600 dark:group-hover:text-primary-500"
        />
        <Tooltip arrow={false}>Settings</Tooltip>
      </BottomNavItem>
      <BottomNavItem btnName="Profile" appBtnPosition="right">
        <UserCircleSolid
          class="w-6 h-6 mb-1 text-gray-500 dark:text-gray-400 group-hover:text-primary-600 dark:group-hover:text-primary-500"
        />
        <Tooltip arrow={false}>Profile</Tooltip>
      </BottomNavItem>
    </BottomNav>
    <a use:link href="/makeroute">MakeRoute</a>
    <a use:link href="/drawingmap">Drawing Map</a>
  </div>
</div>

<style>
  #homepage-body {
    margin: 0;
    width: 768px;
    background: var(--white-bg-color, #f9f8ef);
    display: flex;
    flex-direction: column;
  }

  #homepage-header {
    justify-self: left;
    height: 25vh;
    padding: 15vh 0vh 10vh 0vh;
  }

  #homepage-header h2 {
    font-size: 24px;
    font-weight: bold;
  }

  #homepage-drawing {
    height: 30vh;
  }

  #drawing-list {
    display: flex;
    height: 90%;
    justify-content: space-around;
    align-items: center;
  }

  #ongoing-drawing {
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    border: red 1px solid;
  }

  #homepage-chart {
    height: 30vh;
  }

  #homepage-tabbar {
    height: 15vh;
  }
</style>
