module.exports = {
  root: true,
  parserOptions: {
    ecmaVersion: 2020,
    sourceType: 'module',
  },
  extends: ['eslint:recommended', 'plugin:svelte3/recommended', 'plugin:prettier/recommended'],
  plugins: ['svelte3', 'prettier'],
  overrides: [
    {
      files: ['*.svelte'],
      processor: 'svelte3/svelte3',
    },
  ],
  settings: {
    'svelte3/ignore-styles': () => true, // if you're using style preprocessors like SCSS
  },
  rules: {
    'prettier/prettier': 'error', // Show prettier errors as ESLint errors
  },
};
