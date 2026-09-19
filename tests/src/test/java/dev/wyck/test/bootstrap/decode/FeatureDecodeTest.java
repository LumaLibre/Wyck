package dev.wyck.test.bootstrap.decode;

import dev.wyck.keys.ResourceKey;
import dev.wyck.test.bootstrap.MinecraftBootstrap;
import dev.wyck.util.BukkitBootstrapUtil;
import dev.wyck.util.WeightedList;
import dev.wyck.worldgen.blockpredicates.BlockPredicate;
import dev.wyck.worldgen.feature.Feature;
import dev.wyck.worldgen.feature.FeatureType;
import dev.wyck.worldgen.feature.configurations.BlockPileFeature;
import dev.wyck.worldgen.feature.configurations.BlockBlobFeature;
import dev.wyck.worldgen.feature.configurations.BlockColumnFeature;
import dev.wyck.worldgen.feature.configurations.IcebergFeature;
import dev.wyck.worldgen.feature.configurations.SequenceFeature;
import dev.wyck.worldgen.feature.configurations.DeltaFeature;
import dev.wyck.worldgen.feature.configurations.DiskFeature;
import dev.wyck.worldgen.feature.configurations.EndGatewayFeature;
import dev.wyck.worldgen.feature.configurations.EndSpikeFeature;
import dev.wyck.worldgen.feature.configurations.FeatureConfiguration;
import dev.wyck.worldgen.feature.configurations.FillLayerFeature;
import dev.wyck.worldgen.feature.configurations.LakeFeature;
import dev.wyck.worldgen.feature.configurations.LargeDripstoneFeature;
import dev.wyck.worldgen.feature.configurations.MultifaceGrowthFeature;
import dev.wyck.worldgen.feature.configurations.NoOpFeature;
import dev.wyck.worldgen.feature.configurations.HugeRedMushroomFeature;
import dev.wyck.worldgen.feature.configurations.BambooFeature;
import dev.wyck.worldgen.feature.configurations.OreFeature;
import dev.wyck.worldgen.feature.configurations.RandomBooleanSelectorFeature;
import dev.wyck.worldgen.feature.configurations.RandomSelectorFeature;
import dev.wyck.worldgen.feature.configurations.SimpleBlockFeature;
import dev.wyck.worldgen.feature.configurations.ReplaceBlobsFeature;
import dev.wyck.worldgen.feature.configurations.ReplaceBlockFeature;
import dev.wyck.worldgen.feature.configurations.SculkPatchFeature;
import dev.wyck.worldgen.feature.configurations.SpringFeature;
import dev.wyck.worldgen.feature.configurations.SpikeFeature;
import dev.wyck.worldgen.feature.configurations.SpeleothemClusterFeature;
import dev.wyck.worldgen.feature.configurations.SpeleothemFeature;
import dev.wyck.worldgen.feature.configurations.UnderwaterMagmaFeature;
import dev.wyck.worldgen.feature.configurations.WeightedRandomSelectorFeature;
import dev.wyck.worldgen.feature.configurations.VegetationPatchFeature;
import dev.wyck.worldgen.feature.configurations.end.EndSpike;
import dev.wyck.worldgen.feature.types.ComposedFeature;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.worldgen.material.FluidState;
import dev.wyck.worldgen.material.FluidType;
import dev.wyck.worldgen.stateproviders.SimpleStateProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import dev.wyck.worldgen.valueproviders.FloatProvider;
import dev.wyck.worldgen.placement.PlacedFeatures;
import dev.wyck.worldgen.ruletest.RuleTest;
import dev.wyck.tags.TagSet;
import dev.wyck.worldgen.surface.condition.CaveSurface;
import net.minecraft.core.registries.BuiltInRegistries;
import org.bukkit.Material;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MinecraftBootstrap.class)
class FeatureDecodeTest {

    private static final Set<ResourceKey> UNWRAPPED = Set.of(
        key("single_block_pillar"),
        key("random_neighbor_spread"),
        key("projected_random_patchy_square"),
        key("overlay"),
        key("end_podium"),
        key("stepped_column_cluster")
    );

    @Test
    void everyVanillaFeatureTypeIsDecodedOrExplicitlyUnwrapped() {
        var decoders = new dev.wyck.decode.worldgen.feature.FeatureConfigurationDecoders();
        List<ResourceKey> missing = BuiltInRegistries.FEATURE_TYPE.keySet().stream()
            .map(id -> ResourceKey.of(id.getNamespace(), id.getPath()))
            .filter(key -> !UNWRAPPED.contains(key))
            .filter(key -> !decoders.handles(key))
            .toList();
        assertTrue(missing.isEmpty(), () -> "no feature configuration decoder is registered for: " + missing);

        List<ResourceKey> stale = UNWRAPPED.stream()
            .filter(decoders::handles)
            .toList();
        assertTrue(stale.isEmpty(), () -> "these feature types now have decoders: " + stale);
    }

    @Test
    void simpleConfigurationShapesDecode() {
        assertConfig(FeatureType.NO_OP, NoOpFeature.INSTANCE, NoOpFeature.class);

        BambooFeature probability = assertInstanceOf(BambooFeature.class,
            decode(FeatureType.BAMBOO, BambooFeature.of(0.35f)).config());
        assertEquals(0.35f, probability.probability());

        IcebergFeature state = assertInstanceOf(IcebergFeature.class,
            decode(FeatureType.ICEBERG, IcebergFeature.of(
                BukkitBootstrapUtil.util().createBlockData(Material.PACKED_ICE)
            )).config());
        assertEquals(Material.PACKED_ICE, state.state().getMaterial());

        FillLayerFeature layer = assertInstanceOf(FillLayerFeature.class,
            decode(FeatureType.FILL_LAYER, FillLayerFeature.of(
                6, BukkitBootstrapUtil.util().createBlockData(Material.STONE)
            )).config());
        assertEquals(6, layer.height());
        assertEquals(Material.STONE, layer.state().getMaterial());

        UnderwaterMagmaFeature magma = assertInstanceOf(UnderwaterMagmaFeature.class,
            decode(FeatureType.UNDERWATER_MAGMA, UnderwaterMagmaFeature.of(32, 4, 0.6f)).config());
        assertEquals(32, magma.floorSearchRange());
        assertEquals(4, magma.placementRadiusAroundFloor());
        assertEquals(0.6f, magma.placementProbabilityPerValidPosition());
    }

    @Test
    void configurationsStackThroughStateProviders() {
        BlockPileFeature pile = assertInstanceOf(BlockPileFeature.class,
            decode(FeatureType.BLOCK_PILE, BlockPileFeature.of(
                BlockStateProvider.simple(Material.MOSS_BLOCK)
            )).config());
        assertInstanceOf(SimpleStateProvider.class, pile.stateProvider());

        SimpleBlockFeature simple = assertInstanceOf(SimpleBlockFeature.class,
            decode(FeatureType.SIMPLE_BLOCK, SimpleBlockFeature.of(
                BlockStateProvider.simple(Material.AZALEA), true
            )).config());
        assertInstanceOf(SimpleStateProvider.class, simple.toPlace());
        assertTrue(simple.scheduleTick());
    }

    @Test
    void providerAndScalarConfigurationBatchDecodes() {
        DeltaFeature delta = assertInstanceOf(DeltaFeature.class,
            decode(FeatureType.DELTA_FEATURE, DeltaFeature.of(
                data(Material.LAVA), data(Material.MAGMA_BLOCK),
                IntProvider.uniform(1, 5), IntProvider.uniform(0, 2)
            )).config());
        assertEquals(Material.LAVA, delta.contents().getMaterial());
        assertEquals(5, delta.size().maxInclusive());

        ReplaceBlobsFeature replace = assertInstanceOf(ReplaceBlobsFeature.class,
            decode(FeatureType.REPLACE_BLOBS, ReplaceBlobsFeature.of(
                data(Material.NETHERRACK), data(Material.BLACKSTONE), IntProvider.uniform(2, 6)
            )).config());
        assertEquals(Material.BLACKSTONE, replace.replaceState().getMaterial());

        BlockBlobFeature blob = assertInstanceOf(BlockBlobFeature.class,
            decode(FeatureType.BLOCK_BLOB, BlockBlobFeature.of(
                data(Material.MOSSY_COBBLESTONE), BlockPredicate.matchingBlocks().block(Material.STONE).build()
            )).config());
        assertInstanceOf(dev.wyck.worldgen.blockpredicates.MatchingBlocksPredicate.class, blob.canPlaceOn());
    }

    @Test
    void nestedWorldgenConfigurationBatchDecodes() {
        HugeRedMushroomFeature mushroom = assertInstanceOf(HugeRedMushroomFeature.class,
            decode(FeatureType.HUGE_RED_MUSHROOM, HugeRedMushroomFeature.of(
                BlockStateProvider.simple(Material.RED_MUSHROOM_BLOCK),
                BlockStateProvider.simple(Material.MUSHROOM_STEM), 3,
                BlockPredicate.matchingBlocks().block(Material.DIRT).build()
            )).config());
        assertEquals(3, mushroom.foliageRadius());
        assertInstanceOf(SimpleStateProvider.class, mushroom.capProvider());

        DiskFeature disk = assertInstanceOf(DiskFeature.class,
            decode(FeatureType.DISK, DiskFeature.of(
                BlockStateProvider.simple(Material.SAND),
                BlockPredicate.matchingBlocks().block(Material.DIRT).build(),
                IntProvider.uniform(2, 6), 2
            )).config());
        assertEquals(6, disk.radius().maxInclusive());
        assertEquals(2, disk.halfHeight());

        LakeFeature lake = assertInstanceOf(LakeFeature.class,
            decode(FeatureType.LAKE, LakeFeature.create(
                BlockStateProvider.simple(Material.WATER),
                BlockStateProvider.simple(Material.STONE),
                BlockPredicate.alwaysTrue(),
                BlockPredicate.replaceable().build(),
                BlockPredicate.matchingBlocks().block(Material.DIRT).build()
            )).config());
        assertInstanceOf(SimpleStateProvider.class, lake.fluid());
        assertInstanceOf(dev.wyck.worldgen.blockpredicates.ReplaceablePredicate.class,
            lake.canReplaceWithAirOrFluid());

        SculkPatchFeature sculk = assertInstanceOf(SculkPatchFeature.class,
            decode(FeatureType.SCULK_PATCH, SculkPatchFeature.of(4, 32, 16, 2, 3)).config());
        assertEquals(4, sculk.chargeCount());
        assertEquals(3, sculk.spreadRounds());

        SpringFeature spring = assertInstanceOf(SpringFeature.class,
            decode(FeatureType.SPRING, SpringFeature.of(
                FluidState.flowing(FluidType.FLOWING_WATER, 5, true),
                true, 4, 1, Set.of(Material.STONE, Material.DEEPSLATE)
            )).config());
        assertEquals(FluidType.FLOWING_WATER, spring.state().fluid());
        assertEquals(5, spring.state().amount());
        assertTrue(spring.state().falling());
        assertEquals(Set.of(Material.STONE, Material.DEEPSLATE), spring.validBlocks());

        MultifaceGrowthFeature multiface = assertInstanceOf(MultifaceGrowthFeature.class,
            decode(FeatureType.MULTIFACE_GROWTH, MultifaceGrowthFeature.of(
                Material.GLOW_LICHEN, 12, true, false, true, 0.4f,
                Set.of(Material.STONE, Material.DIRT)
            )).config());
        assertEquals(Material.GLOW_LICHEN, multiface.placeBlock());
        assertEquals(Set.of(Material.STONE, Material.DIRT), multiface.canBePlacedOn());
    }

    @Test
    void selectorConfigurationsStackThroughPlacedFeatures() {
        RandomSelectorFeature random = assertInstanceOf(RandomSelectorFeature.class,
            decode(FeatureType.RANDOM_SELECTOR, RandomSelectorFeature.of(
                List.of(RandomSelectorFeature.weighted(PlacedFeatures.SEAGRASS_WARM, 0.4f)),
                PlacedFeatures.SEA_PICKLE
            )).config());
        assertEquals(0.4f, random.features().getFirst().chance());
        assertEquals(ResourceKey.minecraft("seagrass_warm"),
            ((dev.wyck.worldgen.placement.PlacedFeature.Reference) random.features().getFirst().feature()).key());

        WeightedRandomSelectorFeature weighted = assertInstanceOf(WeightedRandomSelectorFeature.class,
            decode(FeatureType.WEIGHTED_RANDOM_SELECTOR, WeightedRandomSelectorFeature.of(
                WeightedList.of(List.of(
                    new WeightedList.Weighted<>(PlacedFeatures.SEAGRASS_WARM, 3),
                    new WeightedList.Weighted<>(PlacedFeatures.KELP_WARM, 1)
                ))
            )).config());
        assertEquals(3, weighted.features().unwrap().getFirst().weight());

        SequenceFeature composite = assertInstanceOf(SequenceFeature.class,
            decode(FeatureType.SEQUENCE, SequenceFeature.of(
                List.of(PlacedFeatures.SEAGRASS_WARM, PlacedFeatures.KELP_WARM)
            )).config());
        assertEquals(2, composite.features().size());

        RandomBooleanSelectorFeature bool = assertInstanceOf(RandomBooleanSelectorFeature.class,
            decode(FeatureType.RANDOM_BOOLEAN_SELECTOR, RandomBooleanSelectorFeature.of(
                PlacedFeatures.SEAGRASS_WARM, PlacedFeatures.KELP_WARM
            )).config());
        assertInstanceOf(dev.wyck.worldgen.placement.PlacedFeature.Reference.class, bool.featureTrue());
        assertInstanceOf(dev.wyck.worldgen.placement.PlacedFeature.Reference.class, bool.featureFalse());
    }

    @Test
    void endAndSpeleothemConfigurationsDecode() {
        EndGatewayFeature gateway = assertInstanceOf(EndGatewayFeature.class,
            decode(FeatureType.END_GATEWAY, EndGatewayFeature.knownExit(
                new org.bukkit.util.BlockVector(12, 80, -7), true
            )).config());
        assertEquals(new org.bukkit.util.BlockVector(12, 80, -7), gateway.exit().orElseThrow());
        assertTrue(gateway.exact());

        EndSpikeFeature spikes = assertInstanceOf(EndSpikeFeature.class,
            decode(FeatureType.END_SPIKE, EndSpikeFeature.of(
                true, List.of(EndSpike.of(10, -20, 3, 90, true)),
                new org.bukkit.util.BlockVector(0, 128, 0)
            )).config());
        assertTrue(spikes.crystalInvulnerable());
        assertEquals(90, spikes.spikes().getFirst().height());
        assertTrue(spikes.spikes().getFirst().guarded());
        assertEquals(new org.bukkit.util.BlockVector(0, 128, 0), spikes.crystalBeamTarget());

        SpeleothemFeature speleothem = assertInstanceOf(SpeleothemFeature.class,
            decode(FeatureType.SPELEOTHEM, SpeleothemFeature.of(
                data(Material.DRIPSTONE_BLOCK), data(Material.POINTED_DRIPSTONE),
                Set.of(Material.STONE, Material.DEEPSLATE), 0.3f, 0.6f, 0.4f, 0.2f
            )).config());
        assertEquals(Material.POINTED_DRIPSTONE, speleothem.pointedBlock().getMaterial());
        assertEquals(0.3f, speleothem.chanceOfTallerGeneration());

        LargeDripstoneFeature large = assertInstanceOf(LargeDripstoneFeature.class,
            decode(FeatureType.LARGE_DRIPSTONE, LargeDripstoneFeature.of(
                Set.of(Material.STONE), 30, IntProvider.uniform(2, 5), FloatProvider.uniform(0.5f, 1.5f),
                0.5f, FloatProvider.uniform(0.5f, 2.0f), FloatProvider.constant(1.0f),
                FloatProvider.uniform(0.0f, 1.0f), 2, 0.5f
            )).config());
        assertEquals(5, large.columnRadius().maxInclusive());
        assertEquals(2, large.minRadiusForWind());

        SpeleothemClusterFeature cluster = assertInstanceOf(SpeleothemClusterFeature.class,
            decode(FeatureType.SPELEOTHEM_CLUSTER, SpeleothemClusterFeature.builder()
                .baseBlock(data(Material.DRIPSTONE_BLOCK))
                .pointedBlock(data(Material.POINTED_DRIPSTONE))
                .replaceableBlocks(Set.of(Material.STONE))
                .floorToCeilingSearchRange(30)
                .height(IntProvider.uniform(3, 8))
                .radius(IntProvider.uniform(1, 4))
                .maxStalagmiteStalactiteHeightDiff(4)
                .heightDeviation(2)
                .speleothemBlockLayerThickness(IntProvider.uniform(1, 3))
                .density(FloatProvider.uniform(0.5f, 1.0f))
                .wetness(FloatProvider.uniform(0.0f, 0.5f))
                .chanceOfSpeleothemAtMaxDistanceFromCenter(0.2f)
                .maxDistanceFromEdgeAffectingChanceOfSpeleothem(8)
                .maxDistanceFromCenterAffectingHeightBias(12)
                .build()).config());
        assertEquals(8, cluster.height().maxInclusive());
        assertEquals(3, cluster.speleothemBlockLayerThickness().maxInclusive());
    }

    @Test
    void spikeAndBlockColumnConfigurationsStackTheirChildren() {
        SpikeFeature spike = assertInstanceOf(SpikeFeature.class,
            decode(FeatureType.SPIKE, SpikeFeature.of(
                data(Material.PACKED_ICE),
                BlockPredicate.matchingBlocks().block(Material.SNOW_BLOCK).build(),
                BlockPredicate.matchingBlocks().block(Material.AIR).build()
            )).config());
        assertEquals(Material.PACKED_ICE, spike.state().getMaterial());
        assertInstanceOf(dev.wyck.worldgen.blockpredicates.MatchingBlocksPredicate.class, spike.canReplace());

        BlockColumnFeature column = assertInstanceOf(BlockColumnFeature.class,
            decode(FeatureType.BLOCK_COLUMN, BlockColumnFeature.of(
                List.of(
                    BlockColumnFeature.layer(IntProvider.constant(3), BlockStateProvider.simple(Material.CAVE_VINES)),
                    BlockColumnFeature.layer(IntProvider.uniform(1, 4), BlockStateProvider.simple(Material.CAVE_VINES_PLANT))
                ), org.bukkit.block.BlockFace.DOWN, BlockPredicate.alwaysTrue(), true
            )).config());
        assertEquals(2, column.layers().size());
        assertEquals(4, column.layers().get(1).height().maxInclusive());
        assertEquals(org.bukkit.block.BlockFace.DOWN, column.direction());
        assertTrue(column.prioritizeTip());
    }

    @Test
    void oreConfigurationsStackThroughRuleTests() {
        OreFeature ore = assertInstanceOf(OreFeature.class,
            decode(FeatureType.ORE, OreFeature.of(
                List.of(
                    OreFeature.target(RuleTest.tagMatch(ResourceKey.minecraft("stone_ore_replaceables")), data(Material.IRON_ORE)),
                    OreFeature.target(RuleTest.blockMatch(Material.DEEPSLATE), data(Material.DEEPSLATE_IRON_ORE))
                ), 9, 0.25f
            )).config());
        assertEquals(2, ore.targetStates().size());
        assertInstanceOf(dev.wyck.worldgen.ruletest.TagMatchTest.class, ore.targetStates().getFirst().target());
        assertEquals(Material.DEEPSLATE_IRON_ORE, ore.targetStates().get(1).state().getMaterial());
        assertEquals(9, ore.size());
        assertEquals(0.25f, ore.discardChanceOnAirExposure());

        OreFeature scattered = assertInstanceOf(OreFeature.class,
            decode(FeatureType.SCATTERED_ORE, OreFeature.of(
                List.of(OreFeature.target(RuleTest.blockMatch(Material.NETHERRACK), data(Material.GOLD_ORE))),
                4, 0.0f
            )).config());
        assertInstanceOf(dev.wyck.worldgen.ruletest.BlockMatchTest.class,
            scattered.targetStates().getFirst().target());

        ReplaceBlockFeature replace = assertInstanceOf(ReplaceBlockFeature.class,
            decode(FeatureType.REPLACE_SINGLE_BLOCK, ReplaceBlockFeature.of(List.of(
                OreFeature.target(RuleTest.blockStateMatch(Material.STONE), data(Material.CALCITE))
            ))).config());
        assertInstanceOf(dev.wyck.worldgen.ruletest.BlockStateMatchTest.class,
            replace.targetStates().getFirst().target());
        assertEquals(Material.CALCITE, replace.targetStates().getFirst().state().getMaterial());
    }

    @Test
    void vegetationPatchesPreserveTagSetsAndNestedWorldgen() {
        VegetationPatchFeature patch = assertInstanceOf(VegetationPatchFeature.class,
            decode(FeatureType.VEGETATION_PATCH, VegetationPatchFeature.of(
                TagSet.ofBlockTag(ResourceKey.minecraft("moss_replaceable")),
                BlockStateProvider.simple(Material.MOSS_BLOCK), PlacedFeatures.PATCH_GRASS_NORMAL,
                CaveSurface.FLOOR, IntProvider.uniform(1, 3), 0.2f, 8,
                0.6f, IntProvider.uniform(2, 5), 0.1f
            )).config());
        assertTrue(patch.replaceable().isTag());
        assertEquals(ResourceKey.minecraft("moss_replaceable"),
            patch.replaceable().value().right().orElseThrow().key());
        assertInstanceOf(SimpleStateProvider.class, patch.groundState());
        assertInstanceOf(dev.wyck.worldgen.placement.PlacedFeature.Reference.class, patch.vegetationFeature());
        assertEquals(5, patch.xzRadius().maxInclusive());

        VegetationPatchFeature waterlogged = assertInstanceOf(VegetationPatchFeature.class,
            decode(FeatureType.WATERLOGGED_VEGETATION_PATCH, VegetationPatchFeature.of(
                TagSet.ofBlocks(Set.of(Material.DIRT, Material.CLAY)),
                BlockStateProvider.simple(Material.CLAY), PlacedFeatures.SEAGRASS_WARM,
                CaveSurface.CEILING, IntProvider.constant(2), 0.0f, 4,
                1.0f, IntProvider.constant(3), 0.0f
            )).config());
        assertEquals(Set.of(Material.DIRT, Material.CLAY),
            waterlogged.replaceable().value().left().orElseThrow());
        assertEquals(CaveSurface.CEILING, waterlogged.surface());
    }

    private static <C extends FeatureConfiguration> void assertConfig(
        FeatureType type, C configuration, Class<C> expected
    ) {
        assertInstanceOf(expected, decode(type, configuration).config());
    }

    private static ComposedFeature decode(FeatureType type, FeatureConfiguration configuration) {
        Feature original = Feature.of(type, configuration);
        ComposedFeature decoded = assertInstanceOf(ComposedFeature.class,
            Feature.decode(original.asHandle()));
        assertEquals(type, decoded.type());
        return decoded;
    }

    private static ResourceKey key(String path) {
        return ResourceKey.minecraft(path);
    }

    private static org.bukkit.block.data.BlockData data(Material material) {
        return BukkitBootstrapUtil.util().createBlockData(material);
    }
}
