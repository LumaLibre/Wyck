package me.outspending.biomesapi;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.worldgen.PillarConfig;
import me.outspending.biomesapi.worldgen.PillarFeature;
import me.outspending.biomesapi.wrapper.worldgen.BiomeGenerationSettings;
import me.outspending.biomesapi.wrapper.worldgen.GenerationStep;
import me.outspending.biomesapi.wrapper.worldgen.HeightmapType;
import me.outspending.biomesapi.wrapper.worldgen.feature.ConfiguredFeature;
import me.outspending.biomesapi.wrapper.worldgen.placement.PlacedFeature;
import me.outspending.biomesapi.wrapper.worldgen.placement.PlacedFeatures;
import me.outspending.biomesapi.wrapper.worldgen.placement.PlacementModifier;
import org.bukkit.Material;

public class TestPluginBootstrapper implements PluginBootstrap {

    public static BiomeResourceKey PILLAR_KEY = BiomeResourceKey.of("example", "pillar");

    public static BiomeGenerationSettings newGen;

    @Override
    public void bootstrap(BootstrapContext context) {

        new PillarFeature().register(PILLAR_KEY);
        ConfiguredFeature tallOne  = ConfiguredFeature.customFeature(PILLAR_KEY, new PillarConfig(Material.OBSIDIAN, Material.GLOWSTONE, 10, 15));
        ConfiguredFeature shortOne = ConfiguredFeature.customFeature(PILLAR_KEY, new PillarConfig(Material.BLACKSTONE, Material.SHROOMLIGHT, 3, 5));


        PlacedFeature tallPlaced = PlacedFeature.of(
            tallOne,
            PlacementModifier.count(2),
            PlacementModifier.inSquare(),
            PlacementModifier.heightmap(HeightmapType.WORLD_SURFACE),
            PlacementModifier.biomeFilter()
        );
        PlacedFeature shortPlaced = PlacedFeature.of(
            shortOne,
            PlacementModifier.count(4),
            PlacementModifier.inSquare(),
            PlacementModifier.heightmap(HeightmapType.WORLD_SURFACE),
            PlacementModifier.biomeFilter()
        );


        newGen = BiomeGenerationSettings.builder()
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.TREES_PLAINS)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.FALLEN_OAK_TREE)
            .addFeature(GenerationStep.VEGETAL_DECORATION, tallPlaced)
            .addFeature(GenerationStep.VEGETAL_DECORATION, shortPlaced)
            .build();
    }
}