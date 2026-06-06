package me.outspending.biomesapi.wrapper.worldgen.placement;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.wrapper.NmsHandle;
import me.outspending.biomesapi.wrapper.worldgen.BlockPredicate;
import me.outspending.biomesapi.wrapper.worldgen.HeightmapType;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.HeightProvider;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.IntProvider;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.VerticalAnchor;
import org.bukkit.block.BlockFace;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * Wraps the PlacementModifier family, the ordered transforms applied to
 * a feature's candidate positions during placement.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@AsOf("2.3.0")
public sealed interface PlacementModifier extends NmsHandle permits PlacementModifier.BiomeFilter, PlacementModifier.BlockPredicateFilter, PlacementModifier.CountPlacement, PlacementModifier.EnvironmentScanPlacement, PlacementModifier.FixedPlacement, PlacementModifier.HeightRangePlacement, PlacementModifier.HeightmapPlacement, PlacementModifier.InSquarePlacement, PlacementModifier.NoiseBasedCountPlacement, PlacementModifier.NoiseThresholdCountPlacement, PlacementModifier.RandomOffsetPlacement, PlacementModifier.RarityFilter, PlacementModifier.SurfaceRelativeThresholdFilter, PlacementModifier.SurfaceWaterDepthFilter {

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.worldgen.placement.PlacementModifierFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        @NotNull Object toNms(@NotNull PlacementModifier modifier);
    }

    @AsOf("2.3.0")
    static @NotNull PlacementModifier biomeFilter() {
        return new BiomeFilter();
    }

    @AsOf("2.3.0")
    static @NotNull PlacementModifier inSquare() {
        return new InSquarePlacement();
    }

    @AsOf("2.3.0")
    static @NotNull PlacementModifier rarityFilter(int chance) {
        return new RarityFilter(chance);
    }

    @AsOf("2.3.0")
    static @NotNull PlacementModifier surfaceWaterDepth(int maxWaterDepth) {
        return new SurfaceWaterDepthFilter(maxWaterDepth);
    }

    @AsOf("2.3.0")
    static @NotNull PlacementModifier noiseBasedCount(int noiseToCountRatio, double noiseFactor, double noiseOffset) {
        return new NoiseBasedCountPlacement(noiseToCountRatio, noiseFactor, noiseOffset);
    }

    @AsOf("2.3.0")
    static @NotNull PlacementModifier noiseThresholdCount(double noiseLevel, int belowNoise, int aboveNoise) {
        return new NoiseThresholdCountPlacement(noiseLevel, belowNoise, aboveNoise);
    }

    @AsOf("2.3.0")
    static @NotNull PlacementModifier heightmap(@NotNull HeightmapType heightmap) {
        return new HeightmapPlacement(heightmap);
    }

    @AsOf("2.3.0")
    static @NotNull PlacementModifier surfaceRelativeThreshold(@NotNull HeightmapType heightmap, int minInclusive, int maxInclusive) {
        return new SurfaceRelativeThresholdFilter(heightmap, minInclusive, maxInclusive);
    }

    @AsOf("2.3.0")
    static @NotNull PlacementModifier heightRange(@NotNull HeightProvider height) {
        return new HeightRangePlacement(height);
    }

    @AsOf("2.3.0")
    static @NotNull PlacementModifier heightRangeUniform(@NotNull VerticalAnchor minInclusive, @NotNull VerticalAnchor maxInclusive) {
        return new HeightRangePlacement(HeightProvider.uniform(minInclusive, maxInclusive));
    }

    @AsOf("2.3.0")
    static @NotNull PlacementModifier heightRangeTriangle(@NotNull VerticalAnchor minInclusive, @NotNull VerticalAnchor maxInclusive) {
        return new HeightRangePlacement(HeightProvider.trapezoid(minInclusive, maxInclusive, 0));
    }

    @AsOf("2.3.0")
    static @NotNull PlacementModifier fixedPlacement(@NotNull BlockVector... positions) {
        return new FixedPlacement(List.of(positions));
    }

    /**
     * Repeats placement a number of times sampled from the count provider.
     * @param count the count provider
     * @return a count placement modifier
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static @NotNull PlacementModifier count(@NotNull IntProvider count) {
        return new CountPlacement(count);
    }

    /**
     * Repeats placement a fixed number of times.
     * @param count the fixed count
     * @return a count placement modifier
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static @NotNull PlacementModifier count(int count) {
        return new CountPlacement(IntProvider.constant(count));
    }

    /**
     * Randomly offsets the position by the given spreads.
     * @param xzSpread the horizontal spread provider
     * @param ySpread the vertical spread provider
     * @return a random offset placement modifier
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static @NotNull PlacementModifier randomOffset(@NotNull IntProvider xzSpread, @NotNull IntProvider ySpread) {
        return new RandomOffsetPlacement(xzSpread, ySpread);
    }

    /**
     * Randomly offsets the position vertically only.
     * @param ySpread the vertical spread provider
     * @return a random offset placement modifier
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static @NotNull PlacementModifier randomOffsetVertical(@NotNull IntProvider ySpread) {
        return new RandomOffsetPlacement(IntProvider.constant(0), ySpread);
    }

    /**
     * Randomly offsets the position horizontally only.
     * @param xzSpread the horizontal spread provider
     * @return a random offset placement modifier
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static @NotNull PlacementModifier randomOffsetHorizontal(@NotNull IntProvider xzSpread) {
        return new RandomOffsetPlacement(xzSpread, IntProvider.constant(0));
    }

    /**
     * Randomly offsets the position using triangular spreads over the given ranges.
     * @param xzRange the horizontal range
     * @param yRange the vertical range
     * @return a random offset placement modifier
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static @NotNull PlacementModifier randomOffsetTriangle(int xzRange, int yRange) {
        return new RandomOffsetPlacement(IntProvider.triangle(xzRange), IntProvider.triangle(yRange));
    }

    /**
     * Keeps positions that satisfy the given block predicate.
     * @param predicate the predicate to test
     * @return a block predicate filter modifier
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static @NotNull PlacementModifier blockPredicateFilter(@NotNull BlockPredicate predicate) {
        return new BlockPredicateFilter(predicate);
    }

    /**
     * Scans vertically for a target condition, allowing the default search condition.
     * @param directionOfSearch the vertical search direction (UP or DOWN)
     * @param targetCondition the condition the scan is looking for
     * @param maxSteps the maximum number of steps, in [1, 32]
     * @return an environment scan placement modifier
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static @NotNull PlacementModifier environmentScan(@NotNull BlockFace directionOfSearch, @NotNull BlockPredicate targetCondition, int maxSteps) {
        return new EnvironmentScanPlacement(directionOfSearch, targetCondition, BlockPredicate.alwaysTrue(), maxSteps);
    }

    /**
     * Scans vertically for a target condition while an allowed search condition holds.
     * @param directionOfSearch the vertical search direction (UP or DOWN)
     * @param targetCondition the condition the scan is looking for
     * @param allowedSearchCondition the condition that must hold to keep scanning
     * @param maxSteps the maximum number of steps, in [1, 32]
     * @return an environment scan placement modifier
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static @NotNull PlacementModifier environmentScan(@NotNull BlockFace directionOfSearch, @NotNull BlockPredicate targetCondition, @NotNull BlockPredicate allowedSearchCondition, int maxSteps) {
        return new EnvironmentScanPlacement(directionOfSearch, targetCondition, allowedSearchCondition, maxSteps);
    }

    @Override
    @AsOf("2.3.0")
    default @NotNull Object toMinecraft() {
        return WIRE.get().toNms(this);
    }

    @AsOf("2.3.0")
    record BiomeFilter() implements PlacementModifier {}

    @AsOf("2.3.0")
    record BlockPredicateFilter(@NotNull BlockPredicate predicate) implements PlacementModifier {

        @AsOf("2.3.0")
        public BlockPredicateFilter {
            Objects.requireNonNull(predicate, "predicate");
        }
    }

    @AsOf("2.3.0")
    record CountPlacement(@NotNull IntProvider count) implements PlacementModifier {

        @AsOf("2.3.0")
        public CountPlacement {
            Objects.requireNonNull(count, "count");
        }
    }

    @AsOf("2.3.0")
    record EnvironmentScanPlacement(
        @NotNull BlockFace directionOfSearch,
        @NotNull BlockPredicate targetCondition,
        @NotNull BlockPredicate allowedSearchCondition,
        int maxSteps
    ) implements PlacementModifier {

        @AsOf("2.3.0")
        public EnvironmentScanPlacement {
            Objects.requireNonNull(directionOfSearch, "directionOfSearch");
            Objects.requireNonNull(targetCondition, "targetCondition");
            Objects.requireNonNull(allowedSearchCondition, "allowedSearchCondition");
            Preconditions.checkArgument(
                directionOfSearch == BlockFace.UP || directionOfSearch == BlockFace.DOWN,
                "directionOfSearch must be vertical (UP or DOWN)"
            );
            Preconditions.checkArgument(maxSteps >= 1 && maxSteps <= 32, "maxSteps must be within [1, 32]");
        }
    }

    @AsOf("2.3.0")
    record FixedPlacement(@NotNull List<BlockVector> positions) implements PlacementModifier {

        @AsOf("2.3.0")
        public FixedPlacement {
            positions = List.copyOf(positions);
        }
    }

    @AsOf("2.3.0")
    record HeightRangePlacement(@NotNull HeightProvider height) implements PlacementModifier {

        @AsOf("2.3.0")
        public HeightRangePlacement {
            Objects.requireNonNull(height, "height");
        }
    }

    @AsOf("2.3.0")
    record HeightmapPlacement(@NotNull HeightmapType heightmap) implements PlacementModifier {

        @AsOf("2.3.0")
        public HeightmapPlacement {
            Objects.requireNonNull(heightmap, "heightmap");
        }
    }

    @AsOf("2.3.0")
    record InSquarePlacement() implements PlacementModifier {}

    @AsOf("2.3.0")
    record NoiseBasedCountPlacement(int noiseToCountRatio, double noiseFactor, double noiseOffset) implements PlacementModifier {}

    @AsOf("2.3.0")
    record NoiseThresholdCountPlacement(double noiseLevel, int belowNoise, int aboveNoise) implements PlacementModifier {}

    @AsOf("2.3.0")
    record RandomOffsetPlacement(@NotNull IntProvider xzSpread, @NotNull IntProvider ySpread) implements PlacementModifier {

        @AsOf("2.3.0")
        public RandomOffsetPlacement {
            Objects.requireNonNull(xzSpread, "xzSpread");
            Objects.requireNonNull(ySpread, "ySpread");
        }
    }

    @AsOf("2.3.0")
    record RarityFilter(int chance) implements PlacementModifier {

        @AsOf("2.3.0")
        public RarityFilter {
            Preconditions.checkArgument(chance > 0, "chance must be positive");
        }
    }

    @AsOf("2.3.0")
    record SurfaceRelativeThresholdFilter(@NotNull HeightmapType heightmap, int minInclusive, int maxInclusive) implements PlacementModifier {

        @AsOf("2.3.0")
        public SurfaceRelativeThresholdFilter {
            Objects.requireNonNull(heightmap, "heightmap");
        }
    }

    @AsOf("2.3.0")
    record SurfaceWaterDepthFilter(int maxWaterDepth) implements PlacementModifier {}
}