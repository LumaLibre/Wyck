package me.outspending.biomesapi;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.registry.bootstrap.BootstrapBiomeRegistry;
import me.outspending.biomesapi.registry.bootstrap.Composer;
import me.outspending.biomesapi.wrapper.worldgen.BiomeGenerationSettings;
import me.outspending.biomesapi.wrapper.worldgen.GenerationStep;
import me.outspending.biomesapi.wrapper.worldgen.placement.PlacedFeatures;

public class ExampleBootstrapper implements PluginBootstrap {
    @Override
    public void bootstrap(BootstrapContext context) {

        // This must use the 'DATAPACK' composer!
        BootstrapBiomeRegistry registry = BootstrapBiomeRegistry.compose(context, Composer.DATAPACK);
        BiomeGenerationSettings generation = BiomeGenerationSettings.builder()
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.TREES_PLAINS)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.FALLEN_OAK_TREE)
            .build();

        ResourceKey myBiomeKey = ResourceKey.of("example", "my_biome2");
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

        ResourceKey overworldKey = ResourceKey.of("minecraft", "overworld");
        ResourceKey plainsKey = ResourceKey.of("minecraft", "plains");
        registry.replaceInDimension(overworldKey, plainsKey, myBiomeKey);
    }
}
