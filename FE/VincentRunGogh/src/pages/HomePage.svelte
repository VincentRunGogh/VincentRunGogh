<script lang="ts">
  import { link } from 'svelte-spa-router';

  let dummyDrawingList: {name:string, date:any}[] = [
    {name: '1번드로잉', date: '2024-09-04'},
    {name: '2번드로잉', date: '2024-09-05'},
    {name: '3번드로잉', date: '2024-09-06'},
  ];
</script>

<div id="homepage-body">
  <div id="homepage-header">
    <p>유저 이름</p>
    <h4>오운완했나요?</h4>
  </div>
  <div id="homepage-drawing">
    <p>진행 중인 드로잉</p>
    {#each dummyDrawingList as drawing}
      <div id="ongoing-drawing">
        <div>{drawing.name}</div>
        <div>{drawing.date}</div>
      </div>
    {/each}
  </div>
  <div id="homepage-chart">
    <div>차트가보여질거여요</div>
  </div>
  <div id="homepage-tabbar">
    <a use:link href="/makeroute">MakeRoute</a>
    <a use:link href="/drawingmap">Drawing Map</a>
  </div>
</div>

<style>
  
  #homepage-body {
    margin: 0;
    width: 768px;
    background: var(--white-bg-color, #F9F8EF);
    display: flex;
    flex-direction: column;
    place-items: center;
  }

  #homepage-header {
    height: 25vh;
  }

  #homepage-drawing {
    height: 30vh;
  }

  #ongoing-drawing {
    display: flex;
    justify-content: space-around;
  }

  #homepage-chart {
    height: 30vh;
  }

  #homepage-tabbar {
    height: 15vh;
  }
</style>
