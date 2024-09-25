import { Axios, loginAxios } from '@/api/http-commons';

const http = Axios();
const loginHttp = loginAxios();

// 아트 루트화
export function sendArtLine(drawForm) {
  return loginHttp
    .post('/routes', drawForm)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
}

// 루트 최종 생성
export function makeRoute(makeRouteForm) {
  return loginHttp
    .post('/routes/end', makeRouteForm)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
}
