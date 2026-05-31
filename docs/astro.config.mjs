// @ts-check
import {defineConfig} from 'astro/config';
import starlight from '@astrojs/starlight';
import starlightThemeVintage from 'starlight-theme-vintage';

// https://astro.build/config
export default defineConfig({
  site: "https://biomes.lumas.dev",
  integrations: [
    starlight({
      plugins: [starlightThemeVintage()],
      title: '',
      logo: {
        src: './src/assets/biomesapi.png',
      },
      //favicon: '/biomesapi.png',
      social: [
        { icon: 'github', label: 'GitHub', href: 'https://github.com/LumaLibre/BiomesAPI' }
      ],
      sidebar: [
        {
          label: "Getting Started",
          link: "/setup/",
        },
        {
          label: "Biomes",
          autogenerate: { directory: "biomes" },
        },
        {
          label: "Wrappers",
          autogenerate: { directory: "wrappers" },
        },
        {
          label: "Renderers",
          autogenerate: { directory: "renderers" },
        },
        {
          label: "Connection",
          autogenerate: { directory: "connection" },
        },
        {
          label: "Components",
          autogenerate: { directory: "components" },
        }
      ],
    }),
  ],
});
