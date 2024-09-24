import { writable } from 'svelte/store';

interface User {
  id: string;
  nickname: string;
  height: number;
  weight: number;
  profileImage: string;
}

function createUserStore() {
  const { subscribe, set, update } = writable<User | null>(null);

  function login(user: User) {
    localStorage.setItem('user', JSON.stringify(user));
    set(user);
  }

  function logout() {
    localStorage.removeItem('user');
    set(null);
  }

  function initialize() {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      set(JSON.parse(storedUser));
    }
  }

  return {
    subscribe,
    login,
    logout,
    initialize,
  };
}

export const userStore = createUserStore();
