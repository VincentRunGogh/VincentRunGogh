// import 컴포넌트
// @ts-ignore
import HomePage from '@/pages/HomePage.svelte';
import { DrawingPage, MakeRoutePage } from '../pages';
import RouteListPage from '@/pages/RouteLIstPage.svelte';
export default {
  // 링크 : 컴포넌트
  // :param, *any
  '/': HomePage,
  '/makeroute': MakeRoutePage,
  '/drawingmap': DrawingPage,
  '/routelist': RouteListPage,
};
