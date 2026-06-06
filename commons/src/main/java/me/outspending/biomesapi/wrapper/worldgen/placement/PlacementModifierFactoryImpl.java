package me.outspending.biomesapi.wrapper.worldgen.placement;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.wrapper.worldgen.BlockPredicate;
import me.outspending.biomesapi.wrapper.worldgen.HeightmapType;
import me.outspending.biomesapi.wrapper.worldgen.WorldgenConversions;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.IntProvider;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public final class PlacementModifierFactoryImpl implements PlacementModifier.Factory {

    @Override
    public @NotNull Object toNms(@NotNull PlacementModifier modifier) {
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

    private net.minecraft.world.level.levelgen.Heightmap.Types toNmsHeightmap(@NotNull HeightmapType heightmap) {
        return heightmap.toNms(net.minecraft.world.level.levelgen.Heightmap.Types.class);
    }

    private net.minecraft.util.valueproviders.IntProvider toNmsInt(@NotNull IntProvider provider) {
        return (net.minecraft.util.valueproviders.IntProvider) provider.toMinecraft();
    }

    private net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate toNmsPredicate(@NotNull BlockPredicate predicate) {
        return (net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate) predicate.toMinecraft();
    }

    private net.minecraft.world.level.levelgen.placement.FixedPlacement toNmsFixedPlacement(@NotNull PlacementModifier.FixedPlacement fixed) {
        List<BlockVector> positions = fixed.positions();
        net.minecraft.core.BlockPos[] blockPositions = new net.minecraft.core.BlockPos[positions.size()];
        int index = 0;
        for (BlockVector vector : positions) {
            blockPositions[index] = new net.minecraft.core.BlockPos(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());
            index++;
        }

        return net.minecraft.world.level.levelgen.placement.FixedPlacement.of(blockPositions);
    }

    private net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement toNmsEnvironmentScan(@NotNull PlacementModifier.EnvironmentScanPlacement scan) {
        net.minecraft.core.Direction direction = WorldgenConversions.toNmsDirection(scan.directionOfSearch());
        net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate target = toNmsPredicate(scan.targetCondition());
        net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate allowed = toNmsPredicate(scan.allowedSearchCondition());
        return net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement.scanningFor(direction, target, allowed, scan.maxSteps());
    }
}