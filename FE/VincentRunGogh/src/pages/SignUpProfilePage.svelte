<script lang="ts">
  import { Input, Label, Checkbox } from 'flowbite-svelte';

  import ProfileForm from '@/components/forms/ProfileForm.svelte';
  import { signup } from '@/api/authApi';
  import { profileFormStore } from '@/stores/profileFormStore';
  const {values} =profileFormStore;
  let birth: Date | null;
  let gender: 0 | 1 | null;

  const submit = () => {
    const data={
      brith,gender,$values.email
    }
    signup(data)
  };
</script>

<ProfileForm isSignup={true} />

<div class="mb-6">
  <Label for="birth" class="pb-2">생년월일</Label>
  <Input type="date" bind:value={birth} />
</div>
<ul
  class="items-center w-full rounded-lg border border-gray-200 sm:flex dark:bg-gray-800 dark:border-gray-600 divide-x rtl:divide-x-reverse divide-gray-200 dark:divide-gray-600"
>
  <li class="w-full"><Checkbox checked color="yellow" class="p-3">여자</Checkbox></li>
  <li class="w-full"><Checkbox color="yellow" class="p-3">남자</Checkbox></li>
</ul>
