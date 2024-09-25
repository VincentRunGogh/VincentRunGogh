import { writable } from 'svelte/store';

interface UserAuth {
  nickname: string | null;
}
interface User extends UserAuth {
  height: number | null;
  weight: number | null;
  profileImage: string | null;
}

function createUserStore() {
  const { subscribe, set, update } = writable<User | UserAuth | null>(null);

  function login(user: UserAuth) {
    localStorage.setItem('user', JSON.stringify(user));
    set(user);
  }

  function logout() {
    localStorage.removeItem('user');
    set(null);
  }
  function updateUser(user: User) {
    update((prevUser) => ({
      ...prevUser,
      ...user,
    }));
    localStorage.setItem('user', JSON.stringify(user));
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
