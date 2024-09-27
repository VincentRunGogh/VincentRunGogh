import { LatLng } from 'leaflet';
import { writable } from 'svelte/store';
import type { Writable } from 'svelte/store';
interface Position {
  lat: number;
  lng: number;
}

export const isLockScreen = writable<boolean>(false);
export const isPause = writable(false);
export const totalDistance = writable(0);
export const averageSpeed = writable(0);
export const elapsedTime = writable(0);
export const posList = writable([
  { latlng: { lat: 36.3718192, lng: 127.4102336 } as LatLng, time: 1726651413273, speed: 3 },
  { latlng: { lat: 36.3715, lng: 127.4102336 } as LatLng, time: 1726651413275, speed: 5 },
  { latlng: { lat: 36.3711, lng: 127.4112336 } as LatLng, time: 1726651413275, speed: 10 },
]);
export const route = writable([]);

interface DrawingInfo {
  title: string;
  drawingPositionList?: Position[];
  routePositionList?: Position[];
}

// 초기 상태 정의
const initialState: DrawingInfo = {
  title: '',
  drawingPositionList: [],
  routePositionList: [],
};

// drawingStore를 생성하고 초기 상태를 할당
export const drawingStore: Writable<DrawingInfo> = writable(initialState);

// API 응답을 처리하여 스토어 업데이트
export function updateDrawingInfo(data: Partial<DrawingInfo>): void {
  drawingStore.set({
    title: data.title || '',
    drawingPositionList: data.drawingPositionList || [],
    routePositionList: data.routePositionList || [],
  });
}
