<script lang="ts">
  import './app.css';
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

  let isAuthenticated = false;
  userStore.subscribe(($user) => {
    isAuthenticated = $user !== null;
  });

  const routes = {
    // 링크 : 컴포넌트
    // :param, *any
    '/makeroute': MakeRoutePage,
    '/drawingmap': DrawingPage,
    '/drawingcapture': DrawingCapturePage,
    '/calendar': CalendarPage,
    '/drawingdetail': DrawingDetailPage,
    '/': isAuthenticated ? HomePage : LoginPage,
    '/routelist': RouteListPage,
    '/community': CommunityPage,
    '/community/mystorage': MyStoragePage,
    '/login': LoginPage,
    '/signup': SignUpPage,
    '/myhealth': MyHealthPage,
    '/progress': ProgressPage,

    '*': isAuthenticated ? HomePage : LoginPage,
  };
</script>

<Router {routes} />
