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
import me.outspending.biomesapi.wrapper.worldgen.BiomeGenerationSettings;
import me.outspending.biomesapi.wrapper.worldgen.GenerationStep;
import me.outspending.biomesapi.wrapper.worldgen.HeightmapType;
import me.outspending.biomesapi.wrapper.worldgen.carver.CanyonCarverConfiguration;
import me.outspending.biomesapi.wrapper.worldgen.carver.CanyonShapeConfiguration;
import me.outspending.biomesapi.wrapper.worldgen.carver.ConfiguredWorldCarver;
import me.outspending.biomesapi.wrapper.worldgen.climate.BiomeClimatePoint;
import me.outspending.biomesapi.wrapper.worldgen.climate.BiomeParameter;
import me.outspending.biomesapi.wrapper.worldgen.feature.ConfiguredFeatures;
import me.outspending.biomesapi.wrapper.worldgen.placement.PlacedFeature;
import me.outspending.biomesapi.wrapper.worldgen.placement.PlacedFeatures;
import me.outspending.biomesapi.wrapper.worldgen.placement.PlacementModifier;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.FloatProvider;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.HeightProvider;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.VerticalAnchor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.List;

public class TestPluginBootstrapper implements PluginBootstrap {
    @Override
    public void bootstrap(BootstrapContext context) {
        BootstrapBiomeRegistry registry = BootstrapBiomeRegistry.compose(context, Composer.INJECTOR);

        registry.deferring(() -> {
//            BiomeGenerationSettings generation = BiomeGenerationSettings.builder()
//                // bees-on-trees variants come before the main tree patches in vanilla
//                .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.TREES_PLAINS)
//                .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.FALLEN_OAK_TREE)
//                .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.TREES_MANGROVE)
//                .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.FLOWER_PLAINS)
//                .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.FLOWER_DEFAULT)
//                .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.WILDFLOWERS_MEADOW)
//                .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.PATCH_GRASS_PLAIN)
//                .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.PATCH_TALL_GRASS)
//                .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.PATCH_TALL_GRASS_2)
//                .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.PATCH_SUNFLOWER)
//                .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.PATCH_PUMPKIN)
//                .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.BROWN_MUSHROOM_NORMAL)
//                .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.RED_MUSHROOM_NORMAL)
//                .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.PATCH_FIREFLY_BUSH_NEAR_WATER)
//                .addFeature(GenerationStep.TOP_LAYER_MODIFICATION, PlacedFeatures.FREEZE_TOP_LAYER)
//                .addCarver(ConfiguredWorldCarver.canyon(
//                    CanyonCarverConfiguration.builder()
//                        .probability(0.75f)
//                        .lavaLevel(VerticalAnchor.absolute(10))
//                        .y(HeightProvider.uniform(VerticalAnchor.absolute(10), VerticalAnchor.absolute(70)))
//                        .yScale(FloatProvider.uniform(0.75f, 1.25f))
//                        .verticalRotation(FloatProvider.uniform(-15.0f, 15.0f))
//                        .replaceable(List.of(Material.GRASS_BLOCK, Material.STONE))
//                        .shape(CanyonShapeConfiguration.builder()
//                            .distanceFactor(FloatProvider.uniform(0.75f, 1.25f))
//                            .thickness(FloatProvider.uniform(1.0f, 3.0f))
//                            .widthSmoothness(3)
//                            .horizontalRadiusFactor(FloatProvider.uniform(0.75f, 1.25f))
//                            .verticalRadiusDefaultFactor(1.0f)
//                            .verticalRadiusCenterFactor(1.0f)
//                            .build()
//                        )
//                        .build()
//                ))
//                .build();

            BiomeGenerationSettings generation = BiomeGenerationSettings.builder()
                .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.FLOWER_PLAINS)
                .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.PATCH_GRASS_PLAIN)
                .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.BROWN_MUSHROOM_NORMAL)
                .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.RED_MUSHROOM_NORMAL)
                .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.PATCH_PUMPKIN)
                .addCarver(ConfiguredWorldCarver.canyon(
                    CanyonCarverConfiguration.builder()
                        .probability(0.75f)
                        .lavaLevel(VerticalAnchor.absolute(10))
                        .y(HeightProvider.uniform(VerticalAnchor.absolute(10), VerticalAnchor.absolute(70)))
                        .yScale(FloatProvider.uniform(0.75f, 1.25f))
                        .verticalRotation(FloatProvider.uniform(-15.0f, 15.0f))
                        .replaceable(List.of(Material.GRASS_BLOCK, Material.STONE))
                        .shape(CanyonShapeConfiguration.builder()
                            .distanceFactor(FloatProvider.uniform(0.75f, 1.25f))
                            .thickness(FloatProvider.uniform(1.0f, 3.0f))
                            .widthSmoothness(3)
                            .horizontalRadiusFactor(FloatProvider.uniform(0.75f, 1.25f))
                            .verticalRadiusDefaultFactor(1.0f)
                            .verticalRadiusCenterFactor(1.0f)
                            .build()
                        )
                        .build()
                ))
                .build();

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
                .setGenerationSettings(generation)
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

            var overworld = BiomeResourceKey.of("minecraft", "overworld");
            var plains = BiomeResourceKey.of("minecraft", "plains");
            var forest = BiomeResourceKey.of("minecraft", "forest");
            var testbiome = BiomeResourceKey.of("test", "custombiome");
            //registry.replaceInDimension(overworld, plains, testbiome);
            //registry.replaceInDimension(overworld, forest, testbiome);

            registry.addToDimension(overworld, testbiome, BiomeClimatePoint.builder().build());

        });
        context.getLogger().info("Finished registering biomes.");
    }
}