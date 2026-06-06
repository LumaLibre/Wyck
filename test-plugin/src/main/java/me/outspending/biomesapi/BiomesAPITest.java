package me.outspending.biomesapi;

import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.providers.BasicBiomeProvider;
import me.outspending.biomesapi.wrapper.BiomeSettings;
import me.outspending.biomesapi.wrapper.worldgen.BiomeGenerationSettings;
import me.outspending.biomesapi.wrapper.worldgen.GenerationStep;
import me.outspending.biomesapi.wrapper.worldgen.HeightmapType;
import me.outspending.biomesapi.wrapper.worldgen.feature.ConfiguredFeatures;
import me.outspending.biomesapi.wrapper.worldgen.placement.PlacedFeatures;
import me.outspending.biomesapi.wrapper.worldgen.placement.PlacementModifier;
import me.outspending.biomesapi.wrapper.worldgen.carver.CanyonCarverConfiguration;
import me.outspending.biomesapi.wrapper.worldgen.carver.CanyonShapeConfiguration;
import me.outspending.biomesapi.wrapper.worldgen.carver.Carvers;
import me.outspending.biomesapi.wrapper.worldgen.carver.CaveCarverConfiguration;
import me.outspending.biomesapi.wrapper.worldgen.carver.ConfiguredWorldCarver;
import me.outspending.biomesapi.wrapper.worldgen.placement.PlacedFeature;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.FloatProvider;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.HeightProvider;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.VerticalAnchor;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class BiomesAPITest extends JavaPlugin implements Listener {

    //private BiomeSetter biomeSetter;
    //private Lazy<CustomBiome> customBiome = RegisteredBiomes.getLazily(BiomeResourceKey.of("test", "custombiome"));

    @Override
    public void onEnable() {
        //ChunkGenerator
        BiomeGenerationSettings generation = BiomeGenerationSettings.builder()
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.FANCY_OAK_CHECKED)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.TREES_PLAINS)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.FALLEN_OAK_TREE)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.FLOWER_DEFAULT)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.FLOWER_PLAINS)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.WILDFLOWERS_MEADOW)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.PATCH_GRASS_PLAIN)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.PATCH_TALL_GRASS)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.PATCH_TALL_GRASS_2)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.PATCH_SUNFLOWER)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.BROWN_MUSHROOM_NORMAL)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.RED_MUSHROOM_NORMAL)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.PATCH_PUMPKIN)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.PATCH_FIREFLY_BUSH_NEAR_WATER)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.OAK_BEES_002)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.FANCY_OAK_BEES_002)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeature.of(
                ConfiguredFeatures.FLOWER_DEFAULT,
                PlacementModifier.count(18),
                PlacementModifier.inSquare(),
                PlacementModifier.heightmap(HeightmapType.MOTION_BLOCKING),
                PlacementModifier.biomeFilter()))
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeature.of(
                ConfiguredFeatures.FANCY_OAK,
                PlacementModifier.count(5),
                PlacementModifier.inSquare(),
                PlacementModifier.heightmap(HeightmapType.MOTION_BLOCKING),
                PlacementModifier.biomeFilter()))
            .addFeature(GenerationStep.FLUID_SPRINGS, PlacedFeatures.SPRING_WATER)
            .addFeature(GenerationStep.TOP_LAYER_MODIFICATION, PlacedFeatures.FREEZE_TOP_LAYER)
            .build();

        CustomBiome mybiome = CustomBiome.builder()
            .resourceKey(BiomeResourceKey.of("test", "mybiome"))
            .settings(BiomeSettings.defaultSettings())
            .fogColor("#DB00FD")
            .skyColor("#2F46FF")
            .waterColor("#00FFD0")
            .grassColor("#D1D13A")
            .foliageColor("#FF6A00")
            .setGenerationSettings(generation)
            .register();

        new WorldCreator("new_world")
            .biomeProvider(BasicBiomeProvider.of(mybiome))
            .createWorld();
    }

    //@EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        //biomeSetter.setChunkBiome(event.getChunk(), customBiome.get());
    }
}