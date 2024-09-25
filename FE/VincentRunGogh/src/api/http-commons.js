import axios from 'axios';

import { successAlert, errorAlert } from '@/utils/notificationAlert';

function Axios() {
  const instance = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL,
    headers: {
      'Content-Type': 'application/json;charset=utf-8',
    },
    withCredentials: true,
  });

  // instance.defaults.headers.post['Content-Type'] = 'application/json';
  // instance.defaults.headers.put['Content-Type'] = 'application/json';

  instance.interceptors.response.use(
    (response) => response,
    (error) => {
      errorAlert(error.response.data.message);
      return Promise.reject(error);
    }
  );
  return instance;
}

async function tokenRegeneration() {
  const refreshToken = localStorage.getItem('refreshToken');
  const response = await Axios().post('/auth/token/reissue');
  return response;
}

function loginAxios() {
  const refreshToken = localStorage.getItem('refreshToken');

  const instance = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL,
    headers: {
      'Content-Type': 'application/json;charset=utf-8',
    },
    withCredentials: true,
  });

  instance.defaults.headers.post['Content-Type'] = 'application/json';
  instance.defaults.headers.put['Content-Type'] = 'application/json';

  instance.interceptors.request.use(
    (config) => {
      const accessToken = localStorage.getItem('accessToken');
      config.headers['Content-Type'] = 'application/json';
      config.headers['Authorization'] = `Bearer ${accessToken}`;

      return config;
    },
    (error) => {
      console.log(error.toJSON());
      return Promise.reject(error);
    }
  );

  instance.interceptors.response.use(
    (response) => response,
    async (error) => {
      const originalRequest = error.config;
      if (error.response.status === 401) {
        try {
          const response = await tokenRegeneration();
          const newAccessToken = response.data.data.accessToken;
          localStorage.setItem('accessToken', response.data.accessToken);
          axios.defaults.headers.common.Authorization = `Bearer ${newAccessToken}`;
          // localStorage.setItem('refreshToken', response.data.refreshToken);
          //진행중이던 요청 이어서하기
          originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;
          return axios(originalRequest);
        } catch (error) {
          successAlert('다시 로그인 해주세요', () => {
            window.location.replace('/app/login');
            localStorage.removeItem('userStore');
            return new Promise(() => {});
          });
        }
      } else return Promise.reject(error);
    }
  );
  return instance;
}

export { Axios, loginAxios };
