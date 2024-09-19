// import 컴포넌트
// @ts-ignore
import { DrawingPage, MakeRoutePage, HomePage, RouteListPage } from '../pages';
export default {
  // 링크 : 컴포넌트
  // :param, *any
  '/': HomePage,
  '/makeroute': MakeRoutePage,
  '/drawingmap': DrawingPage,
  '/routelist': RouteListPage,
};
