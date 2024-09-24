<script lang="ts">
  import { userStore } from '../stores/userStore';
  import { replace, link } from 'svelte-spa-router';

  import { Button, Modal, Label, Input, Checkbox } from 'flowbite-svelte';

  let id = '';
  let password = '';

  const handleLogin = async (event: SubmitEvent) => {
    event.preventDefault();
    // const isSuccess = await login(id,password); // performLogin은 예시 함수입니다.
    // if (isSuccess) {
    //   userStore.set({ id: '123', nickname: 'User' }); // 사용자 정보 설정 예시
    //   push('/'); // 홈페이지로 이동
    // } else {
    //   console.error('로그인 실패');
    // }
  };
</script>

<form on:submit|preventDefault={handleLogin} class="flex flex-col space-y-6">
  <h3 class="mb-4 text-xl font-medium text-gray-900 dark:text-white">로그인</h3>
  <Label class="space-y-2">
    <span>아이디</span>
    <Input type="email" name="email" placeholder="name@company.com" required bind:value={id} />
  </Label>
  <Label class="space-y-2">
    <span>비밀번호</span>
    <Input type="password" name="password" required bind:value={password} />
  </Label>
  <div class="flex items-start">
    <a
      use:link
      href="/"
      class="ms-auto text-sm text-primary-700 hover:underline dark:text-primary-500"
    >
      비밀번호 찾기
    </a>
    <div class="text-sm font-medium text-gray-500 dark:text-gray-300">
      계정이 없으신가요? <a
        use:link
        href="/"
        class="text-primary-700 hover:underline dark:text-primary-500"
      >
        Create account
      </a>
    </div>
  </div>
  <Button type="submit" class="w-full1">로그인</Button>
</form>
