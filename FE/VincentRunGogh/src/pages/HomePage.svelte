<script lang="ts">
  import { link } from 'svelte-spa-router';
  
  let dummyDrawingList: {name:string, date:Date}[] = [
    {name: '1번드로잉', date: new Date()},
    {name: '2번드로잉', date: new Date()},
    {name: '3번드로잉', date: new Date()},
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

  </div>
  <div id="homepage-tabbar">
    <a use:link href="/makeroute">MakeRoute</a>
    <a use:link href="/drawingmap">Drawing Map</a>
  </div>
</div>

<style>
  #homepage-body {
    display: flex;
    flex-direction: column;
    place-items: center;
  }

  #ongoing-drawing {
    display: flex;
    justify-content: space-around;
  }
</style>
