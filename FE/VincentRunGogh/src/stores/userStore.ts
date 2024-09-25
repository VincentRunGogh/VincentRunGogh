import { writable } from 'svelte/store';

// Assuming UserAuth is defined somewhere if you're using it
interface UserAuth {
  id: string;
  email: string;
}

interface User extends UserAuth {
  nickname: string;
  birth: string;
  gender: number;
  profileImage: string;
  height: number;
  weight: number;
}

function createUserStore() {
  const { subscribe, set, update } = writable<User | null>(null);

  function login(userData: User) {
    try {
      localStorage.setItem('user', JSON.stringify(userData));
      set(userData);
    } catch (error) {
      console.error('Failed to save user to localStorage:', error);
    }
  }

  function logout() {
    try {
      localStorage.removeItem('user');
      set(null);
    } catch (error) {
      console.error('Failed to remove user from localStorage:', error);
    }
  }

  function updateUser(partialData: Partial<User>) {
    update((current) => {
      const updatedUser = { ...current, ...partialData };
      try {
        console.log(updatedUser);
        localStorage.setItem('user', JSON.stringify(updatedUser));
      } catch (error) {
        console.error('Failed to update user in localStorage:', error);
      }
      return updatedUser;
    });
  }

  function initialize() {
    try {
      const storedUser = localStorage.getItem('user');
      if (storedUser) {
        set(JSON.parse(storedUser));
      }
    } catch (error) {
      console.error('Failed to parse user from localStorage:', error);
    }
  }

  return {
    subscribe,
    login,
    logout,
    updateUser,
    initialize,
    setProfile: (userData: User) => login(userData),
    clearProfile: logout,
  };
}

export const userStore = createUserStore();
