<script lang="ts">
  import './app.css';
  import Router, { replace } from 'svelte-spa-router';
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
    if (isAuthenticated) {
      replace('/'); // 로그인 상태면 홈 페이지로 이동
    } else {
      replace('/login'); // 비로그인 상태면 로그인 페이지로 이동
    }
  });

  $: routes = {
    '/makeroute': !isAuthenticated ? LoginPage : MakeRoutePage,
    '/drawingmap': !isAuthenticated ? LoginPage : DrawingPage,
    '/drawingcapture': !isAuthenticated ? LoginPage : DrawingCapturePage,
    '/calendar': !isAuthenticated ? LoginPage : CalendarPage,
    '/drawingdetail': !isAuthenticated ? LoginPage : DrawingDetailPage,
    '/': !isAuthenticated ? LoginPage : HomePage,
    '/routelist': !isAuthenticated ? LoginPage : RouteListPage,
    '/community': !isAuthenticated ? LoginPage : CommunityPage,
    '/community/mystorage': !isAuthenticated ? LoginPage : MyStoragePage,
    '/myhealth': !isAuthenticated ? LoginPage : MyHealthPage,
    '/progress': !isAuthenticated ? LoginPage : ProgressPage,

    '/signup': isAuthenticated ? LoginPage : SignUpPage,
    '/login': isAuthenticated ? LoginPage : LoginPage,
    '*': isAuthenticated ? HomePage : LoginPage,
  };
</script>

<Router {routes} />
