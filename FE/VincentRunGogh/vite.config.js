import { defineConfig } from 'vite';
import { svelte } from '@sveltejs/vite-plugin-svelte';
import { VitePWA } from 'vite-plugin-pwa';

export default defineConfig({
  plugins: [
    svelte(),
    VitePWA({
      registerType: 'autoUpdate',
      devOptions: {
        enabled: false // 개발 중에는 서비스 워커를 비활성화
      },
      includeAssets: ['favicon.svg', 'favicon.ico', 'android-chrome-192x192.png','android-chrome-512x512.png' , 'apple-touch-icon.png'],
      manifest: {
        name: 'Vincent Run Gogh',
        short_name: 'Vincent Run Gogh',
        description: 'Draw, Run!',
        theme_color: '#ffffff',
        icons: [
          {
            src: 'android-chrome-192x192.png',
            sizes: '192x192',
            type: 'image/png',
          },
          {
            src: 'android-chrome-512x512.png',
            sizes: '512x512',
            type: 'image/png',
          },
          {
            src: 'android-chrome-512x512.png',
            sizes: '512x512',
            type: 'image/png',
            purpose: 'any maskable',
          },
        ],
      },
    }),
  ],
});
