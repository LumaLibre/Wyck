package me.outspending.biomesapi;

import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.providers.BasicBiomeProvider;
import me.outspending.biomesapi.wrapper.BiomeSettings;
import me.outspending.biomesapi.wrapper.worldgen.BiomeGenerationSettings;
import me.outspending.biomesapi.wrapper.worldgen.GenerationStep;
import me.outspending.biomesapi.wrapper.worldgen.HeightmapType;
import me.outspending.biomesapi.wrapper.worldgen.feature.ConfiguredFeatures;
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
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class BiomesAPITest extends JavaPlugin implements Listener {

    //private BiomeSetter biomeSetter;
    //private Lazy<CustomBiome> customBiome = RegisteredBiomes.getLazily(BiomeResourceKey.of("test", "custombiome"));

    @Override
    public void onEnable() {
        BiomeGenerationSettings generation = BiomeGenerationSettings.builder()
            // --- carvers: vanilla refs + two authored variants ---
            .addCarver(Carvers.CAVE)
            .addCarver(Carvers.CANYON)
            .addCarver(ConfiguredWorldCarver.cave(
                CaveCarverConfiguration.builder()
                    .probability(0.45F)
                    .y(HeightProvider.uniform(VerticalAnchor.aboveBottom(8), VerticalAnchor.absolute(180)))
                    .yScale(FloatProvider.uniform(0.5F, 1.4F))
                    .lavaLevel(VerticalAnchor.aboveBottom(8))
                    .replaceable(List.of(Material.STONE, Material.DEEPSLATE, Material.DIRT, Material.GRASS_BLOCK))
                    .horizontalRadiusMultiplier(FloatProvider.uniform(1.2F, 2.4F))
                    .verticalRadiusMultiplier(FloatProvider.uniform(1.0F, 1.8F))
                    .floorLevel(FloatProvider.uniform(-1.0F, -0.4F))
                    .build()))
            .addCarver(ConfiguredWorldCarver.canyon(
                CanyonCarverConfiguration.builder()
                    .probability(0.20F)
                    .y(HeightProvider.uniform(VerticalAnchor.absolute(10), VerticalAnchor.absolute(120)))
                    .yScale(FloatProvider.constant(3.0F))
                    .lavaLevel(VerticalAnchor.aboveBottom(8))
                    .replaceable(List.of(Material.STONE, Material.DEEPSLATE))
                    .verticalRotation(FloatProvider.uniform(-0.4F, 0.4F))
                    .shape(CanyonShapeConfiguration.builder()
                        .distanceFactor(FloatProvider.uniform(0.6F, 1.0F))
                        .thickness(FloatProvider.trapezoid(0.0F, 8.0F, 3.0F))
                        .widthSmoothness(3)
                        .horizontalRadiusFactor(FloatProvider.uniform(0.75F, 1.0F))
                        .verticalRadiusDefaultFactor(1.0F)
                        .verticalRadiusCenterFactor(0.0F)
                        .build())
                    .build()))

            // --- ores: every vanilla ore, whole column, via references ---
            .addFeature(GenerationStep.UNDERGROUND_ORES, PlacedFeature.reference(BiomeResourceKey.minecraft("ore_coal_upper")))
            .addFeature(GenerationStep.UNDERGROUND_ORES, PlacedFeature.reference(BiomeResourceKey.minecraft("ore_iron_upper")))
            .addFeature(GenerationStep.UNDERGROUND_ORES, PlacedFeature.reference(BiomeResourceKey.minecraft("ore_gold")))
            .addFeature(GenerationStep.UNDERGROUND_ORES, PlacedFeature.reference(BiomeResourceKey.minecraft("ore_redstone")))
            .addFeature(GenerationStep.UNDERGROUND_ORES, PlacedFeature.reference(BiomeResourceKey.minecraft("ore_diamond")))
            .addFeature(GenerationStep.UNDERGROUND_ORES, PlacedFeature.reference(BiomeResourceKey.minecraft("ore_lapis")))
            .addFeature(GenerationStep.UNDERGROUND_ORES, PlacedFeature.reference(BiomeResourceKey.minecraft("ore_copper")))
            .addFeature(GenerationStep.UNDERGROUND_ORES, PlacedFeature.reference(BiomeResourceKey.minecraft("ore_emerald")))

            // --- authored ore: hand-built diamond placement, absurd density ---
            .addFeature(GenerationStep.UNDERGROUND_ORES, PlacedFeature.of(
                ConfiguredFeatures.ORE_DIAMOND_LARGE,
                PlacementModifier.count(40),
                PlacementModifier.inSquare(),
                PlacementModifier.heightRangeUniform(VerticalAnchor.absolute(-60), VerticalAnchor.absolute(120)),
                PlacementModifier.biomeFilter()))

            // --- geodes + dripstone ---
            .addFeature(GenerationStep.LOCAL_MODIFICATIONS, PlacedFeature.reference(BiomeResourceKey.minecraft("amethyst_geode")))
            .addFeature(GenerationStep.UNDERGROUND_DECORATION, PlacedFeature.reference(BiomeResourceKey.minecraft("large_dripstone")))
            .addFeature(GenerationStep.UNDERGROUND_DECORATION, PlacedFeature.reference(BiomeResourceKey.minecraft("pointed_dripstone")))

            // --- springs ---
            .addFeature(GenerationStep.FLUID_SPRINGS, PlacedFeature.reference(BiomeResourceKey.minecraft("spring_water")))
            .addFeature(GenerationStep.FLUID_SPRINGS, PlacedFeature.reference(BiomeResourceKey.minecraft("spring_lava")))
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeature.of(
                ConfiguredFeatures.FANCY_OAK,
                PlacementModifier.count(12),
                PlacementModifier.inSquare(),
                PlacementModifier.heightmap(HeightmapType.MOTION_BLOCKING),
                PlacementModifier.biomeFilter()))
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeature.of(
                ConfiguredFeatures.FLOWER_DEFAULT,
                PlacementModifier.count(20),
                PlacementModifier.inSquare(),
                PlacementModifier.heightmap(HeightmapType.MOTION_BLOCKING),
                PlacementModifier.biomeFilter()))
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeature.of(
                ConfiguredFeatures.PILE_PUMPKIN,
                PlacementModifier.rarityFilter(4),
                PlacementModifier.inSquare(),
                PlacementModifier.heightmap(HeightmapType.MOTION_BLOCKING),
                PlacementModifier.biomeFilter()))
            .addFeature(GenerationStep.TOP_LAYER_MODIFICATION, PlacedFeature.reference(BiomeResourceKey.minecraft("freeze_top_layer")))
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

        new WorldCreator("xyz")
            .biomeProvider(BasicBiomeProvider.of(mybiome))
            .createWorld();
    }

    //@EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        //biomeSetter.setChunkBiome(event.getChunk(), customBiome.get());
    }
}