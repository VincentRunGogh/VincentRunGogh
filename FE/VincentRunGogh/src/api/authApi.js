import { Axios, loginAxios } from '@/api/http-commons';

const http = Axios();

async function signup(data, success, fail) {
  await http.post('/auth/signup', data).then(success).catch(fail);
}
async function login(email, pw, success, fail) {
  const data = {
    email: email,
    password: pw,
  };
  await http.post('/auth/login', data).then(success).catch(fail);
}

async function checkEmailDuplication(email, success, fail) {
  await http.get(`/auth/find-email?email=${email}`).then(success).catch(fail);
}
async function checkNickNameDuplication(nickname, success, fail) {
  await http.get(`/auth/find-nickname?nickname=${nickname}`).then(success).catch(fail);
}

async function sendEmailVerification(email, success, fail) {
  await http.post(`/auth/code/send`, { email: email }).then(success).catch(fail);
}
async function checkEmailVerification(email, code, success, fail) {
  const data = {
    email: email,
    code: code,
  };
  await http.post(`/auth/code/send`, data).then(success).catch(fail);
}
async function checkVerifyCode(type, data, success, fail) {
  let typeurl = '';
  if (type === 'findid') {
    typeurl = '-id';
  } else if (type === 'findpassword') {
    typeurl = '-pw';
  }
  await http.post(`/auth/phone-verify-code${typeurl}-check`, data).then(success).catch(fail);
}

async function updateUserPwd(type, data, success, fail) {
  switch (type) {
    case 'find':
      await http.put('/auth/password-not-login', data).then(success).catch(fail);
      break;
    case 'update':
      const loginHttp = loginAxios();
      await loginHttp.put('/auth/password', data).then(success).catch(fail);
  }
}

// async function logout(loginId, success, fail) {
//   await http.get(`/auth/logout/${loginId}`).then(success).catch(fail);
// }

export {
  registerUser,
  loginApi,
  updateUserPwd,
  checkIdDuplication,
  reqVerifyCode,
  checkVerifyCode,
  getNFCToken,
};
