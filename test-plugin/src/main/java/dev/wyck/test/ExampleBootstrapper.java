package dev.wyck.test;

import dev.wyck.wrapper.environment.particle.ParticleTypes;
import dev.wyck.wrapper.environment.particle.options.DustParticle;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import dev.wyck.model.biome.CustomBiome;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.bootstrap.BootstrapBiomeRegistry;
import dev.wyck.registry.bootstrap.Composer;
import dev.wyck.wrapper.worldgen.BiomeGenerationSettings;
import dev.wyck.wrapper.worldgen.GenerationStep;
import dev.wyck.wrapper.worldgen.placement.PlacedFeatures;

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
                .generationSettings(generation)
                .particle(ParticleTypes.DUST, 0.1f, DustParticle.of("#FF0000"))
                .particle(ParticleTypes.ASH, 0.01f)
                .build()
        );

        ResourceKey overworldKey = ResourceKey.of("minecraft", "overworld");
        ResourceKey plainsKey = ResourceKey.of("minecraft", "plains");
        registry.replaceInDimension(overworldKey, plainsKey, myBiomeKey);
    }
}
