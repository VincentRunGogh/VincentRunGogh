import { Axios, loginAxios } from '@/api/http-commons';

const http = Axios();

// 일주일 운동 정보 조회
export function getWeeklyInfo() {
  return http
    .get('/users/week', {
      headers: {
        'Content-Type': 'application/json',
        Authorization:
          'Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsImlhdCI6MTcyNzI0Mjc2NywiZXhwIjoxNzI3MjY0MzY3fQ.0wEilIhg4_GOZMm8CyISGz4KCcm_omTYWhz1_t3zgA8',
      },
    })
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
}
