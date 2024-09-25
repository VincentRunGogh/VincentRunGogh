import { Axios, loginAxios } from '@/api/http-commons';

const http = Axios();

// 아트 루트화
export function sendArtLine(drawForm) {
  return http
    .post('/routes', drawForm, {
      headers: {
        'Content-Type': 'application/json',
        Authorization:
          'Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsImlhdCI6MTcyNzE1OTE2MSwiZXhwIjoxNzI3MTgwNzYxfQ.nin-Za8NMFiMmDTrlBy7xEbaOG5B4j8pLhwEwBQlPdc',
      },
    })
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
}
