// @ts-check
import {defineConfig} from 'astro/config';
import starlight from '@astrojs/starlight';
import starlightThemeVintage from 'starlight-theme-vintage';
import {fileURLToPath} from 'node:url';
import yaml from '@rollup/plugin-yaml';

// https://astro.build/config
export default defineConfig({
  site: "https://biomes.lumas.dev",
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
      components: {
        Sidebar: './src/components/Sidebar.astro',
      },
      plugins: [starlightThemeVintage()],
      title: 'BiomesAPI',
      logo: {
        src: './src/assets/biomesapi.png',
        replacesTitle: true,
      },
      social: [
        { icon: 'github', label: 'GitHub', href: 'https://github.com/LumaLibre/BiomesAPI' },
        { icon:  'discord', label: 'Discord', href: 'https://discord.gg/CCZGFg85jM' },
      ],
      sidebar: [
        {
          label: "Getting Started",
          link: "/setup/",
        },
        // {
        //   label: "Introduction",
        //   link: "/intro/",
        // },
        {
          label: "Biomes",
          autogenerate: { directory: "biomes" },
        },
        {
          label: "Wrappers",
          autogenerate: { directory: "wrappers" },
        },
        {
          label: "Registries",
          autogenerate: { directory: "registries" },
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
          label: "Connection",
          autogenerate: { directory: "connection" },
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
