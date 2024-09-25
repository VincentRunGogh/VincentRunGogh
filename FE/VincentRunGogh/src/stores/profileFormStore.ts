import { writable, derived, get } from 'svelte/store';

interface FormValues {
  nickname: string;
  height: string;
  weight: string;
  profileImage: File | null;
}

interface HelperMessage {
  message: string;
  color: string;
}

function createFormStore() {
  const values = writable<FormValues>({ nickname: '', height: '', weight: '', profileImage: null });
  const helpers = writable({
    nickname: { message: '영문, 한글, 숫자 10자 이하', color: 'gray' },
    height: { message: '키를 숫자로 입력해주세요.', color: 'gray' },
    weight: { message: '몸무게를 숫자로 입력해주세요.', color: 'gray' },
    profileImage: { message: 'SVG, PNG, JPG', color: 'green' },
  });

  function validateNickname(value: string) {
    const regex = /^[A-Za-z가-힣0-9]{1,10}$/;
    if (!regex.test(value)) {
      helpers.update((h) => ({
        ...h,
        nickname: {
          message: '올바른 형식이 아닙니다. (영문, 한글, 숫자 10자 이하)',
          color: 'red',
        },
      }));
    } else {
      helpers.update((h) => ({
        ...h,
        nickname: {
          message: '중복 확인이 필요합니다.',
          color: 'gray',
        },
      }));
    }
  }

  async function checkNicknameAvailability(value: string) {
    // try {
    //   const response = await fetch(`/api/v1/auth/find-nickname?nickname=${value}`);
    //   const result = await response.json();
    //   if (result.isAvailable) {
    helpers.set({
      ...get(helpers),
      nickname: {
        message: '사용 가능한 닉네임입니다.',
        color: 'green',
      },
    });
    //   } else {
    //     helpers.set({
    //       ...get(helpers),
    //       nickname: {
    //         message: '이미 사용 중인 닉네임입니다.',
    //         color: 'red',
    //       },
    //     });
    //   }
    // } catch (error) {
    //   console.error('에러', error);
    //   helpers.set({
    //     ...get(helpers),
    //     nickname: {
    //       message: '닉네임 검사 중 오류가 발생했습니다.',
    //       color: 'red',
    //     },
    //   });
    // }
  }
  function validateHeight(value: string) {
    const regex = /^\d+$/;
    helpers.update((h) => ({
      ...h,
      height: {
        message: regex.test(value) ? 'Well done!' : '키는 숫자만 입력 가능합니다.',
        color: regex.test(value) ? 'green' : 'red',
      },
    }));
  }

  function validateWeight(value: string) {
    const regex = /^\d+$/;
    helpers.update((h) => ({
      ...h,
      weight: {
        message: regex.test(value) ? 'Well done!' : '몸무게는 숫자만 입력 가능합니다.',
        color: regex.test(value) ? 'green' : 'red',
      },
    }));
  }

  function validateProfileImage(file: File) {
    const validTypes = ['image/jpeg', 'image/png'];
    const isValidType = validTypes.includes(file.type);
    helpers.update((h) => ({
      ...h,
      profileImage: {
        message: isValidType ? '파일이 유효합니다.' : '지원하지 않는 파일 형식입니다.',
        color: isValidType ? 'green' : 'red',
      },
    }));
  }

  return {
    values,
    helpers,
    validateNickname,
    validateHeight,
    validateWeight,
    validateProfileImage,
    checkNicknameAvailability,
  };
}

export const profileFormStore = createFormStore();
