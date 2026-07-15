package dev.wyck.test;

import dev.wyck.keys.ResourceKey;
import dev.wyck.model.biome.Biome;
import dev.wyck.wrapper.biome.BiomeSpecialEffects;
import dev.wyck.wrapper.biome.BiomeGenerationSettings;
import dev.wyck.wrapper.worldgen.blockpredicates.BlockPredicate;
import dev.wyck.wrapper.worldgen.Decoration;
import dev.wyck.wrapper.worldgen.HeightmapType;
import dev.wyck.wrapper.worldgen.feature.ConfiguredFeature;
import dev.wyck.wrapper.worldgen.feature.FeatureType;
import dev.wyck.wrapper.worldgen.feature.configurations.FeatureConfiguration;
import dev.wyck.wrapper.worldgen.feature.configurations.TreeConfiguration;
import dev.wyck.wrapper.worldgen.feature.featuresize.FeatureSize;
import dev.wyck.wrapper.worldgen.feature.foliageplacers.FoliagePlacer;
import dev.wyck.wrapper.worldgen.feature.treedecorators.TreeDecorator;
import dev.wyck.wrapper.worldgen.feature.trunkplacers.TrunkPlacer;
import dev.wyck.wrapper.worldgen.function.DensityFunction;
import dev.wyck.wrapper.worldgen.function.DensityFunctions;
import dev.wyck.wrapper.worldgen.placement.PlacedFeature;
import dev.wyck.wrapper.worldgen.placement.PlacementModifier;
import dev.wyck.wrapper.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.BlockFace;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        // Making a tall spruce tree
        TreeConfiguration treeConfig = FeatureConfiguration.tree()
            .foliageProvider(BlockStateProvider.simple(Material.MANGROVE_LEAVES))
            .trunkProvider(BlockStateProvider.simple(Material.CHERRY_WOOD))
            .belowTrunkProvider(
                BlockStateProvider.ruleBased()
                    .rule(
                        BlockPredicate.not(BlockPredicate.matchingBlockTag(Tag.CANNOT_REPLACE_BELOW_TREE_TRUNK)),
                        BlockStateProvider.simple(Material.DIRT)
                    )
                    .build()
            )
            .foliagePlacer(FoliagePlacer.fancy()
                .height(4)
                .offset(IntProvider.constant(0))
                .radius(IntProvider.uniform(6, 7))
                .build()
            )
            .minimumSize(FeatureSize.twoLayers()
                .limit(1)
                .lowerSize(0)
                .minClippedHeight(0)
                .upperSize(0)
                .build()
            )
            .trunkPlacer(TrunkPlacer.cherry()
                .branchCount(IntProvider.constant(2))
                .branchHorizontalLength(IntProvider.constant(7))
                .baseHeight(7)
                .heightRandA(4)
                .heightRandB(6)
                .build()
            )
            .decorator(TreeDecorator.attachedToLeaves()
                .direction(BlockFace.UP)
                .blockProvider(BlockStateProvider.simple(Material.MEDIUM_AMETHYST_BUD))
                .probability(0.2f)
                .build()
            )
            .ignoreVines()
            .build();

        // Using it in a biome
        Biome.builder()
            .resourceKey(ResourceKey.of("test:biome"))
            .specialEffects(BiomeSpecialEffects.builder()
                .foliageColorOverride("#8b69ca")
                .build())
            .generationSettings(
                BiomeGenerationSettings.builder()
                    .feature(Decoration.VEGETAL_DECORATION, PlacedFeature.builder()
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

        DensityFunction densityFunction = DensityFunctions.PILLARS
            .add(DensityFunctions.CONTINENTS_LARGE)
            .mul(DensityFunction.constant(10f))
            .blendDensity()
            .register();
    }
}