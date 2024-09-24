<script>
  const form = createForm({
    initialValues: { name: '', email: '' },
    validate: (values) => {
      const errors = {};
      if (!values.name) {
        errors.name = 'Name is required';
      }
      if (!values.email) {
        errors.email = 'Email is required';
      }
      return errors;
    },
    onSubmit: async (values) => {
      console.log('Form Submitted:', values);
    },
  });
</script>

<div>회원가입 페이지입니다.</div>
