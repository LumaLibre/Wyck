package dev.wyck.test;

import dev.wyck.keys.ResourceKey;
import dev.wyck.model.biome.Biome;
import dev.wyck.model.level.LevelCreator;
import dev.wyck.model.level.dimension.Dimension;
import dev.wyck.wrapper.biome.BiomeSpecialEffects;
import dev.wyck.wrapper.entity.BiomeSpawner;
import dev.wyck.wrapper.entity.MobCategory;
import dev.wyck.wrapper.entity.data.NaturalSpawner;
import dev.wyck.wrapper.environment.attribute.EnvironmentAttributes;
import dev.wyck.wrapper.environment.particle.ParticleOptions;
import dev.wyck.wrapper.environment.particle.ParticleTypes;
import dev.wyck.wrapper.level.BiomeSource;
import dev.wyck.wrapper.level.noise.Noise;
import dev.wyck.wrapper.level.noise.chunk.ChunkGenerator;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

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
//            .generationSettings(
//                BiomeGenerationSettings.builder()
//                    .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeature.builder()
//                        .feature(new SchematicTreeFeature().register(), SchematicTreeFeature.SchematicTreeConfig.defaults())
//                        .modifier(PlacementModifier.rarityFilter(10))
//                        .modifier(PlacementModifier.inSquare())
//                        .modifier(PlacementModifier.surfaceWaterDepth(0))
//                        .modifier(PlacementModifier.heightmap(HeightmapType.OCEAN_FLOOR))
//                        .modifier(PlacementModifier.biomeFilter())
//                        .build())
//
//                    .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeature.builder()
//                        .feature(new SchematicTreeFeature().registerAs(ResourceKey.of("t", "s")), SchematicTreeFeature.SchematicTreeConfig.defaults2())
//                        .modifier(PlacementModifier.rarityFilter(10))
//                        .modifier(PlacementModifier.inSquare())
//                        .modifier(PlacementModifier.surfaceWaterDepth(0))
//                        .modifier(PlacementModifier.heightmap(HeightmapType.OCEAN_FLOOR))
//                        .modifier(PlacementModifier.biomeFilter())
//                        .build())
//                    .build()
//            )
            .register();

        ChunkGenerator chunkGenerator = ChunkGenerator.builder()
            .biomeSource(BiomeSource.fixed(biome))
            .noise(Noise.overworld())
            .build();

        LevelCreator spec = LevelCreator.builder(ResourceKey.of("test:exampleworld21"))
            .generator(chunkGenerator)
            .dimension(Dimension.reference(ResourceKey.minecraft("overworld")))
            .build();
        spec.create();
    }
}