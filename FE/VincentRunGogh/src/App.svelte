<script>
  import Router from 'svelte-spa-router'
  import routes from './routes'
  import { link } from 'svelte-spa-router';
</script>

<a use:link href="/makeroute">MakeRoute</a>
<a use:link href="/drawingmap">Drawing Map</a>
<Router {routes} />

<style>

</style>
