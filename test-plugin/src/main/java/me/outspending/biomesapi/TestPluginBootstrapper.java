package me.outspending.biomesapi;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.registry.bootstrap.BootstrapBiomeRegistry;
import me.outspending.biomesapi.registry.bootstrap.Composer;
import me.outspending.biomesapi.wrapper.BiomeSettings;
import me.outspending.biomesapi.wrapper.entity.BiomeSpawner;
import me.outspending.biomesapi.wrapper.entity.MobCategory;
import me.outspending.biomesapi.wrapper.entity.data.NaturalSpawner;
import org.bukkit.entity.EntityType;

public class TestPluginBootstrapper implements PluginBootstrap {
    @Override
    public void bootstrap(BootstrapContext context) {
        BootstrapBiomeRegistry registry = BootstrapBiomeRegistry.compose(context, Composer.DATAPACK);

        registry.deferring(() -> {
            BiomeSpawner spawner = BiomeSpawner.builder()
                .setCreatureGenerationProbability(0.2f)
                .addSpawner(MobCategory.CREATURE, 100, NaturalSpawner.of(EntityType.PIG, 4, 8))
                .build();

            registry.queue(CustomBiome.builder()
                .resourceKey(BiomeResourceKey.of("test", "custombiome"))
                .settings(BiomeSettings.defaultSettings())
                .fogColor("#FFFFFF")
                .skyColor("#B99DFC")
                .waterColor("#F5F2EB")
                .grassColor("#DBE9EC")
                .setSpawner(spawner)
                .build()
            );

            registry.queue(CustomBiome.builder()
                .resourceKey(BiomeResourceKey.of("test", "b"))
                .settings(BiomeSettings.defaultSettings())
                .fogColor("#DB00FD")
                .skyColor("#2F46FF")
                .waterColor("#000000")
                .grassColor("#D1D13A")
                .setSpawner(spawner)
                .build()
            );
        });
        context.getLogger().info("Finished registering biomes.");
    }
}