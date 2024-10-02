<script lang="ts">
  import { Navbar } from 'flowbite-svelte';
  import BackButton from '@components/buttons/BackButton.svelte';
  export let title: string = '';
</script>

<div id="header" class="flex justify-center items-center text-center">
  <BackButton />
  <h2 class="text-center">
    {title}
  </h2>
</div>

<style>
  #header {
    height: 10vh;
    width: 100%;
  }
</style>
