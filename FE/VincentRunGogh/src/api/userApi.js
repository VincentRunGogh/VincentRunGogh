import { loginAxios } from '@/api/http-commons';
const http = loginAxios();

async function getProfile(success, fail) {
  await http.put('/users', { image: image }).then(success).catch(fail);
}
async function updateProfileImg(image, success, fail) {
  await http.put('/users/profile-image', { image: image }).then(success).catch(fail);
}
async function updateProfile(data, success, fail) {
  await http.put('/users', data).then(success).catch(fail);
}
async function updatePw(password, success, fail) {
  await http.put('/users/password', { password: password }).then(success).catch(fail);
}

async function logout(success, fail) {
  await http.post(`/auth/logout/${loginId}`).then(success).catch(fail);
}

export { getProfile, updateProfileImg, updateProfile, updatePw, logout };
