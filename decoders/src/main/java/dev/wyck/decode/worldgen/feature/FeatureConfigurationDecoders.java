package dev.wyck.decode.worldgen.feature;

import dev.wyck.decode.Decoders;
import dev.wyck.decode.FastReflection;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.blockpredicates.BlockPredicate;
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
import dev.wyck.worldgen.feature.configurations.FallenTreeFeature;
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
import dev.wyck.worldgen.feature.configurations.RootSystemFeature;
import dev.wyck.worldgen.feature.configurations.SculkPatchFeature;
import dev.wyck.worldgen.feature.configurations.SpringFeature;
import dev.wyck.worldgen.feature.configurations.SpikeFeature;
import dev.wyck.worldgen.feature.configurations.SpeleothemClusterFeature;
import dev.wyck.worldgen.feature.configurations.SpeleothemFeature;
import dev.wyck.worldgen.feature.configurations.UnderwaterMagmaFeature;
import dev.wyck.worldgen.feature.configurations.WeightedRandomSelectorFeature;
import dev.wyck.worldgen.feature.configurations.VegetationPatchFeature;
import dev.wyck.worldgen.feature.configurations.TreeFeature;
import dev.wyck.worldgen.feature.configurations.TemplateFeature;
import dev.wyck.worldgen.feature.configurations.GeodeFeature;
import dev.wyck.worldgen.feature.configurations.FossilFeature;
import dev.wyck.worldgen.structure.templatesystem.ProcessorList;
import dev.wyck.worldgen.structure.templatesystem.StructureTemplate;
import dev.wyck.worldgen.feature.configurations.HugeFungusFeature;
import dev.wyck.worldgen.feature.configurations.geode.GeodeBlockSettings;
import dev.wyck.worldgen.feature.configurations.geode.GeodeCrackSettings;
import dev.wyck.worldgen.feature.configurations.geode.GeodeLayerSettings;
import dev.wyck.worldgen.Rotation;
import dev.wyck.worldgen.feature.configurations.end.EndSpike;
import dev.wyck.worldgen.ruletest.RuleTest;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.worldgen.material.FluidState;
import dev.wyck.worldgen.valueproviders.IntProvider;
import dev.wyck.worldgen.valueproviders.FloatProvider;
import dev.wyck.worldgen.placement.PlacedFeature;
import dev.wyck.wrapper.decode.DecoderRegistry;
import dev.wyck.tags.TagSet;
import dev.wyck.worldgen.surface.condition.CaveSurface;
import dev.wyck.worldgen.feature.featuresize.FeatureSize;
import dev.wyck.worldgen.feature.foliageplacers.FoliagePlacer;
import dev.wyck.worldgen.feature.rootplacers.RootPlacer;
import dev.wyck.worldgen.feature.treedecorators.TreeDecorator;
import dev.wyck.worldgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.core.registries.BuiltInRegistries;
import org.bukkit.craftbukkit.block.CraftBlockType;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class FeatureConfigurationDecoders extends DecoderRegistry<FeatureConfiguration, net.minecraft.world.level.levelgen.feature.Feature> {

    private static final String[] NO_OP_CODECS = {
        "no_op",
        // TODO: wrap the rest of these
        "chorus_plant",
        "void_start_platform",
        "desert_well",
        "glowstone_blob",
        "freeze_top_layer",
        "vines",
        "monster_room",
        "blue_ice",
        "end_platform",
        "end_island",
        "kelp",
        "coral_tree",
        "coral_mushroom",
        "coral_claw",
        "weeping_vines",
        "bonus_chest",
        "basalt_pillar"
    };

    public FeatureConfigurationDecoders() {
        for (String type : NO_OP_CODECS) {
            register(type, _ -> NoOpFeature.INSTANCE);
        }
        register("bamboo", configured -> BambooFeature.of(
            ((net.minecraft.world.level.levelgen.feature.BambooFeature) configured).probability()
        ));
        register("iceberg", configured -> IcebergFeature.of(Decoders.blockData(
            ((net.minecraft.world.level.levelgen.feature.IcebergFeature) configured).state()
        )));
        register("block_pile", configured -> BlockPileFeature.of(BlockStateProvider.decode(
            ((net.minecraft.world.level.levelgen.feature.BlockPileFeature) configured).stateProvider()
        )));
        register("simple_block", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.SimpleBlockFeature) configured;
            return SimpleBlockFeature.of(BlockStateProvider.decode(config.toPlace()), config.scheduleTick());
        });
        register("fill_layer", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.FillLayerFeature) configured;
            return FillLayerFeature.of(config.height(), Decoders.blockData(config.state()));
        });
        register("underwater_magma", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.UnderwaterMagmaFeature) configured;
            return UnderwaterMagmaFeature.of(
                config.floorSearchRange(),
                config.placementRadiusAroundFloor(),
                config.placementProbabilityPerValidPosition()
            );
        });
        register("delta_feature", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.DeltaFeature) configured;
            return DeltaFeature.of(
                Decoders.blockData(config.contents()), Decoders.blockData(config.rim()),
                IntProvider.decode(config.size()), IntProvider.decode(config.rimSize())
            );
        });
        register("netherrack_replace_blobs", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.ReplaceBlobsFeature) configured;
            return ReplaceBlobsFeature.of(
                Decoders.blockData(config.targetState()), Decoders.blockData(config.replaceState()),
                IntProvider.decode(config.radius())
            );
        });
        register("block_blob", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.BlockBlobFeature) configured;
            return BlockBlobFeature.of(
                Decoders.blockData(config.state()), BlockPredicate.decode(config.canPlaceOn())
            );
        });
        register("huge_red_mushroom", this::hugeMushroom);
        register("huge_brown_mushroom", this::hugeMushroom);
        register("disk", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.DiskFeature) configured;
            return DiskFeature.of(
                BlockStateProvider.decode(config.stateProvider()),
                BlockPredicate.decode(config.target()),
                IntProvider.decode(config.radius()),
                config.halfHeight()
            );
        });
        register("lake", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.LakeFeature) configured;
            return LakeFeature.create(
                BlockStateProvider.decode(config.fluid()),
                BlockStateProvider.decode(config.barrier()),
                BlockPredicate.decode(config.canPlaceFeature()),
                BlockPredicate.decode(config.canReplaceWithAirOrFluid()),
                BlockPredicate.decode(config.canReplaceWithBarrier())
            );
        });
        register("sculk_patch", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.SculkPatchFeature) configured;
            return SculkPatchFeature.of(
                config.chargeCount(), config.amountPerCharge(), config.spreadAttempts(),
                config.growthRounds(), config.spreadRounds()
            );
        });
        register("spring_feature", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.SpringFeature) configured;
            return SpringFeature.of(
                FluidState.decode(config.state()), config.requiresBlockBelow(),
                config.rockCount(), config.holeCount(), Decoders.materials(config.validBlocks())
            );
        });
        register("multiface_growth", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.MultifaceGrowthFeature) configured;
            return MultifaceGrowthFeature.of(
                CraftBlockType.minecraftToBukkit(config.placeBlock()), config.searchRange(),
                config.canPlaceOnFloor(), config.canPlaceOnCeiling(), config.canPlaceOnWall(),
                config.chanceOfSpreading(), Decoders.materials(config.canBePlacedOn())
            );
        });
        register("random_selector", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.RandomSelectorFeature) configured;
            return RandomSelectorFeature.of(
                config.features().stream().map(weighted -> RandomSelectorFeature.weighted(
                    PlacedFeature.decode(weighted.feature()), weighted.chance()
                )).toList(),
                PlacedFeature.decode(config.defaultFeature())
            );
        });
        register("weighted_random_selector", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.WeightedRandomSelectorFeature) configured;
            return WeightedRandomSelectorFeature.of(Decoders.weighted(
                config.features(), PlacedFeature::decode
            ));
        });
        register("simple_random_selector", this::composite);
        register("sequence", this::composite);
        register("random_boolean_selector", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.RandomBooleanSelectorFeature) configured;
            return RandomBooleanSelectorFeature.of(
                PlacedFeature.decode(config.featureTrue()),
                PlacedFeature.decode(config.featureFalse())
            );
        });
        register("end_gateway", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.EndGatewayFeature) configured;
            return config.exit()
                .map(exit -> EndGatewayFeature.knownExit(vector(exit), config.exact()))
                .orElseGet(EndGatewayFeature::delayedExitSearch);
        });
        register("end_spike", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.EndSpikeFeature) configured;
            var target = config.crystalBeamTarget();
            return EndSpikeFeature.of(
                config.crystalInvulnerable(),
                config.spikes().stream().map(EndSpike::decode).toList(),
                target.map(FeatureConfigurationDecoders::vector).orElse(null)
            );
        });
        register("speleothem", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.SpeleothemFeature) configured;
            return SpeleothemFeature.of(
                Decoders.blockData(config.baseBlock()), Decoders.blockData(config.pointedBlock()),
                Decoders.materials(config.replaceableBlocks()), config.chanceOfTallerGeneration(),
                config.chanceOfDirectionalSpread(), config.chanceOfSpreadRadius2(), config.chanceOfSpreadRadius3()
            );
        });
        register("large_dripstone", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.LargeDripstoneFeature) configured;
            return LargeDripstoneFeature.of(
                Decoders.materials(config.replaceableBlocks()), config.floorToCeilingSearchRange(),
                IntProvider.decode(config.columnRadius()), FloatProvider.decode(config.heightScale()),
                config.maxColumnRadiusToCaveHeightRatio(),
                FloatProvider.decode(config.stalactiteBluntness()),
                FloatProvider.decode(config.stalagmiteBluntness()),
                FloatProvider.decode(config.windSpeed()), config.minRadiusForWind(), config.minBluntnessForWind()
            );
        });
        register("speleothem_cluster", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.SpeleothemClusterFeature) configured;
            return SpeleothemClusterFeature.builder()
                .baseBlock(Decoders.blockData(config.baseBlock()))
                .pointedBlock(Decoders.blockData(config.pointedBlock()))
                .replaceableBlocks(Decoders.materials(config.replaceableBlocks()))
                .floorToCeilingSearchRange(config.floorToCeilingSearchRange())
                .height(IntProvider.decode(config.height()))
                .radius(IntProvider.decode(config.radius()))
                .maxStalagmiteStalactiteHeightDiff(config.maxStalagmiteStalactiteHeightDiff())
                .heightDeviation(config.heightDeviation())
                .speleothemBlockLayerThickness(IntProvider.decode(config.speleothemBlockLayerThickness()))
                .density(FloatProvider.decode(config.density()))
                .wetness(FloatProvider.decode(config.wetness()))
                .chanceOfSpeleothemAtMaxDistanceFromCenter(config.chanceOfSpeleothemAtMaxDistanceFromCenter())
                .maxDistanceFromEdgeAffectingChanceOfSpeleothem(config.maxDistanceFromEdgeAffectingChanceOfSpeleothem())
                .maxDistanceFromCenterAffectingHeightBias(config.maxDistanceFromCenterAffectingHeightBias())
                .build();
        });
        register("spike", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.SpikeFeature) configured;
            return SpikeFeature.of(
                Decoders.blockData(config.state()), BlockPredicate.decode(config.canPlaceOn()),
                BlockPredicate.decode(config.canReplace())
            );
        });
        register("block_column", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.BlockColumnFeature) configured;
            return BlockColumnFeature.of(
                config.layers().stream().map(layer -> BlockColumnFeature.layer(
                    IntProvider.decode(layer.height()), BlockStateProvider.decode(layer.state())
                )).toList(),
                CraftBlock.notchToBlockFace(config.direction()),
                BlockPredicate.decode(config.allowedPlacement()), config.prioritizeTip()
            );
        });
        register("ore", this::ore);
        register("scattered_ore", this::ore);
        register("replace_single_block", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.ReplaceBlockFeature)
                configured;
            return ReplaceBlockFeature.of(targets(config.replacements()));
        });
        register("vegetation_patch", this::vegetationPatch);
        register("waterlogged_vegetation_patch", this::vegetationPatch);
        register("tree", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.TreeFeature)
                configured;
            return TreeFeature.of(
                BlockStateProvider.decode(config.trunkProvider()),
                TrunkPlacer.decode(config.trunkPlacer()),
                BlockStateProvider.decode(config.foliageProvider()),
                FoliagePlacer.decode(config.foliagePlacer()),
                config.rootPlacer().map(RootPlacer::decode).orElse(null),
                FeatureSize.decode(config.minimumSize()),
                config.decorators().stream().map(TreeDecorator::decode).toList(),
                config.ignoreVines(), BlockStateProvider.decode(config.belowTrunkProvider())
            );
        });
        register("fallen_tree", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.FallenTreeFeature)
                configured;
            return FallenTreeFeature.of(
                BlockStateProvider.decode(config.trunkProvider()), IntProvider.decode(config.logLength()),
                config.stumpDecorators().stream().map(TreeDecorator::decode).toList(),
                config.logDecorators().stream().map(TreeDecorator::decode).toList()
            );
        });
        register("root_system", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.RootSystemFeature)
                configured;
            return RootSystemFeature.of(
                PlacedFeature.decode(config.treeFeature()), config.requiredVerticalSpaceForTree(),
                config.levelTestDistance(), config.maxLevelDeviation(), config.rootRadius(),
                TagSet.decodeBlocks(config.rootReplaceable()),
                BlockStateProvider.decode(config.rootStateProvider()), config.rootPlacementAttempts(),
                config.rootColumnMaxHeight(), config.hangingRootRadius(), config.hangingRootsVerticalSpan(),
                BlockStateProvider.decode(config.hangingRootStateProvider()),
                config.hangingRootPlacementAttempts(), config.allowedVerticalWaterForTree(),
                BlockPredicate.decode(config.allowedTreePosition())
            );
        });
        register("template", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.TemplateFeature)
                configured;
            return TemplateFeature.of(Decoders.weighted(config.templates(), entry ->
                new TemplateFeature.TemplateEntry(
                    StructureTemplate.of(Decoders.key(entry.template())),
                    entry.rotations().stream().map(Rotation.TRANSLATOR::fromNms).toList()
                )
            ));
        });
        register("geode", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.GeodeFeature)
                configured;
            return GeodeFeature.of(
                GeodeBlockSettings.decode(config.blockSettings()),
                GeodeLayerSettings.decode(config.layerSettings()),
                GeodeCrackSettings.decode(config.crackSettings()),
                config.usePotentialPlacementsChance(), config.useAlternateLayer0Chance(),
                config.placementsRequireLayer0Alternate(), IntProvider.decode(config.outerWallDistance()),
                IntProvider.decode(config.distributionPoints()), IntProvider.decode(config.pointOffset()),
                config.minGenOffset(), config.maxGenOffset(), config.noiseMultiplier(), config.invalidBlocksThreshold()
            );
        });
        register("huge_fungus", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.HugeFungusFeature)
                configured;
            return HugeFungusFeature.of(
                Decoders.blockData(config.validBaseState()), Decoders.blockData(config.stemState()),
                Decoders.blockData(config.hatState()), Decoders.blockData(config.decorState()),
                BlockPredicate.decode(config.replaceableBlocks()), config.planted()
            );
        });
        register("fossil", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.FossilFeature)
                configured;
            return FossilFeature.of(
                config.fossilStructures().stream().map(Decoders::key).map(StructureTemplate::of).toList(),
                config.overlayStructures().stream().map(Decoders::key).map(StructureTemplate::of).toList(),
                ProcessorList.decode(config.fossilProcessors()),
                ProcessorList.decode(config.overlayProcessors()),
                config.maxEmptyCornersAllowed()
            );
        });
    }

    @Override
    protected ResourceKey discriminate(net.minecraft.world.level.levelgen.feature.Feature feature) {
        return Decoders.registryKey(BuiltInRegistries.FEATURE_TYPE, feature.codec());
    }

    private static BlockVector vector(net.minecraft.core.BlockPos position) {
        return new BlockVector(position.getX(), position.getY(), position.getZ());
    }

    private FeatureConfiguration hugeMushroom(net.minecraft.world.level.levelgen.feature.Feature configured) {
        var config = (net.minecraft.world.level.levelgen.feature.AbstractHugeMushroomFeature) configured;
        return HugeRedMushroomFeature.of(
            BlockStateProvider.decode(config.capProvider()),
            BlockStateProvider.decode(config.stemProvider()),
            config.foliageRadius(),
            BlockPredicate.decode(config.canPlaceOn())
        );
    }

    private FeatureConfiguration composite(net.minecraft.world.level.levelgen.feature.Feature configured) {
        net.minecraft.core.HolderSet<net.minecraft.world.level.levelgen.placement.PlacedFeature> features = switch (configured) {
            case net.minecraft.world.level.levelgen.feature.SequenceFeature sequence -> sequence.features();
            case net.minecraft.world.level.levelgen.feature.SimpleRandomSelectorFeature selector -> selector.features();
            default -> throw new IllegalArgumentException("Not a composite feature: " + configured);
        };
        return SequenceFeature.of(features.stream()
            .map(PlacedFeature::decode)
            .toList());
    }

    private FeatureConfiguration ore(net.minecraft.world.level.levelgen.feature.Feature configured) {
        var config = (net.minecraft.world.level.levelgen.feature.AbstractOreFeature)
            configured;
        return OreFeature.of(
            targets(config.targetStates()), config.size(), config.discardChanceOnAirExposure()
        );
    }

    private FeatureConfiguration vegetationPatch(net.minecraft.world.level.levelgen.feature.Feature configured) {
        var config = (net.minecraft.world.level.levelgen.feature.VegetationPatchFeature)
            configured;
        return VegetationPatchFeature.of(
            TagSet.decodeBlocks(FastReflection.read(config, "replaceable")),
            BlockStateProvider.decode(FastReflection.read(config, "groundState")),
            PlacedFeature.decode(FastReflection.read(config, "vegetationFeature")),
            CaveSurface.TRANSLATOR.fromNms(FastReflection.read(config, "surface")),
            IntProvider.decode(FastReflection.read(config, "depth")), FastReflection.read(config, "extraBottomBlockChance"),
            FastReflection.read(config, "verticalRange"), FastReflection.read(config, "vegetationChance"),
            IntProvider.decode(FastReflection.read(config, "xzRadius")), FastReflection.read(config, "extraEdgeColumnChance")
        );
    }

    private static java.util.List<OreFeature.TargetBlockState> targets(
        java.util.List<net.minecraft.world.level.levelgen.feature.BlockReplacement> targets
    ) {
        return targets.stream().map(target -> OreFeature.target(
            RuleTest.decode(target.target()), Decoders.blockData(target.state())
        )).toList();
    }
}
