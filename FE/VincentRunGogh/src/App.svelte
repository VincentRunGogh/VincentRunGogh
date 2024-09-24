<script lang="ts">
  import { wrap } from 'svelte-spa-router/wrap';
  import Router from 'svelte-spa-router';

  import { userStore } from '@/stores/userStore';
  import {
    DrawingDetailPage,
    DrawingPage,
    MakeRoutePage,
    DrawingCapturePage,
    CalendarPage,
    RouteListPage,
    HomePage,
    CommunityPage,
    MyStoragePage,
    LoginPage,
    SignUpPage,
    MyHealthPage,
    ProgressPage,
  } from '@/pages';

  userStore.initialize();

  // Check if the user is authenticated
  function isAuthenticated() {
    let auth = false;
    userStore.subscribe(($user) => {
      auth = $user !== null;
    });
    return auth;
  }

  // Redirect if not authenticated
  function requireAuth() {
    if (isAuthenticated()) {
      return wrap({
        component: HomePage,
      });
    } else {
      return wrap({
        component: LoginPage,
      });
    }
  }

  const routes = {
    '/': requireAuth(),
    '/makeroute': requireAuth(),
    '/drawingmap': requireAuth(),
    '/drawingcapture': requireAuth(),
    '/calendar': requireAuth(),
    '/drawingdetail': requireAuth(),
    '/routelist': requireAuth(),
    '/community': requireAuth(),
    '/community/mystorage': requireAuth(),
    '/login': LoginPage,
    '/signup': SignUpPage,
    '/myhealth': requireAuth(),
    '/progress': requireAuth(),
    '*': requireAuth(), // Fallback route
  };
</script>

<Router {routes} />
