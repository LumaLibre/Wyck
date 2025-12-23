// @ts-check
import { defineConfig } from 'astro/config';
import starlight from '@astrojs/starlight';

// https://astro.build/config
export default defineConfig({
  site: "https://biomesapi.lumamc.net",
  integrations: [
    starlight({
      title: 'BiomesAPI Docs',
      social: [
        { icon: 'github', label: 'GitHub', href: 'https://github.com/LumaLibre/BiomesAPI' }
      ],
      sidebar: [
        {
          label: "Guides",
          autogenerate: { directory: "guides" },
        },
      ],
    }),
  ],
});
