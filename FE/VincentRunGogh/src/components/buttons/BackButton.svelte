<script>
  import { AngleLeftOutline } from 'flowbite-svelte-icons';
  import { replace } from 'svelte-spa-router';
  import Swal from 'sweetalert2';

  function clickBackButton() {
    Swal.fire({
      title: '홈으로 이동하시겠습니까?',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: '홈으로',
      cancelButtonText: '취소',
    }).then((result) => {
      if (result.isConfirmed) {
        replace('/');
        Swal.close();
      }
    });
  }
</script>

<button id="backbutton" on:click={clickBackButton}>
  <AngleLeftOutline />
</button>

<style>
  #backbutton {
    position: fixed;
    top: 4%;
    left: 4%;
    z-index: 1000;
  }
</style>
