// import 컴포넌트
// @ts-ignore
import MakeRoutePage from "@/components/MakeRoutePage.svelte";
import { DrawingPage } from "../pages";
export default {
  // 링크 : 컴포넌트
  // :param, *any
  "/makeroute": MakeRoutePage,
  "/drawingmap": DrawingPage,
};
