// import 컴포넌트
// @ts-ignore
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
export default {
  // 링크 : 컴포넌트
  // :param, *any
  '/makeroute': MakeRoutePage,
  '/drawingmap': DrawingPage,
  '/drawingcapture': DrawingCapturePage,
  '/calendar': CalendarPage,
  '/drawingdetail': DrawingDetailPage,
  '/': HomePage,
  '/routelist': RouteListPage,
  '/community': CommunityPage,
  '/community/mystorage': MyStoragePage,
  '/login': LoginPage,
  '/signup': SignUpPage,
  '/myhealth': MyHealthPage,
  '/progress': ProgressPage,
};
