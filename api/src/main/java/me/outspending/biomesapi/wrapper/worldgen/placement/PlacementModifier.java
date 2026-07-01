package me.outspending.biomesapi.wrapper.worldgen.placement;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.serialization.Codecs;
import me.outspending.biomesapi.serialization.StringRepresentable;
import me.outspending.biomesapi.wrapper.internal.NmsDecoder;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import me.outspending.biomesapi.wrapper.worldgen.BlockPredicate;
import me.outspending.biomesapi.wrapper.worldgen.HeightmapType;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.HeightProvider;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.IntProvider;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.VerticalAnchor;
import org.bukkit.block.BlockFace;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.Map;

/**
 * Wraps the PlacementModifier family, the ordered transforms applied to
 * a feature's candidate positions during placement.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public sealed interface PlacementModifier extends NmsHandle, StringRepresentable permits PlacementModifier.BiomeFilter, PlacementModifier.BlockPredicateFilter, PlacementModifier.CountPlacement, PlacementModifier.EnvironmentScanPlacement, PlacementModifier.FixedPlacement, PlacementModifier.HeightRangePlacement, PlacementModifier.HeightmapPlacement, PlacementModifier.InSquarePlacement, PlacementModifier.NoiseBasedCountPlacement, PlacementModifier.NoiseThresholdCountPlacement, PlacementModifier.RandomOffsetPlacement, PlacementModifier.RarityFilter, PlacementModifier.SurfaceRelativeThresholdFilter, PlacementModifier.SurfaceWaterDepthFilter {

    Codec<PlacementModifier> CODEC = Codec.lazyInitialized(() -> {
        Map<String, MapCodec<? extends PlacementModifier>> byType = Map.ofEntries(
            Map.entry("biome_filter", BiomeFilter.MAP_CODEC),
            Map.entry("block_predicate_filter", BlockPredicateFilter.MAP_CODEC),
            Map.entry("count_placement", CountPlacement.MAP_CODEC),
            Map.entry("environment_scan_placement", EnvironmentScanPlacement.MAP_CODEC),
            Map.entry("fixed_placement", FixedPlacement.MAP_CODEC),
            Map.entry("height_range_placement", HeightRangePlacement.MAP_CODEC),
            Map.entry("heightmap_placement", HeightmapPlacement.MAP_CODEC),
            Map.entry("in_square_placement", InSquarePlacement.MAP_CODEC),
            Map.entry("noise_based_count_placement", NoiseBasedCountPlacement.MAP_CODEC),
            Map.entry("noise_threshold_count_placement", NoiseThresholdCountPlacement.MAP_CODEC),
            Map.entry("random_offset_placement", RandomOffsetPlacement.MAP_CODEC),
            Map.entry("rarity_filter", RarityFilter.MAP_CODEC),
            Map.entry("surface_relative_threshold_filter", SurfaceRelativeThresholdFilter.MAP_CODEC),
            Map.entry("surface_water_depth_filter", SurfaceWaterDepthFilter.MAP_CODEC)
        );
        return Codec.STRING.dispatch("type", modifier -> {
            String key = modifier.type();
            if (!byType.containsKey(key)) {
                throw new IllegalStateException("PlacementModifier " + modifier.getClass().getSimpleName()
                    + " type()=\"" + key + "\" has no entry in dispatch map " + byType.keySet());
            }
            return key;
        }, byType::get);
    });

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.worldgen.placement.PlacementModifierFactoryImpl");

    @ApiStatus.Internal
    interface Factory extends NmsDecoder<PlacementModifier> {
        Object toNms(PlacementModifier modifier);
    }

    /**
     * Creates a placement modifier that filters positions based on the biome.
     *
     * @return a biome filter placement modifier
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static PlacementModifier biomeFilter() {
        return new BiomeFilter();
    }

    /**
     * Creates a placement modifier that ensures placement occurs within a square grid.
     *
     * @return a placement modifier configured for square-based placement
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static PlacementModifier inSquare() {
        return new InSquarePlacement();
    }

    /**
     * Filters a position based on a specified chance value, allowing positions
     * to be kept with a probability of 1/chance.
     *
     * @param chance the probability denominator which determines how likely a position is to be kept and must be greater than 0
     * @return a placement modifier that applies the rarity filter
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static PlacementModifier rarityFilter(int chance) {
        return new RarityFilter(chance);
    }

    /**
     * Creates a placement modifier that filters positions based on their surface water depth.
     *
     * @param maxWaterDepth the maximum depth of water allowed on the surface for a position to pass the filter
     * @return a surface water depth placement modifier
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static PlacementModifier surfaceWaterDepth(int maxWaterDepth) {
        return new SurfaceWaterDepthFilter(maxWaterDepth);
    }

    /**
     * Creates a placement modifier that selects positions based on noise settings.
     *
     * @param noiseToCountRatio the ratio between noise value and count, influencing placement density
     * @param noiseFactor the multiplier applied to the noise value for scaling
     * @param noiseOffset the constant offset added to the noise value for adjustment
     * @return a placement modifier that applies noise-based distribution logic
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static PlacementModifier noiseBasedCount(int noiseToCountRatio, double noiseFactor, double noiseOffset) {
        return new NoiseBasedCountPlacement(noiseToCountRatio, noiseFactor, noiseOffset);
    }

    /**
     * Creates a placement modifier that adjusts placement based on a noise threshold. Positions are selected based on
     * whether the noise value at a position is above or below the specified threshold, determining the count of positions
     * to retain.
     *
     * @param noiseLevel the noise level threshold used to decide placement
     * @param belowNoise the number of placements to retain when the noise level is below the threshold
     * @param aboveNoise the number of placements to retain when the noise level is above the threshold
     * @return a placement modifier configured for noise-based threshold placement
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static PlacementModifier noiseThresholdCount(double noiseLevel, int belowNoise, int aboveNoise) {
        return new NoiseThresholdCountPlacement(noiseLevel, belowNoise, aboveNoise);
    }

    /**
     * Creates a placement modifier that filters positions based on a specified heightmap type.
     *
     * @param heightmap the type of heightmap to use for filtering positions
     * @return a placement modifier configured to use the specified heightmap type
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static PlacementModifier heightmap(HeightmapType heightmap) {
        return new HeightmapPlacement(heightmap);
    }

    /**
     * Creates a placement modifier that filters positions based on their relative surface height
     * using the specified heightmap type and a range of inclusive minimum and maximum height thresholds.
     * Positions are retained if they fall within the given range on the specified heightmap.
     *
     * @param heightmap the type of heightmap to use for filtering positions
     * @param minInclusive the inclusive minimum height threshold for filtering
     * @param maxInclusive the inclusive maximum height threshold for filtering
     * @return a placement modifier that applies the surface-relative height threshold filter
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static PlacementModifier surfaceRelativeThreshold(HeightmapType heightmap, int minInclusive, int maxInclusive) {
        return new SurfaceRelativeThresholdFilter(heightmap, minInclusive, maxInclusive);
    }

    /**
     * Creates a placement modifier that defines a range of vertical heights where placement is allowed.
     *
     * @param height the height provider that specifies the inclusive minimum and maximum vertical bounds
     * @return a placement modifier configured for height-based placement within the specified range
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static PlacementModifier heightRange(HeightProvider height) {
        return new HeightRangePlacement(height);
    }

    /**
     * Creates a PlacementModifier that defines a uniform height range for placement
     * between the specified minimum and maximum vertical anchors, inclusive.
     *
     * @param minInclusive the minimum vertical anchor for the placement range, inclusive
     * @param maxInclusive the maximum vertical anchor for the placement range, inclusive
     * @return a PlacementModifier that applies a uniform height placement between the given bounds
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static PlacementModifier heightRangeUniform(VerticalAnchor minInclusive, VerticalAnchor maxInclusive) {
        return new HeightRangePlacement(HeightProvider.uniform(minInclusive, maxInclusive));
    }

    /**
     * Creates a height range placement modifier using a triangular distribution between
     * the specified minimum and maximum vertical anchors. The distribution will favor
     * heights closer to midpoints within the range.
     *
     * @param minInclusive the minimum vertical anchor, inclusive
     * @param maxInclusive the maximum vertical anchor, inclusive
     * @return a PlacementModifier that represents a triangular height range placement
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static PlacementModifier heightRangeTriangle(VerticalAnchor minInclusive, VerticalAnchor maxInclusive) {
        return new HeightRangePlacement(HeightProvider.trapezoid(minInclusive, maxInclusive, 0));
    }

    /**
     * Creates a fixed placement modifier that defines positions where blocks
     * or features will be placed.
     *
     * @param positions an array of BlockVector objects representing the fixed positions for placement
     * @return a PlacementModifier that applies the fixed placement defined by the specified positions
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static PlacementModifier fixedPlacement(BlockVector... positions) {
        return new FixedPlacement(List.of(positions));
    }

    /**
     * Repeats placement a number of times sampled from the count provider.
     * @param count the count provider
     * @return a count placement modifier
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static PlacementModifier count(IntProvider count) {
        return new CountPlacement(count);
    }

    /**
     * Repeats placement a fixed number of times.
     * @param count the fixed count
     * @return a count placement modifier
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static PlacementModifier count(int count) {
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
    static PlacementModifier randomOffset(IntProvider xzSpread, IntProvider ySpread) {
        return new RandomOffsetPlacement(xzSpread, ySpread);
    }

    /**
     * Randomly offsets the position vertically only.
     * @param ySpread the vertical spread provider
     * @return a random offset placement modifier
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static PlacementModifier randomOffsetVertical(IntProvider ySpread) {
        return new RandomOffsetPlacement(IntProvider.constant(0), ySpread);
    }

    /**
     * Randomly offsets the position horizontally only.
     * @param xzSpread the horizontal spread provider
     * @return a random offset placement modifier
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static PlacementModifier randomOffsetHorizontal(IntProvider xzSpread) {
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
    static PlacementModifier randomOffsetTriangle(int xzRange, int yRange) {
        return new RandomOffsetPlacement(IntProvider.triangle(xzRange), IntProvider.triangle(yRange));
    }

    /**
     * Keeps positions that satisfy the given block predicate.
     * @param predicate the predicate to test
     * @return a block predicate filter modifier
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static PlacementModifier blockPredicateFilter(BlockPredicate predicate) {
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
    static PlacementModifier environmentScan(BlockFace directionOfSearch, BlockPredicate targetCondition, int maxSteps) {
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
    static PlacementModifier environmentScan(BlockFace directionOfSearch, BlockPredicate targetCondition, BlockPredicate allowedSearchCondition, int maxSteps) {
        return new EnvironmentScanPlacement(directionOfSearch, targetCondition, allowedSearchCondition, maxSteps);
    }

    @Override
    @AsOf("2.3.0")
    default Object toMinecraft() {
        return WIRE.get().toNms(this);
    }

    @AsOf("2.4.0")
    static PlacementModifier fromMinecraft(Object nms) {
        return WIRE.get().fromMinecraft(nms);
    }

    @AsOf("2.3.0")
    record BiomeFilter() implements PlacementModifier {
        public static final MapCodec<BiomeFilter> MAP_CODEC = MapCodec.unit(BiomeFilter::new);
    }

    @AsOf("2.3.0")
    record BlockPredicateFilter(BlockPredicate predicate) implements PlacementModifier {
        public static final MapCodec<BlockPredicateFilter> MAP_CODEC = BlockPredicate.CODEC.fieldOf("predicate")
            .xmap(BlockPredicateFilter::new, BlockPredicateFilter::predicate);

        @AsOf("2.3.0")
        public BlockPredicateFilter {
            Preconditions.checkNotNull(predicate, "predicate");
        }
    }

    @AsOf("2.3.0")
    record CountPlacement(IntProvider count) implements PlacementModifier {
        public static final MapCodec<CountPlacement> MAP_CODEC = IntProvider.CODEC.fieldOf("count")
            .xmap(CountPlacement::new, CountPlacement::count);

        @AsOf("2.3.0")
        public CountPlacement {
            Preconditions.checkNotNull(count, "count");
        }
    }

    @AsOf("2.3.0")
    record EnvironmentScanPlacement(
        BlockFace directionOfSearch,
        BlockPredicate targetCondition,
        BlockPredicate allowedSearchCondition,
        int maxSteps
    ) implements PlacementModifier {
        public static final MapCodec<EnvironmentScanPlacement> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codecs.BLOCK_FACE_CODEC.fieldOf("direction").forGetter(EnvironmentScanPlacement::directionOfSearch),
            BlockPredicate.CODEC.fieldOf("target_condition").forGetter(EnvironmentScanPlacement::targetCondition),
            BlockPredicate.CODEC.fieldOf("allowed_search_condition").forGetter(EnvironmentScanPlacement::allowedSearchCondition),
            Codec.INT.fieldOf("max_steps").forGetter(EnvironmentScanPlacement::maxSteps)
        ).apply(i, EnvironmentScanPlacement::new));

        @AsOf("2.3.0")
        public EnvironmentScanPlacement {
            Preconditions.checkNotNull(directionOfSearch, "directionOfSearch");
            Preconditions.checkNotNull(targetCondition, "targetCondition");
            Preconditions.checkNotNull(allowedSearchCondition, "allowedSearchCondition");
            Preconditions.checkArgument(
                directionOfSearch == BlockFace.UP || directionOfSearch == BlockFace.DOWN,
                "directionOfSearch must be vertical (UP or DOWN)"
            );
            Preconditions.checkArgument(maxSteps >= 1 && maxSteps <= 32, "maxSteps must be within [1, 32]");
        }
    }

    @AsOf("2.3.0")
    record FixedPlacement(List<BlockVector> positions) implements PlacementModifier {
        public static final MapCodec<FixedPlacement> MAP_CODEC = Codec.list(Codecs.BLOCK_VECTOR_CODEC).fieldOf("positions")
            .xmap(FixedPlacement::new, FixedPlacement::positions);

        @AsOf("2.3.0")
        public FixedPlacement {
            positions = List.copyOf(positions);
        }
    }

    @AsOf("2.3.0")
    record HeightRangePlacement(HeightProvider height) implements PlacementModifier {
        public static final MapCodec<HeightRangePlacement> MAP_CODEC = HeightProvider.CODEC.fieldOf("height")
            .xmap(HeightRangePlacement::new, HeightRangePlacement::height);

        @AsOf("2.3.0")
        public HeightRangePlacement {
            Preconditions.checkNotNull(height, "height");
        }
    }

    @AsOf("2.3.0")
    record HeightmapPlacement(HeightmapType heightmap) implements PlacementModifier {
        public static final MapCodec<HeightmapPlacement> MAP_CODEC = HeightmapType.CODEC.fieldOf("heightmap")
            .xmap(HeightmapPlacement::new, HeightmapPlacement::heightmap);

        @AsOf("2.3.0")
        public HeightmapPlacement {
            Preconditions.checkNotNull(heightmap, "heightmap");
        }
    }

    @AsOf("2.3.0")
    record InSquarePlacement() implements PlacementModifier {
        public static final MapCodec<InSquarePlacement> MAP_CODEC = MapCodec.unit(InSquarePlacement::new);
    }

    @AsOf("2.3.0")
    record NoiseBasedCountPlacement(int noiseToCountRatio, double noiseFactor, double noiseOffset) implements PlacementModifier {
        public static final MapCodec<NoiseBasedCountPlacement> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codec.INT.fieldOf("noise_to_count_ratio").forGetter(NoiseBasedCountPlacement::noiseToCountRatio),
            Codec.DOUBLE.fieldOf("noise_factor").forGetter(NoiseBasedCountPlacement::noiseFactor),
            Codec.DOUBLE.fieldOf("noise_offset").forGetter(NoiseBasedCountPlacement::noiseOffset)
        ).apply(i, NoiseBasedCountPlacement::new));
    }

    @AsOf("2.3.0")
    record NoiseThresholdCountPlacement(double noiseLevel, int belowNoise, int aboveNoise) implements PlacementModifier {
        public static final MapCodec<NoiseThresholdCountPlacement> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codec.DOUBLE.fieldOf("noise_level").forGetter(NoiseThresholdCountPlacement::noiseLevel),
            Codec.INT.fieldOf("below_noise").forGetter(NoiseThresholdCountPlacement::belowNoise),
            Codec.INT.fieldOf("above_noise").forGetter(NoiseThresholdCountPlacement::aboveNoise)
        ).apply(i, NoiseThresholdCountPlacement::new));
    }

    @AsOf("2.3.0")
    record RandomOffsetPlacement(IntProvider xzSpread, IntProvider ySpread) implements PlacementModifier {
        public static final MapCodec<RandomOffsetPlacement> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            IntProvider.CODEC.fieldOf("xz_spread").forGetter(RandomOffsetPlacement::xzSpread),
            IntProvider.CODEC.fieldOf("y_spread").forGetter(RandomOffsetPlacement::ySpread)
        ).apply(i, RandomOffsetPlacement::new));

        @AsOf("2.3.0")
        public RandomOffsetPlacement {
            Preconditions.checkNotNull(xzSpread, "xzSpread");
            Preconditions.checkNotNull(ySpread, "ySpread");
        }
    }

    @AsOf("2.3.0")
    record RarityFilter(int chance) implements PlacementModifier {
        public static final MapCodec<RarityFilter> MAP_CODEC = Codec.INT.fieldOf("chance")
            .xmap(RarityFilter::new, RarityFilter::chance);

        @AsOf("2.3.0")
        public RarityFilter {
            Preconditions.checkArgument(chance > 0, "chance must be positive");
        }
    }

    @AsOf("2.3.0")
    record SurfaceRelativeThresholdFilter(HeightmapType heightmap, int minInclusive, int maxInclusive) implements PlacementModifier {
        public static final MapCodec<SurfaceRelativeThresholdFilter> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            HeightmapType.CODEC.fieldOf("heightmap").forGetter(SurfaceRelativeThresholdFilter::heightmap),
            Codec.INT.fieldOf("min_inclusive").forGetter(SurfaceRelativeThresholdFilter::minInclusive),
            Codec.INT.fieldOf("max_inclusive").forGetter(SurfaceRelativeThresholdFilter::maxInclusive)
        ).apply(i, SurfaceRelativeThresholdFilter::new));

        @AsOf("2.3.0")
        public SurfaceRelativeThresholdFilter {
            Preconditions.checkNotNull(heightmap, "heightmap");
        }
    }

    @AsOf("2.3.0")
    record SurfaceWaterDepthFilter(int maxWaterDepth) implements PlacementModifier {
        public static final MapCodec<SurfaceWaterDepthFilter> MAP_CODEC = Codec.INT.fieldOf("max_water_depth")
            .xmap(SurfaceWaterDepthFilter::new, SurfaceWaterDepthFilter::maxWaterDepth);
    }
}