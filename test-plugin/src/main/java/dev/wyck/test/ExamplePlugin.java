package dev.wyck.test;

import dev.wyck.keys.ResourceKey;
import dev.wyck.model.biome.Biome;
import dev.wyck.wrapper.worldgen.BiomeGenerationSettings;
import dev.wyck.wrapper.worldgen.BlockPredicate;
import dev.wyck.wrapper.worldgen.GenerationStep;
import dev.wyck.wrapper.worldgen.HeightmapType;
import dev.wyck.wrapper.worldgen.feature.ConfiguredFeature;
import dev.wyck.wrapper.worldgen.feature.FeatureType;
import dev.wyck.wrapper.worldgen.feature.configurations.FeatureConfiguration;
import dev.wyck.wrapper.worldgen.feature.configurations.TreeConfiguration;
import dev.wyck.wrapper.worldgen.feature.featuresize.FeatureSize;
import dev.wyck.wrapper.worldgen.feature.featuresize.TwoLayersFeatureSize;
import dev.wyck.wrapper.worldgen.feature.foliageplacers.FoliagePlacer;
import dev.wyck.wrapper.worldgen.feature.foliageplacers.PineFoliagePlacer;
import dev.wyck.wrapper.worldgen.feature.trunkplacers.StraightTrunkPlacer;
import dev.wyck.wrapper.worldgen.feature.trunkplacers.TrunkPlacer;
import dev.wyck.wrapper.worldgen.placement.PlacedFeature;
import dev.wyck.wrapper.worldgen.placement.PlacementModifier;
import dev.wyck.wrapper.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        // Making a tall spruce tree
        TreeConfiguration treeConfig = FeatureConfiguration.tree()
            .foliageProvider(BlockStateProvider.simple(Material.SPRUCE_LEAVES))
            .trunkProvider(BlockStateProvider.simple(Material.SPRUCE_LOG))
            .belowTrunkProvider(
                BlockStateProvider.ruleBased()
                    .rule(
                        BlockPredicate.not(BlockPredicate.matchesTag(Tag.CANNOT_REPLACE_BELOW_TREE_TRUNK)),
                        BlockStateProvider.simple(Material.DIRT)
                    )
                    .build()
            )
            .foliagePlacer(FoliagePlacer.pine()
                .height(IntProvider.constant(4))
                .offset(IntProvider.constant(1))
                .radius(IntProvider.uniform(4, 6))
                .build()
            )
            .minimumSize(FeatureSize.twoLayers()
                .limit(1)
                .lowerSize(0)
                .minClippedHeight(0)
                .upperSize(0)
                .build()
            )
            .trunkPlacer(TrunkPlacer.straight()
                .baseHeight(10)
                .heightRandA(4)
                .heightRandB(8)
                .build()
            )
            .ignoreVines()
            .build();

        // Using it in a biome
        Biome.builder()
            .resourceKey(ResourceKey.of("test:biome"))
            .generationSettings(
                BiomeGenerationSettings.builder()
                    .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeature.builder()
                        .feature(ConfiguredFeature.of(FeatureType.TREE, treeConfig))
                        .modifier(PlacementModifier.rarityFilter(1))
                        .modifier(PlacementModifier.inSquare())
                        .modifier(PlacementModifier.surfaceWaterDepthFilter(0))
                        .modifier(PlacementModifier.heightmap(HeightmapType.OCEAN_FLOOR))
                        .modifier(PlacementModifier.biomeFilter())
                        .build())
                    .build()
            )
            .register();
    }
}