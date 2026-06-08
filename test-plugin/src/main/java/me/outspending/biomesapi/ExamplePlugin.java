package me.outspending.biomesapi;

import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.registry.dimension.DimensionEditor;
import me.outspending.biomesapi.wrapper.worldgen.BiomeGenerationSettings;
import me.outspending.biomesapi.wrapper.worldgen.GenerationStep;
import me.outspending.biomesapi.wrapper.worldgen.placement.PlacedFeatures;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        BiomeGenerationSettings generation = BiomeGenerationSettings.builder()
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.TREES_PLAINS)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.FALLEN_OAK_TREE)
            .build();

        BiomeResourceKey myBiomeKey = BiomeResourceKey.of("example", "my_biome");
        CustomBiome.builder(myBiomeKey)
            .fogColor("#DB00FD")
            .skyColor("#2F46FF")
            .waterColor("#00FFD0")
            .grassColor("#D1D13A")
            .foliageColor("#FF6A00")
            .setGenerationSettings(generation)
            .build();

        DimensionEditor dimensionEditor = DimensionEditor.create();
        BiomeResourceKey overworldKey = BiomeResourceKey.of("minecraft", "overworld");
        BiomeResourceKey plains = BiomeResourceKey.of("minecraft", "plains");
        dimensionEditor.replaceInDimension(overworldKey, plains, myBiomeKey);
        dimensionEditor.apply(); // Applies to all loaded worlds
    }
}
