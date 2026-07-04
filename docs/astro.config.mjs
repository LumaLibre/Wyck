// @ts-check
import {defineConfig} from 'astro/config';
import starlight from '@astrojs/starlight';
import starlightThemeVintage from 'starlight-theme-vintage';
import {fileURLToPath} from 'node:url';
import yaml from '@rollup/plugin-yaml';

// https://astro.build/config
export default defineConfig({
  site: "https://wyck.dev/",
  redirects: {
    '/jd': '/javadoc/index.html',
    '/discord': 'https://discord.gg/CCZGFg85jM'
  },
  vite: {
    plugins: [yaml()],
    resolve: {
      alias: {
        '@': fileURLToPath(new URL('./src', import.meta.url)),
      },
    },
  },
  integrations: [
    starlight({
      favicon: '/favicon.png',
      components: {
        Sidebar: './src/components/Sidebar.astro',
      },
      plugins: [starlightThemeVintage()],
      title: 'Wyck (BiomesAPI)',
      logo: {
        src: './src/assets/wyck.png',
        replacesTitle: true,
      },
      social: [
        { icon: 'github', label: 'GitHub', href: 'https://github.com/LumaLibre/Wyck' },
        { icon: 'discord', label: 'Discord', href: 'https://discord.gg/CCZGFg85jM' },
        { icon: 'open-book', label: 'Javadocs', href: 'https://wyck.dev/javadoc/index.html' },
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
          label: "Dimensions",
          autogenerate: { directory: "dimensions" },
        },
        {
          label: "Renderers",
          autogenerate: { directory: "renderers" },
        },
        {
          label: "World Generation",
          autogenerate: { directory: "worldgen" },
        },
        {
          label: "Registries",
          autogenerate: { directory: "registries" },
        },
        {
          label: "Components",
          autogenerate: { directory: "components" },
        },
        {
          label: "Miscellaneous",
          autogenerate: { directory: "misc" },
        }
      ],
    }),
  ],
});
