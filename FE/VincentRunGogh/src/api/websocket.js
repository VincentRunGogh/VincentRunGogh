// src/websocket.ts
import { writable } from 'svelte/store';
import * as SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

export const isConnected = writable(false);

let stompClient = null;
let trackingIntervalId = null;

export function connectWebSocket(onConnectedCallback, onDisconnectCallback) {
  const socket = new SockJS(import.meta.env.VITE_API_BASE_URL + '/ws');
  console.log(socket);
  stompClient = Stomp.over(socket);
  const accessToken = localStorage.getItem('access_token');
  const headers = { Authorization: `Bearer ${accessToken}` };

  stompClient.connect(
    headers,
    (frame) => {
      isConnected.set(true);
      console.log('Connected: ' + frame);
      if (onConnectedCallback) {
        onConnectedCallback();
      }
    },
    (error) => {
      isConnected.set(false);
      console.error('Error:', error);
    }
  );
}

export function disconnectWebSocket() {
  if (stompClient !== null) {
    stompClient.disconnect(() => {
      console.log('Disconnected');
      isConnected.set(false);
    });
  }
  clearInterval(trackingIntervalId);
}

export function sendRealTimePosition(location, nickname) {
  const data = { ...location, time: new Date().getTime() };

  console.log(data); // 위치 데이터를 서버로 보냅니다.
  if (stompClient && stompClient.connected) {
    stompClient.send(`/pub/running/${nickname}`, {}, JSON.stringify(data));
  }
}
