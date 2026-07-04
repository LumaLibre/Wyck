package dev.wyck.test;

import dev.wyck.keys.ResourceKey;
import dev.wyck.misc.providers.BasicBiomeProvider;
import dev.wyck.model.biome.Biome;
import dev.wyck.model.level.LevelCreator;
import dev.wyck.model.level.dimension.Dimension;
import dev.wyck.test.worldgen.FancyTreeFeature;
import dev.wyck.test.worldgen.PillarFeature;
import dev.wyck.wrapper.biome.BiomeSpecialEffects;
import dev.wyck.wrapper.biome.ClimateSettings;
import dev.wyck.wrapper.biome.TemperatureModifier;
import dev.wyck.wrapper.entity.BiomeSpawner;
import dev.wyck.wrapper.entity.MobCategory;
import dev.wyck.wrapper.entity.data.NaturalSpawner;
import dev.wyck.wrapper.environment.attribute.EnvironmentAttributes;
import dev.wyck.wrapper.environment.particle.ParticleCatalog;
import dev.wyck.wrapper.environment.particle.ParticleOptions;
import dev.wyck.wrapper.environment.particle.ParticleTypes;
import dev.wyck.wrapper.environment.particle.options.BlockParticle;
import dev.wyck.wrapper.level.BiomeSource;
import dev.wyck.wrapper.level.noise.Noise;
import dev.wyck.wrapper.level.noise.chunk.ChunkGenerator;
import dev.wyck.wrapper.worldgen.BiomeGenerationSettings;
import dev.wyck.wrapper.worldgen.GenerationStep;
import dev.wyck.wrapper.worldgen.HeightmapType;
import dev.wyck.wrapper.worldgen.carver.CanyonCarverConfiguration;
import dev.wyck.wrapper.worldgen.carver.CanyonShapeConfiguration;
import dev.wyck.wrapper.worldgen.carver.ConfiguredWorldCarver;
import dev.wyck.wrapper.worldgen.feature.ConfiguredFeature;
import dev.wyck.wrapper.worldgen.feature.ConfiguredFeatures;
import dev.wyck.wrapper.worldgen.feature.FeatureType;
import dev.wyck.wrapper.worldgen.feature.config.FeatureConfiguration;
import dev.wyck.wrapper.worldgen.feature.placement.PlacedFeature;
import dev.wyck.wrapper.worldgen.feature.placement.PlacedFeatures;
import dev.wyck.wrapper.worldgen.feature.placement.PlacementModifier;
import dev.wyck.wrapper.worldgen.valueproviders.FloatProvider;
import dev.wyck.wrapper.worldgen.valueproviders.HeightProvider;
import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import dev.wyck.wrapper.worldgen.valueproviders.VerticalAnchor;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ExamplePlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        Biome biome = Biome.builder()
            .resourceKey(ResourceKey.of("test:biome")) // Required key
            // Climate settings
//            .climateSettings(ClimateSettings.builder()
//                .downfall(0.5f)
//                .temperature(0.14f)
//                .temperatureModifier(TemperatureModifier.FROZEN)
//                .build()
//            )
            // Special effects
            .specialEffects(BiomeSpecialEffects.builder()
                .waterColor("#00FF00")
//                .grassColorOverride("#00FF00")
//                .foliageColorOverride("#00FF00")
//                .dryFoliageColorOverride("#0000FF")
                .build()
            )
            // Environmental attributes
//            .attribute(EnvironmentAttributes.BLOCK_LIGHT_TINT, "#FFFFFF")
//            .attribute(EnvironmentAttributes.FOG_COLOR, "#FFFFFF")
//            .attribute(EnvironmentAttributes.AMBIENT_PARTICLES, ParticleCatalog.builder()
//                .particle(ParticleTypes.BLOCK, 0.01f, BlockParticle.of(Material.ACACIA_LOG))
//                .build()
//            )
            .attribute(EnvironmentAttributes.DEFAULT_DRIPSTONE_PARTICLE, ParticleOptions.of(ParticleTypes.DRIPPING_HONEY))
            // Mob spawn settings
            .spawner(BiomeSpawner.builder()
                .spawner(MobCategory.MONSTER, NaturalSpawner.of(EntityType.ZOMBIE, 5, 15))
                .build()
            )
            // Worldgen
            .generationSettings(
                BiomeGenerationSettings.builder()
                    .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeature.builder()
                        .feature(new FancyTreeFeature().register(), FancyTreeFeature.TreeConfig.defaults())
                        .modifier(PlacementModifier.count(1))
                        .modifier(PlacementModifier.inSquare())
                        .modifier(PlacementModifier.heightmap(HeightmapType.MOTION_BLOCKING))
                        .modifier(PlacementModifier.biomeFilter())
                        .build())
                    .build()
            )
            .register();

        ChunkGenerator chunkGenerator = ChunkGenerator.builder()
            .biomeSource(BiomeSource.fixed(biome))
            .noise(Noise.overworld())
            .build();

        LevelCreator spec = LevelCreator.builder(ResourceKey.of("test:exampleworldtwo"))
            .generator(chunkGenerator)
            .dimension(Dimension.reference(ResourceKey.minecraft("overworld")))
            .build();
        spec.create();
    }
}