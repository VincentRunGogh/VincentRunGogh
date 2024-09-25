<script lang="ts">
  import { writable, derived } from 'svelte/store';
  import { Card, Modal, Listgroup, Button } from 'flowbite-svelte';
  import { link } from 'svelte-spa-router';

  import { onMount } from 'svelte';
  import { EditOutline } from 'flowbite-svelte-icons';
  import {
    ChartLineUpOutline,
    ClockOutline,
    UserCircleSolid,
    LockOutline,
    ArrowRightToBracketOutline,
  } from 'flowbite-svelte-icons';
  import BMIChart from '@/components/myhealth/BMIChart.svelte';
  import ProfileForm from '@/components/forms/ProfileForm.svelte';
  import { profileFormStore } from '@/stores/profileFormStore';

  let userInfo;

  let profileImage = writable<string>('');
  let nickname = writable<string>('');
  let birth = writable<string>('');
  let gender = writable<number>(0);
  let height = writable<number>(0);
  let weight = writable<number>(0);

  function getGenderSymbol(gender: number): string {
    return gender === 0 ? '♂' : '♀';
  }
  const bmi = derived([height, weight], ([$height, $weight]) => {
    if ($height > 0 && $weight > 0) {
      let heightInMeters = $height / 100;
      return $weight / (heightInMeters * heightInMeters);
    }
    return 0;
  });

  function getBMICategory(bmi: number): string {
    if (bmi >= 30) return '비만';
    else if (bmi >= 25) return '과체중';
    else if (bmi > 18.5) return '정상';
    else return '저체중';
    return '';
  }
  onMount(async () => {
    // const response = await fetch('api/profiles'); // 실제 API 주소로 대체
    // const result = await response.json();

    // if (result.status === 200) {
    //   nickname.set(result.data.nickname);
    //   birth.set(result.data.birth);
    //   gender.set(result.data.gender);
    //   profileImage.set(result.data.profile);
    //   height.set(result.data.height);
    //   weight.set(result.data.weight);
    // }
    nickname.set('김싸피');
    birth.set('1999-10-04');
    gender.set(1);
    profileImage.set('./default.png');
    height.set(155);
    weight.set(44);
  });
  interface MenuItem {
    name: string;
    icon: any; // any 대신 적절한 타입 지정
    url: any;
  }
  let dataMenuIcons: MenuItem[] = [
    { name: '기록', icon: ClockOutline, url: '/history' },
    { name: '통계', icon: ChartLineUpOutline, url: '/progress' },
  ];

  let accountMenuIcons: MenuItem[] = [
    { name: '비밀번호 변경', icon: LockOutline, url: '/changepassword' },
    { name: '로그아웃', icon: ArrowRightToBracketOutline, url: '/logout' },
  ];

  let editFormModal: boolean = false;
  let submitAttempt: boolean = false;
  function closeForm() {
    editFormModal = false;
    submitAttempt = false;
  }

  const onClickEditBtn = () => {
    editFormModal = true;
  };
  // 폼 제출 함수
  const { values, helpers } = profileFormStore;
  async function submitForm() {
    submitAttempt = true;
    const allValid = Object.values($helpers).every((helper) => helper.color === 'green');

    if (allValid) {
      // Perform API submission
      const response = await fetch('/api/update-profile', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify($values),
      });

      if (response.ok) {
        closeForm();
      } else {
        alert('Failed to update profile.');
      }
    } else {
      alert('Please correct the errors before submitting.');
    }
  }
</script>

<!-- // SECTION - profile -->
<div class="flex items-center space-x-4 rtl:space-x-reverse">
  <div class="wrap">
    <img src={$profileImage} alt="프로필 이미지" class="main-profile-img" />
    <span class="icon" on:click={onClickEditBtn}><EditOutline /></span>
  </div>
  <div class="space-y-1 font-medium dark:text-white">
    <div>{$nickname}</div>
    <div class="text-sm text-gray-500 dark:text-gray-400">{$birth} {getGenderSymbol($gender)}</div>
  </div>
</div>
<Modal title="프로필 변경" bind:open={editFormModal} autoclose={false}>
  <ProfileForm isSignup={false} />
  <!-- Modal 하단 버튼 -->

  <svelte:fragment slot="footer">
    <Button on:click={submitForm}>수정</Button>
    <Button color="alternative" on:click={closeForm}>취소</Button>
  </svelte:fragment>
</Modal>
<!-- //SECTION - bmi -->
<Card padding="md">
  <h5 class="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">BMI</h5>
  <BMIChart bmi={$bmi} />

  <p class="font-normal text-gray-700 dark:text-gray-400 leading-tight">
    {$nickname}님의 체질량지수는 {getBMICategory($bmi)}입니다.
  </p>
  <p class="font-normal text-gray-400 dark:text-gray-200">
    BMI는 근육량, 유전적 원인, 다른 개인적 차이를 반영하지 않습니다.
  </p>
</Card>
<!-- //!SECTION - menu -->
<Listgroup active items={dataMenuIcons} let:item>
  <a use:link href={item.url}>
    <svelte:component this={item.icon} class="w-4 h-4 me-2.5" />
    {item.name}
  </a>
</Listgroup>

<Listgroup active items={accountMenuIcons} let:item>
  <a use:link href={item.url}>
    <svelte:component this={item.icon} class="w-4 h-4 me-2.5" />
    {item.name}
  </a>
</Listgroup>

<style>
  /* canvas {
    display: block;
    max-width: 100%;
    height: auto;
  } */
  .main-profile-img {
    width: 20vw;
    height: 20vw;
    border-radius: 50%;
    border-style: solid;
    border-color: #ffffff;
    box-shadow: 0 0 8px 3px #b8b8b8;
    position: relative;
  }

  .wrap {
    position: relative;
  }

  .edit-button {
    position: absolute;
    width: 30px;
    height: 30px;
    border-radius: 50%;
    border: 1px solid grey;
    top: 0;
    left: 113px;
    background: white;
    color: blue;
  }
  span.icon {
    position: absolute;
    top: 10px;
    right: 0;
    background: #e2e2e2;
    border-radius: 100%;
    width: 30px;
    height: 30px;
    line-height: 30px;
    vertical-align: middle;
    text-align: center;
    font-size: 14px;
    cursor: pointer;
    display: flex;
    justify-content: center;
    align-items: center;
  }
</style>
