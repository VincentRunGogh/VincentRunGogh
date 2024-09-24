// src/stores/userStore.ts
import { writable } from 'svelte/store';

export const userStore = writable({
  height: 0,
  weight: 0,
  nickname: '',
});
