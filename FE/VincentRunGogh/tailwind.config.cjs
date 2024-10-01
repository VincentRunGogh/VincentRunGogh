import flowbitePlugin from 'flowbite/plugin'


export default {
  content: ['./src/**/*.{html,js,svelte,ts}', './node_modules/flowbite-svelte/**/*.{html,js,svelte,ts}'],
  darkMode: 'selector',
  theme: {
    extend: {
      colors: {
        // flowbite-svelte
        primary: {
          50: '#FFF5F2',
          100: '#FFF1EE',
          200: '#FFE4DE',
          300: '#FFD5CC',
          400: '#FFBCAD',
          500: '#FE795D',
          600: '#FFB800', //메인 노랑색
          700: '#E0AA1E', //메인 노랑 어둡게
          800: '#CC4522',
          900: '#A5371B'
        },
        transparent: 'transparent',
        current: 'currentColor',
        'yellow-main': '#FFB800',
        'green-main': '#5E8358',
      }, gradientColorStops: theme => ({
        'yellow-linear-start': '#FAFDB1',
        'yellow-linear-end': '#FFC326',
        'red-linear-start': '#FF9693',
        'red-linear-end': '#FF4040',
        'green-linear-start': '#A2FEA5',
        'green-linear-end': '#33A03B',
      }),
      backgroundImage: theme => ({
        'yellow-gradient': `linear-gradient(95%, ${theme('colors.yellow-linear-start')} 89%, ${theme('colors.yellow-linear-end')} 95%)`,
        'red-gradient': `linear-gradient(100%, ${theme('colors.red-linear-start')}, ${theme('colors.red-linear-end')})`,
        'green-gradient': `linear-gradient(100%, ${theme('colors.green-linear-start')}, ${theme('colors.green-linear-end')})`,
      }),
    }
  },

  plugins: [flowbitePlugin]
} 