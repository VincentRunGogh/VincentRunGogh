// import 컴포넌트
// @ts-ignore
import { DrawingPage, MakeRoutePage, DrawingCapturePage, CalendarPage } from "../pages";
export default {
  // 링크 : 컴포넌트
  // :param, *any
  "/makeroute": MakeRoutePage,
  "/drawingmap": DrawingPage,
  "/drawingcapture": DrawingCapturePage,
  "/calendar": CalendarPage

};
