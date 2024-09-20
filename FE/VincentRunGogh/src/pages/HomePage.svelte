<script lang="ts">
  import Tabbar from '@/components/tabbar/Tabbar.svelte';

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
