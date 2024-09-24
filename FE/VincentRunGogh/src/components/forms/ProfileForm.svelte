<script lang="ts">
  import { writable, derived } from 'svelte/store';
  import { Input, Fileupload, Label, Button, Helper, ButtonGroup } from 'flowbite-svelte';
  import { link } from 'svelte-spa-router';

  import { onMount } from 'svelte';
  import { formStore } from '@/stores/formStore';
  import { userStore } from '@/stores/userStore';

  export let submitAttempt = false;

  const {
    values,
    helpers,
    validateNickname,
    validateHeight,
    validateWeight,
    validateProfileImage,
    checkNicknameAvailability,
  } = formStore;

  async function handleCheckNickname() {
    await checkNicknameAvailability($values.nickname);
  }
  // 이미지 업로드 핸들러
  function handleImageUpload(event) {
    const file = event.target.files[0];
    if (file) {
      validateProfileImage(file);
      values.update((v) => ({ ...v, profileImage: file }));
    }
  }
  onMount(() => {
    userStore.subscribe(({ nickname, height, weight }) => {
      values.update((v) => ({
        ...v,
        nickname: nickname.toString(),
        height: height.toString(),
        weight: weight.toString(),
      }));
    });
  });
</script>

<div class="mb-6">
  <Label for="nickname" class="block mb-2">별명</Label>
  <ButtonGroup class="w-full">
    <Input
      id="nickname"
      type="text"
      placeholder="별명"
      bind:value={$values.nickname}
      on:input={(e) => validateNickname(e.target.value)}
    />
    <Button on:click={handleCheckNickname}>중복 확인</Button>
  </ButtonGroup>
  <Helper style="color: {$helpers.nickname.color};">{$helpers.nickname.message}</Helper>
</div>

<div class="mb-6">
  <Label for="height" class="block mb-2">키</Label>
  <Input
    id="height"
    type="text"
    placeholder="키"
    bind:value={$values.height}
    on:input={(e) => validateHeight(e.target.value)}
  />
  <Helper style="color: {$helpers.height.color};">{$helpers.height.message}</Helper>
</div>

<div class="mb-6">
  <Label for="weight" class="block mb-2">몸무게</Label>
  <Input
    id="weight"
    type="text"
    placeholder="몸무게"
    bind:value={$values.weight}
    on:input={(e) => validateWeight(e.target.value)}
  />
  <Helper style="color: {$helpers.weight.color};">{$helpers.weight.message}</Helper>
</div>

<div class="mb-6">
  <Label for="profileImg" class="pb-2">프로필 이미지 변경</Label>
  <input type="file" id="profileImg" on:change={handleImageUpload} />
  <Helper style="color: {$helpers.profileImage.color};">{$helpers.profileImage.message}</Helper>
</div>
