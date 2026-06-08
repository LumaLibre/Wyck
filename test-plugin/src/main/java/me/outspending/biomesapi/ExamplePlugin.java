package me.outspending.biomesapi;

import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.registry.dimension.DimensionEditor;
import me.outspending.biomesapi.worldgen.PillarFeature;
import me.outspending.biomesapi.wrapper.worldgen.BiomeGenerationSettings;
import me.outspending.biomesapi.wrapper.worldgen.GenerationStep;
import me.outspending.biomesapi.wrapper.worldgen.HeightmapType;
import me.outspending.biomesapi.wrapper.worldgen.feature.ConfiguredFeature;
import me.outspending.biomesapi.wrapper.worldgen.feature.custom.CustomFeature;
import me.outspending.biomesapi.wrapper.worldgen.placement.PlacedFeature;
import me.outspending.biomesapi.wrapper.worldgen.placement.PlacedFeatures;
import me.outspending.biomesapi.wrapper.worldgen.placement.PlacementModifier;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        ResourceKey pillar = ResourceKey.of("example", "pillar");

        CustomFeature.register(pillar, new PillarFeature());
        ConfiguredFeature tallFeature  = ConfiguredFeature.customFeature(pillar, new PillarFeature.PillarConfig(Material.OBSIDIAN, Material.GLOWSTONE, 10, 15));
        ConfiguredFeature shortFeature = ConfiguredFeature.customFeature(pillar, new PillarFeature.PillarConfig(Material.BLACKSTONE, Material.SHROOMLIGHT, 3, 5));

        PlacedFeature tallPlaced = PlacedFeature.builder()
            .feature(tallFeature)
            .modifier(PlacementModifier.count(2))
            .modifier(PlacementModifier.inSquare())
            .modifier(PlacementModifier.heightmap(HeightmapType.WORLD_SURFACE))
            .modifier(PlacementModifier.biomeFilter())
            .build();

        PlacedFeature shortPlaced = PlacedFeature.builder()
            .feature(shortFeature)
            .modifier(PlacementModifier.count(4))
            .modifier(PlacementModifier.inSquare())
            .modifier(PlacementModifier.heightmap(HeightmapType.WORLD_SURFACE))
            .modifier(PlacementModifier.biomeFilter())
            .build();


        BiomeGenerationSettings generation = BiomeGenerationSettings.builder()
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.TREES_PLAINS)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.FALLEN_OAK_TREE)
            .addFeature(GenerationStep.VEGETAL_DECORATION, tallPlaced)
            .addFeature(GenerationStep.VEGETAL_DECORATION, shortPlaced)
            .build();
        ResourceKey myBiomeKey = ResourceKey.of("example", "my_biome");
        CustomBiome.builder(myBiomeKey)
            .fogColor("#DB00FD")
            .skyColor("#2F46FF")
            .waterColor("#00FFD0")
            .grassColor("#D1D13A")
            .foliageColor("#FF6A00")
            .setGenerationSettings(generation)
            .register();

        DimensionEditor dimensionEditor = DimensionEditor.create();
        ResourceKey overworldKey = ResourceKey.of("minecraft", "overworld");
        ResourceKey plains = ResourceKey.of("minecraft", "plains");
        dimensionEditor.replaceInDimension(overworldKey, plains, myBiomeKey);
        dimensionEditor.apply(); // Applies to all loaded worlds
    }
}
