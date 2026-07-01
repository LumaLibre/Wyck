package me.outspending.biomesapi.wrapper.worldgen.placement;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.util.internal.InternalReflectUtil;
import me.outspending.biomesapi.wrapper.worldgen.BlockPredicate;
import me.outspending.biomesapi.wrapper.worldgen.HeightmapType;
import me.outspending.biomesapi.wrapper.worldgen.WorldgenConversions;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.HeightProvider;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.IntProvider;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public final class PlacementModifierFactoryImpl implements PlacementModifier.Factory {

    @Override
    public Object toNms(PlacementModifier modifier) {
        return switch (modifier) {
            case PlacementModifier.BiomeFilter ignored ->
                net.minecraft.world.level.levelgen.placement.BiomeFilter.biome();
            case PlacementModifier.InSquarePlacement ignored ->
                net.minecraft.world.level.levelgen.placement.InSquarePlacement.spread();
            case PlacementModifier.RarityFilter rarity ->
                net.minecraft.world.level.levelgen.placement.RarityFilter.onAverageOnceEvery(rarity.chance());
            case PlacementModifier.SurfaceWaterDepthFilter filter ->
                net.minecraft.world.level.levelgen.placement.SurfaceWaterDepthFilter.forMaxDepth(filter.maxWaterDepth());
            case PlacementModifier.NoiseBasedCountPlacement noise ->
                net.minecraft.world.level.levelgen.placement.NoiseBasedCountPlacement.of(noise.noiseToCountRatio(), noise.noiseFactor(), noise.noiseOffset());
            case PlacementModifier.NoiseThresholdCountPlacement noise ->
                net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement.of(noise.noiseLevel(), noise.belowNoise(), noise.aboveNoise());
            case PlacementModifier.HeightmapPlacement heightmap -> {
                net.minecraft.world.level.levelgen.Heightmap.Types type = toNmsHeightmap(heightmap.heightmap());
                yield net.minecraft.world.level.levelgen.placement.HeightmapPlacement.onHeightmap(type);
            }
            case PlacementModifier.SurfaceRelativeThresholdFilter filter -> {
                net.minecraft.world.level.levelgen.Heightmap.Types type = toNmsHeightmap(filter.heightmap());
                yield net.minecraft.world.level.levelgen.placement.SurfaceRelativeThresholdFilter.of(type, filter.minInclusive(), filter.maxInclusive());
            }
            case PlacementModifier.HeightRangePlacement heightRange -> {
                net.minecraft.world.level.levelgen.heightproviders.HeightProvider height =
                    (net.minecraft.world.level.levelgen.heightproviders.HeightProvider) heightRange.height().toMinecraft();
                yield net.minecraft.world.level.levelgen.placement.HeightRangePlacement.of(height);
            }
            case PlacementModifier.FixedPlacement fixed -> toNmsFixedPlacement(fixed);
            case PlacementModifier.CountPlacement count -> {
                net.minecraft.util.valueproviders.IntProvider provider = toNmsInt(count.count());
                yield net.minecraft.world.level.levelgen.placement.CountPlacement.of(provider);
            }
            case PlacementModifier.RandomOffsetPlacement offset -> {
                net.minecraft.util.valueproviders.IntProvider xz = toNmsInt(offset.xzSpread());
                net.minecraft.util.valueproviders.IntProvider y = toNmsInt(offset.ySpread());
                yield net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement.of(xz, y);
            }
            case PlacementModifier.BlockPredicateFilter filter -> {
                net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate predicate = toNmsPredicate(filter.predicate());
                yield net.minecraft.world.level.levelgen.placement.BlockPredicateFilter.forPredicate(predicate);
            }
            case PlacementModifier.EnvironmentScanPlacement scan -> toNmsEnvironmentScan(scan);
        };
    }

    @Override
    public PlacementModifier fromMinecraft(Object nms) {
        return switch (nms) {
            case net.minecraft.world.level.levelgen.placement.BiomeFilter ignored ->
                PlacementModifier.biomeFilter();

            case net.minecraft.world.level.levelgen.placement.InSquarePlacement ignored ->
                PlacementModifier.inSquare();

            case net.minecraft.world.level.levelgen.placement.RarityFilter rarity -> {
                int chance = InternalReflectUtil.getFieldValue(rarity, "chance");
                yield new PlacementModifier.RarityFilter(chance);
            }

            case net.minecraft.world.level.levelgen.placement.SurfaceWaterDepthFilter filter -> {
                int maxWaterDepth = InternalReflectUtil.getFieldValue(filter, "maxWaterDepth");
                yield new PlacementModifier.SurfaceWaterDepthFilter(maxWaterDepth);
            }

            case net.minecraft.world.level.levelgen.placement.NoiseBasedCountPlacement noise -> {
                int ratio = InternalReflectUtil.getFieldValue(noise, "noiseToCountRatio");
                double factor = InternalReflectUtil.getFieldValue(noise, "noiseFactor");
                double offset = InternalReflectUtil.getFieldValue(noise, "noiseOffset");
                yield new PlacementModifier.NoiseBasedCountPlacement(ratio, factor, offset);
            }

            case net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement noise -> {
                double noiseLevel = InternalReflectUtil.getFieldValue(noise, "noiseLevel");
                int belowNoise = InternalReflectUtil.getFieldValue(noise, "belowNoise");
                int aboveNoise = InternalReflectUtil.getFieldValue(noise, "aboveNoise");
                yield new PlacementModifier.NoiseThresholdCountPlacement(noiseLevel, belowNoise, aboveNoise);
            }

            case net.minecraft.world.level.levelgen.placement.HeightmapPlacement heightmap -> {
                net.minecraft.world.level.levelgen.Heightmap.Types type = InternalReflectUtil.getFieldValue(heightmap, "heightmap");
                yield new PlacementModifier.HeightmapPlacement(fromNmsHeightmap(type));
            }

            case net.minecraft.world.level.levelgen.placement.SurfaceRelativeThresholdFilter filter -> {
                net.minecraft.world.level.levelgen.Heightmap.Types type = InternalReflectUtil.getFieldValue(filter, "heightmap");
                int minInclusive = InternalReflectUtil.getFieldValue(filter, "minInclusive");
                int maxInclusive = InternalReflectUtil.getFieldValue(filter, "maxInclusive");
                yield new PlacementModifier.SurfaceRelativeThresholdFilter(fromNmsHeightmap(type), minInclusive, maxInclusive);
            }

            case net.minecraft.world.level.levelgen.placement.HeightRangePlacement heightRange -> {
                net.minecraft.world.level.levelgen.heightproviders.HeightProvider height = InternalReflectUtil.getFieldValue(heightRange, "height");
                yield new PlacementModifier.HeightRangePlacement(HeightProvider.fromMinecraft(height));
            }

            case net.minecraft.world.level.levelgen.placement.FixedPlacement fixed -> fromNmsFixedPlacement(fixed);

            case net.minecraft.world.level.levelgen.placement.CountPlacement count -> {
                net.minecraft.util.valueproviders.IntProvider provider = InternalReflectUtil.getFieldValue(count, "count");
                yield new PlacementModifier.CountPlacement(IntProvider.fromMinecraft(provider));
            }

            case net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement offset -> {
                net.minecraft.util.valueproviders.IntProvider xz = InternalReflectUtil.getFieldValue(offset, "xzSpread");
                net.minecraft.util.valueproviders.IntProvider y = InternalReflectUtil.getFieldValue(offset, "ySpread");
                yield new PlacementModifier.RandomOffsetPlacement(IntProvider.fromMinecraft(xz), IntProvider.fromMinecraft(y));
            }

            case net.minecraft.world.level.levelgen.placement.BlockPredicateFilter filter -> {
                net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate predicate = InternalReflectUtil.getFieldValue(filter, "predicate");
                yield new PlacementModifier.BlockPredicateFilter(BlockPredicate.fromMinecraft(predicate));
            }

            case net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement scan -> fromNmsEnvironmentScan(scan);

            default -> throw new IllegalArgumentException("Unsupported placement modifier: " + nms.getClass().getName());
        };
    }

    private net.minecraft.world.level.levelgen.Heightmap.Types toNmsHeightmap(HeightmapType heightmap) {
        return heightmap.toNms(net.minecraft.world.level.levelgen.Heightmap.Types.class);
    }

    private HeightmapType fromNmsHeightmap(net.minecraft.world.level.levelgen.Heightmap.Types type) {
        return HeightmapType.TRANSLATOR.fromNms(type);
    }

    private net.minecraft.util.valueproviders.IntProvider toNmsInt(IntProvider provider) {
        return (net.minecraft.util.valueproviders.IntProvider) provider.toMinecraft();
    }

    private net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate toNmsPredicate(BlockPredicate predicate) {
        return (net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate) predicate.toMinecraft();
    }

    private net.minecraft.world.level.levelgen.placement.FixedPlacement toNmsFixedPlacement(PlacementModifier.FixedPlacement fixed) {
        List<BlockVector> positions = fixed.positions();
        net.minecraft.core.BlockPos[] blockPositions = new net.minecraft.core.BlockPos[positions.size()];
        int index = 0;
        for (BlockVector vector : positions) {
            blockPositions[index] = new net.minecraft.core.BlockPos(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());
            index++;
        }

        return net.minecraft.world.level.levelgen.placement.FixedPlacement.of(blockPositions);
    }

    private PlacementModifier fromNmsFixedPlacement(net.minecraft.world.level.levelgen.placement.FixedPlacement fixed) {
        List<net.minecraft.core.BlockPos> nmsPositions = InternalReflectUtil.getFieldValue(fixed, "positions");
        List<BlockVector> positions = new ArrayList<>(nmsPositions.size());
        for (net.minecraft.core.BlockPos pos : nmsPositions) {
            positions.add(new BlockVector(pos.getX(), pos.getY(), pos.getZ()));
        }
        return new PlacementModifier.FixedPlacement(positions);
    }

    private net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement toNmsEnvironmentScan(PlacementModifier.EnvironmentScanPlacement scan) {
        net.minecraft.core.Direction direction = WorldgenConversions.toNmsDirection(scan.directionOfSearch());
        net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate target = toNmsPredicate(scan.targetCondition());
        net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate allowed = toNmsPredicate(scan.allowedSearchCondition());
        return net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement.scanningFor(direction, target, allowed, scan.maxSteps());
    }

    private PlacementModifier fromNmsEnvironmentScan(net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement scan) {
        net.minecraft.core.Direction direction = InternalReflectUtil.getFieldValue(scan, "directionOfSearch");
        net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate target = InternalReflectUtil.getFieldValue(scan, "targetCondition");
        net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate allowed = InternalReflectUtil.getFieldValue(scan, "allowedSearchCondition");
        int maxSteps = InternalReflectUtil.getFieldValue(scan, "maxSteps");
        return new PlacementModifier.EnvironmentScanPlacement(
            CraftBlock.notchToBlockFace(direction),
            BlockPredicate.fromMinecraft(target),
            BlockPredicate.fromMinecraft(allowed),
            maxSteps
        );
    }
}