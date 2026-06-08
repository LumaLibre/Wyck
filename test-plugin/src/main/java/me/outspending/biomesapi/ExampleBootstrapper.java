package me.outspending.biomesapi;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.registry.bootstrap.BootstrapBiomeRegistry;
import me.outspending.biomesapi.registry.bootstrap.Composer;
import me.outspending.biomesapi.worldgen.PillarConfig;
import me.outspending.biomesapi.worldgen.PillarFeature;
import me.outspending.biomesapi.wrapper.worldgen.BiomeGenerationSettings;
import me.outspending.biomesapi.wrapper.worldgen.GenerationStep;
import me.outspending.biomesapi.wrapper.worldgen.feature.ConfiguredFeature;
import me.outspending.biomesapi.wrapper.worldgen.placement.PlacedFeatures;
import org.bukkit.Material;

public class ExampleBootstrapper implements PluginBootstrap {
    @Override
    public void bootstrap(BootstrapContext context) {

        // This must use the 'DATAPACK' composer!
        BootstrapBiomeRegistry registry = BootstrapBiomeRegistry.compose(context, Composer.DATAPACK);
        BiomeGenerationSettings generation = BiomeGenerationSettings.builder()
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.TREES_PLAINS)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.FALLEN_OAK_TREE)
            .build();

        BiomeResourceKey myBiomeKey = BiomeResourceKey.of("example", "my_biome");
        registry.queue(
            CustomBiome.builder(myBiomeKey)
                .fogColor("#DB00FD")
                .skyColor("#2F46FF")
                .waterColor("#00FFD0")
                .grassColor("#D1D13A")
                .foliageColor("#FF6A00")
                .setGenerationSettings(generation)
                .build()
        );

        BiomeResourceKey overworldKey = BiomeResourceKey.of("minecraft", "overworld");
        BiomeResourceKey plainsKey = BiomeResourceKey.of("minecraft", "plains");
        registry.replaceInDimension(overworldKey, plainsKey, myBiomeKey);
    }
}
