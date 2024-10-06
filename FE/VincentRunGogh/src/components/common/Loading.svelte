<script>
  import { Spinner } from 'svelte-flowbite';
  import { writable } from 'svelte/store';

  let isLoading = writable(true); // 로딩 상태 관리
</script>

{#if $isLoading}
  <div class="overlay">
    <Spinner />
  </div>
{/if}

<div>
  <!-- Page Content -->
  여기에 페이지 컨텐츠가 위치합니다.
</div>

<style>
  .overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 999;
  }
</style>
