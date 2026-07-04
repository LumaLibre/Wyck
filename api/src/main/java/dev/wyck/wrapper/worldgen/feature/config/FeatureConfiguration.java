package dev.wyck.wrapper.worldgen.feature.config;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.WireProvider;
import dev.wyck.wrapper.internal.Wrapper;
import dev.wyck.wrapper.worldgen.valueproviders.FloatProvider;
import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Wraps Minecraft's FeatureConfiguration.
 *
 * @since 2.3.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public interface FeatureConfiguration extends Wrapper {

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("dev.wyck.wrapper.worldgen.feature.config.FeatureConfigurationFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        Object toNms(FeatureConfiguration configuration);
    }

    @Override
    @AsOf("3.0.0")
    default Object toMinecraft() {
        return WIRE.get().toNms(this);
    }

    /**
     * The empty configuration for feature types that take no parameters.
     * @return the none configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static NoneConfiguration none() {
        return NoneConfiguration.INSTANCE;
    }

    /**
     * A single placement probability, in the range [0, 1].
     * @param probability the placement probability
     * @return the configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ProbabilityConfiguration probability(float probability) {
        return ProbabilityConfiguration.of(probability);
    }

    /**
     * A builder for pointed dripstone spread chances.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static PointedDripstoneConfiguration.Builder pointedDripstone() {
        return PointedDripstoneConfiguration.builder();
    }

    /**
     * A number of placement attempts, constrained by the vanilla codec to [0, 256].
     * @param count the count provider
     * @return the configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static CountConfiguration count(IntProvider count) {
        return CountConfiguration.of(count);
    }

    /**
     * A column feature, with reach constrained to [0, 3] and height to [1, 10].
     * @param reach the horizontal reach provider
     * @param height the column height provider
     * @return the configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ColumnConfiguration column(IntProvider reach, IntProvider height) {
        return ColumnConfiguration.of(reach, height);
    }

    /**
     * A builder for a sculk patch.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SculkPatchConfiguration.Builder sculkPatch() {
        return SculkPatchConfiguration.builder();
    }

    /**
     * A builder for a dripstone cluster.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static DripstoneClusterConfiguration.Builder dripstoneCluster() {
        return DripstoneClusterConfiguration.builder();
    }

    /**
     * A builder for a large dripstone.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static LargeDripstoneConfiguration.Builder largeDripstone() {
        return LargeDripstoneConfiguration.builder();
    }

    /**
     * The empty configuration used by feature types that take no parameters.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    record NoneConfiguration() implements FeatureConfiguration {
        public static final NoneConfiguration INSTANCE = new NoneConfiguration();
    }

    /**
     * A single placement probability, in the range [0, 1].
     * @param probability the placement probability
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    record ProbabilityConfiguration(float probability) implements FeatureConfiguration {

        @AsOf("3.0.0")
        public ProbabilityConfiguration {
            Preconditions.checkArgument(probability >= 0.0F && probability <= 1.0F,
                "probability must be in [0, 1], was %s", probability);
        }

        @AsOf("3.0.0")
        public static ProbabilityConfiguration of(float probability) {
            return new ProbabilityConfiguration(probability);
        }
    }

    /**
     * Pointed dripstone spread chances.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    record PointedDripstoneConfiguration(
        float chanceOfTallerDripstone,
        float chanceOfDirectionalSpread,
        float chanceOfSpreadRadius2,
        float chanceOfSpreadRadius3
    ) implements FeatureConfiguration {

        public static final PointedDripstoneConfiguration DEFAULT = new PointedDripstoneConfiguration(0.2F, 0.7F, 0.5F, 0.5F);

        @AsOf("3.0.0")
        public PointedDripstoneConfiguration {
            Preconditions.checkArgument(chanceOfTallerDripstone >= 0.0F && chanceOfTallerDripstone <= 1.0F,
                "chanceOfTallerDripstone must be in [0, 1], was %s", chanceOfTallerDripstone);
            Preconditions.checkArgument(chanceOfDirectionalSpread >= 0.0F && chanceOfDirectionalSpread <= 1.0F,
                "chanceOfDirectionalSpread must be in [0, 1], was %s", chanceOfDirectionalSpread);
            Preconditions.checkArgument(chanceOfSpreadRadius2 >= 0.0F && chanceOfSpreadRadius2 <= 1.0F,
                "chanceOfSpreadRadius2 must be in [0, 1], was %s", chanceOfSpreadRadius2);
            Preconditions.checkArgument(chanceOfSpreadRadius3 >= 0.0F && chanceOfSpreadRadius3 <= 1.0F,
                "chanceOfSpreadRadius3 must be in [0, 1], was %s", chanceOfSpreadRadius3);
        }

        @AsOf("3.0.0")
        public Builder toBuilder() {
            return new Builder(this);
        }

        @AsOf("3.0.0")
        public static Builder builder() {
            return new Builder();
        }

        @NullMarked
        @AsOf("3.0.0")
        public static final class Builder {
            private float chanceOfTallerDripstone = 0.2F;
            private float chanceOfDirectionalSpread = 0.7F;
            private float chanceOfSpreadRadius2 = 0.5F;
            private float chanceOfSpreadRadius3 = 0.5F;

            private Builder() {}

            @AsOf("3.0.0")
            public Builder(PointedDripstoneConfiguration configuration) {
                this.chanceOfTallerDripstone = configuration.chanceOfTallerDripstone();
                this.chanceOfDirectionalSpread = configuration.chanceOfDirectionalSpread();
                this.chanceOfSpreadRadius2 = configuration.chanceOfSpreadRadius2();
                this.chanceOfSpreadRadius3 = configuration.chanceOfSpreadRadius3();
            }

            @AsOf("3.0.0")
            public Builder chanceOfTallerDripstone(float chance) {
                this.chanceOfTallerDripstone = chance;
                return this;
            }

            @AsOf("3.0.0")
            public Builder chanceOfDirectionalSpread(float chance) {
                this.chanceOfDirectionalSpread = chance;
                return this;
            }

            @AsOf("3.0.0")
            public Builder chanceOfSpreadRadius2(float chance) {
                this.chanceOfSpreadRadius2 = chance;
                return this;
            }

            @AsOf("3.0.0")
            public Builder chanceOfSpreadRadius3(float chance) {
                this.chanceOfSpreadRadius3 = chance;
                return this;
            }

            @AsOf("3.0.0")
            public PointedDripstoneConfiguration build() {
                return new PointedDripstoneConfiguration(
                    this.chanceOfTallerDripstone,
                    this.chanceOfDirectionalSpread,
                    this.chanceOfSpreadRadius2,
                    this.chanceOfSpreadRadius3
                );
            }
        }
    }

    /**
     * A number of placement attempts. The vanilla codec constrains this to [0, 256].
     * @param count the count provider
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    record CountConfiguration(IntProvider count) implements FeatureConfiguration {

        @AsOf("3.0.0")
        public CountConfiguration {
            Preconditions.checkNotNull(count, "count");
        }

        @AsOf("3.0.0")
        public static CountConfiguration of(IntProvider count) {
            return new CountConfiguration(count);
        }
    }

    /**
     * A column feature. The vanilla codec constrains reach to [0, 3] and height to [1, 10].
     * @param reach the horizontal reach provider
     * @param height the column height provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    record ColumnConfiguration(IntProvider reach, IntProvider height) implements FeatureConfiguration {

        @AsOf("3.0.0")
        public ColumnConfiguration {
            Preconditions.checkNotNull(reach, "reach");
            Preconditions.checkNotNull(height, "height");
        }

        @AsOf("3.0.0")
        public static ColumnConfiguration of(IntProvider reach, IntProvider height) {
            return new ColumnConfiguration(reach, height);
        }
    }

    /**
     * A sculk patch.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    record SculkPatchConfiguration(
        int chargeCount,
        int amountPerCharge,
        int spreadAttempts,
        int growthRounds,
        int spreadRounds,
        IntProvider extraRareGrowths,
        float catalystChance
    ) implements FeatureConfiguration {

        @AsOf("3.0.0")
        public SculkPatchConfiguration {
            Preconditions.checkArgument(chargeCount >= 1 && chargeCount <= 32,
                "chargeCount must be in [1, 32], was %s", chargeCount);
            Preconditions.checkArgument(amountPerCharge >= 1 && amountPerCharge <= 500,
                "amountPerCharge must be in [1, 500], was %s", amountPerCharge);
            Preconditions.checkArgument(spreadAttempts >= 1 && spreadAttempts <= 64,
                "spreadAttempts must be in [1, 64], was %s", spreadAttempts);
            Preconditions.checkArgument(growthRounds >= 0 && growthRounds <= 8,
                "growthRounds must be in [0, 8], was %s", growthRounds);
            Preconditions.checkArgument(spreadRounds >= 0 && spreadRounds <= 8,
                "spreadRounds must be in [0, 8], was %s", spreadRounds);
            Preconditions.checkNotNull(extraRareGrowths, "extraRareGrowths");
            Preconditions.checkArgument(catalystChance >= 0.0F && catalystChance <= 1.0F,
                "catalystChance must be in [0, 1], was %s", catalystChance);
        }

        @AsOf("3.0.0")
        public static Builder builder() {
            return new Builder();
        }

        @NullMarked
        @AsOf("3.0.0")
        public static final class Builder {
            private int chargeCount = 1;
            private int amountPerCharge = 1;
            private int spreadAttempts = 1;
            private int growthRounds = 0;
            private int spreadRounds = 0;
            private @Nullable IntProvider extraRareGrowths;
            private float catalystChance = 0.05F;

            private Builder() {}

            @AsOf("3.0.0")
            public Builder chargeCount(int chargeCount) {
                this.chargeCount = chargeCount;
                return this;
            }

            @AsOf("3.0.0")
            public Builder amountPerCharge(int amountPerCharge) {
                this.amountPerCharge = amountPerCharge;
                return this;
            }

            @AsOf("3.0.0")
            public Builder spreadAttempts(int spreadAttempts) {
                this.spreadAttempts = spreadAttempts;
                return this;
            }

            @AsOf("3.0.0")
            public Builder growthRounds(int growthRounds) {
                this.growthRounds = growthRounds;
                return this;
            }

            @AsOf("3.0.0")
            public Builder spreadRounds(int spreadRounds) {
                this.spreadRounds = spreadRounds;
                return this;
            }

            @AsOf("3.0.0")
            public Builder extraRareGrowths(IntProvider extraRareGrowths) {
                this.extraRareGrowths = extraRareGrowths;
                return this;
            }

            @AsOf("3.0.0")
            public Builder catalystChance(float catalystChance) {
                this.catalystChance = catalystChance;
                return this;
            }

            @AsOf("3.0.0")
            public SculkPatchConfiguration build() {
                return new SculkPatchConfiguration(
                    this.chargeCount,
                    this.amountPerCharge,
                    this.spreadAttempts,
                    this.growthRounds,
                    this.spreadRounds,
                    Preconditions.checkNotNull(this.extraRareGrowths, "extraRareGrowths"),
                    this.catalystChance
                );
            }
        }
    }

    /**
     * A dripstone cluster.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    record DripstoneClusterConfiguration(
        int floorToCeilingSearchRange,
        IntProvider height,
        IntProvider radius,
        int maxStalagmiteStalactiteHeightDiff,
        int heightDeviation,
        IntProvider dripstoneBlockLayerThickness,
        FloatProvider density,
        FloatProvider wetness,
        float chanceOfDripstoneColumnAtMaxDistanceFromCenter,
        int maxDistanceFromEdgeAffectingChanceOfDripstoneColumn,
        int maxDistanceFromCenterAffectingHeightBias
    ) implements FeatureConfiguration {

        @AsOf("3.0.0")
        public DripstoneClusterConfiguration {
            Preconditions.checkArgument(floorToCeilingSearchRange >= 1 && floorToCeilingSearchRange <= 512,
                "floorToCeilingSearchRange must be in [1, 512], was %s", floorToCeilingSearchRange);
            Preconditions.checkNotNull(height, "height");
            Preconditions.checkNotNull(radius, "radius");
            Preconditions.checkArgument(maxStalagmiteStalactiteHeightDiff >= 0 && maxStalagmiteStalactiteHeightDiff <= 64,
                "maxStalagmiteStalactiteHeightDiff must be in [0, 64], was %s", maxStalagmiteStalactiteHeightDiff);
            Preconditions.checkArgument(heightDeviation >= 1 && heightDeviation <= 64,
                "heightDeviation must be in [1, 64], was %s", heightDeviation);
            Preconditions.checkNotNull(dripstoneBlockLayerThickness, "dripstoneBlockLayerThickness");
            Preconditions.checkNotNull(density, "density");
            Preconditions.checkNotNull(wetness, "wetness");
            Preconditions.checkArgument(chanceOfDripstoneColumnAtMaxDistanceFromCenter >= 0.0F && chanceOfDripstoneColumnAtMaxDistanceFromCenter <= 1.0F,
                "chanceOfDripstoneColumnAtMaxDistanceFromCenter must be in [0, 1], was %s", chanceOfDripstoneColumnAtMaxDistanceFromCenter);
            Preconditions.checkArgument(maxDistanceFromEdgeAffectingChanceOfDripstoneColumn >= 1 && maxDistanceFromEdgeAffectingChanceOfDripstoneColumn <= 64,
                "maxDistanceFromEdgeAffectingChanceOfDripstoneColumn must be in [1, 64], was %s", maxDistanceFromEdgeAffectingChanceOfDripstoneColumn);
            Preconditions.checkArgument(maxDistanceFromCenterAffectingHeightBias >= 1 && maxDistanceFromCenterAffectingHeightBias <= 64,
                "maxDistanceFromCenterAffectingHeightBias must be in [1, 64], was %s", maxDistanceFromCenterAffectingHeightBias);
        }

        @AsOf("3.0.0")
        public static Builder builder() {
            return new Builder();
        }

        @NullMarked
        @AsOf("3.0.0")
        public static final class Builder {
            private @Nullable Integer floorToCeilingSearchRange;
            private @Nullable IntProvider height;
            private @Nullable IntProvider radius;
            private @Nullable Integer maxStalagmiteStalactiteHeightDiff;
            private @Nullable Integer heightDeviation;
            private @Nullable IntProvider dripstoneBlockLayerThickness;
            private @Nullable FloatProvider density;
            private @Nullable FloatProvider wetness;
            private @Nullable Float chanceOfDripstoneColumnAtMaxDistanceFromCenter;
            private @Nullable Integer maxDistanceFromEdgeAffectingChanceOfDripstoneColumn;
            private @Nullable Integer maxDistanceFromCenterAffectingHeightBias;

            private Builder() {}

            @AsOf("3.0.0")
            public Builder floorToCeilingSearchRange(int floorToCeilingSearchRange) {
                this.floorToCeilingSearchRange = floorToCeilingSearchRange;
                return this;
            }

            @AsOf("3.0.0")
            public Builder height(IntProvider height) {
                this.height = height;
                return this;
            }

            @AsOf("3.0.0")
            public Builder radius(IntProvider radius) {
                this.radius = radius;
                return this;
            }

            @AsOf("3.0.0")
            public Builder maxStalagmiteStalactiteHeightDiff(int maxStalagmiteStalactiteHeightDiff) {
                this.maxStalagmiteStalactiteHeightDiff = maxStalagmiteStalactiteHeightDiff;
                return this;
            }

            @AsOf("3.0.0")
            public Builder heightDeviation(int heightDeviation) {
                this.heightDeviation = heightDeviation;
                return this;
            }

            @AsOf("3.0.0")
            public Builder dripstoneBlockLayerThickness(IntProvider dripstoneBlockLayerThickness) {
                this.dripstoneBlockLayerThickness = dripstoneBlockLayerThickness;
                return this;
            }

            @AsOf("3.0.0")
            public Builder density(FloatProvider density) {
                this.density = density;
                return this;
            }

            @AsOf("3.0.0")
            public Builder wetness(FloatProvider wetness) {
                this.wetness = wetness;
                return this;
            }

            @AsOf("3.0.0")
            public Builder chanceOfDripstoneColumnAtMaxDistanceFromCenter(float chanceOfDripstoneColumnAtMaxDistanceFromCenter) {
                this.chanceOfDripstoneColumnAtMaxDistanceFromCenter = chanceOfDripstoneColumnAtMaxDistanceFromCenter;
                return this;
            }

            @AsOf("3.0.0")
            public Builder maxDistanceFromEdgeAffectingChanceOfDripstoneColumn(int maxDistanceFromEdgeAffectingChanceOfDripstoneColumn) {
                this.maxDistanceFromEdgeAffectingChanceOfDripstoneColumn = maxDistanceFromEdgeAffectingChanceOfDripstoneColumn;
                return this;
            }

            @AsOf("3.0.0")
            public Builder maxDistanceFromCenterAffectingHeightBias(int maxDistanceFromCenterAffectingHeightBias) {
                this.maxDistanceFromCenterAffectingHeightBias = maxDistanceFromCenterAffectingHeightBias;
                return this;
            }

            @AsOf("3.0.0")
            public DripstoneClusterConfiguration build() {
                return new DripstoneClusterConfiguration(
                    Preconditions.checkNotNull(this.floorToCeilingSearchRange, "floorToCeilingSearchRange"),
                    Preconditions.checkNotNull(this.height, "height"),
                    Preconditions.checkNotNull(this.radius, "radius"),
                    Preconditions.checkNotNull(this.maxStalagmiteStalactiteHeightDiff, "maxStalagmiteStalactiteHeightDiff"),
                    Preconditions.checkNotNull(this.heightDeviation, "heightDeviation"),
                    Preconditions.checkNotNull(this.dripstoneBlockLayerThickness, "dripstoneBlockLayerThickness"),
                    Preconditions.checkNotNull(this.density, "density"),
                    Preconditions.checkNotNull(this.wetness, "wetness"),
                    Preconditions.checkNotNull(this.chanceOfDripstoneColumnAtMaxDistanceFromCenter, "chanceOfDripstoneColumnAtMaxDistanceFromCenter"),
                    Preconditions.checkNotNull(this.maxDistanceFromEdgeAffectingChanceOfDripstoneColumn, "maxDistanceFromEdgeAffectingChanceOfDripstoneColumn"),
                    Preconditions.checkNotNull(this.maxDistanceFromCenterAffectingHeightBias, "maxDistanceFromCenterAffectingHeightBias")
                );
            }
        }
    }

    /**
     * A large dripstone.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    record LargeDripstoneConfiguration(
        int floorToCeilingSearchRange,
        IntProvider columnRadius,
        FloatProvider heightScale,
        float maxColumnRadiusToCaveHeightRatio,
        FloatProvider stalactiteBluntness,
        FloatProvider stalagmiteBluntness,
        FloatProvider windSpeed,
        int minRadiusForWind,
        float minBluntnessForWind
    ) implements FeatureConfiguration {

        @AsOf("3.0.0")
        public LargeDripstoneConfiguration {
            Preconditions.checkArgument(floorToCeilingSearchRange >= 1 && floorToCeilingSearchRange <= 512,
                "floorToCeilingSearchRange must be in [1, 512], was %s", floorToCeilingSearchRange);
            Preconditions.checkNotNull(columnRadius, "columnRadius");
            Preconditions.checkNotNull(heightScale, "heightScale");
            Preconditions.checkArgument(maxColumnRadiusToCaveHeightRatio >= 0.1F && maxColumnRadiusToCaveHeightRatio <= 1.0F,
                "maxColumnRadiusToCaveHeightRatio must be in [0.1, 1], was %s", maxColumnRadiusToCaveHeightRatio);
            Preconditions.checkNotNull(stalactiteBluntness, "stalactiteBluntness");
            Preconditions.checkNotNull(stalagmiteBluntness, "stalagmiteBluntness");
            Preconditions.checkNotNull(windSpeed, "windSpeed");
            Preconditions.checkArgument(minRadiusForWind >= 0 && minRadiusForWind <= 100,
                "minRadiusForWind must be in [0, 100], was %s", minRadiusForWind);
            Preconditions.checkArgument(minBluntnessForWind >= 0.0F && minBluntnessForWind <= 5.0F,
                "minBluntnessForWind must be in [0, 5], was %s", minBluntnessForWind);
        }

        @AsOf("3.0.0")
        public static Builder builder() {
            return new Builder();
        }

        @NullMarked
        @AsOf("3.0.0")
        public static final class Builder {
            private int floorToCeilingSearchRange = 30;
            private @Nullable IntProvider columnRadius;
            private @Nullable FloatProvider heightScale;
            private @Nullable Float maxColumnRadiusToCaveHeightRatio;
            private @Nullable FloatProvider stalactiteBluntness;
            private @Nullable FloatProvider stalagmiteBluntness;
            private @Nullable FloatProvider windSpeed;
            private @Nullable Integer minRadiusForWind;
            private @Nullable Float minBluntnessForWind;

            private Builder() {}

            @AsOf("3.0.0")
            public Builder floorToCeilingSearchRange(int floorToCeilingSearchRange) {
                this.floorToCeilingSearchRange = floorToCeilingSearchRange;
                return this;
            }

            @AsOf("3.0.0")
            public Builder columnRadius(IntProvider columnRadius) {
                this.columnRadius = columnRadius;
                return this;
            }

            @AsOf("3.0.0")
            public Builder heightScale(FloatProvider heightScale) {
                this.heightScale = heightScale;
                return this;
            }

            @AsOf("3.0.0")
            public Builder maxColumnRadiusToCaveHeightRatio(float maxColumnRadiusToCaveHeightRatio) {
                this.maxColumnRadiusToCaveHeightRatio = maxColumnRadiusToCaveHeightRatio;
                return this;
            }

            @AsOf("3.0.0")
            public Builder stalactiteBluntness(FloatProvider stalactiteBluntness) {
                this.stalactiteBluntness = stalactiteBluntness;
                return this;
            }

            @AsOf("3.0.0")
            public Builder stalagmiteBluntness(FloatProvider stalagmiteBluntness) {
                this.stalagmiteBluntness = stalagmiteBluntness;
                return this;
            }

            @AsOf("3.0.0")
            public Builder windSpeed(FloatProvider windSpeed) {
                this.windSpeed = windSpeed;
                return this;
            }

            @AsOf("3.0.0")
            public Builder minRadiusForWind(int minRadiusForWind) {
                this.minRadiusForWind = minRadiusForWind;
                return this;
            }

            @AsOf("3.0.0")
            public Builder minBluntnessForWind(float minBluntnessForWind) {
                this.minBluntnessForWind = minBluntnessForWind;
                return this;
            }

            @AsOf("3.0.0")
            public LargeDripstoneConfiguration build() {
                return new LargeDripstoneConfiguration(
                    this.floorToCeilingSearchRange,
                    Preconditions.checkNotNull(this.columnRadius, "columnRadius"),
                    Preconditions.checkNotNull(this.heightScale, "heightScale"),
                    Preconditions.checkNotNull(this.maxColumnRadiusToCaveHeightRatio, "maxColumnRadiusToCaveHeightRatio"),
                    Preconditions.checkNotNull(this.stalactiteBluntness, "stalactiteBluntness"),
                    Preconditions.checkNotNull(this.stalagmiteBluntness, "stalagmiteBluntness"),
                    Preconditions.checkNotNull(this.windSpeed, "windSpeed"),
                    Preconditions.checkNotNull(this.minRadiusForWind, "minRadiusForWind"),
                    Preconditions.checkNotNull(this.minBluntnessForWind, "minBluntnessForWind")
                );
            }
        }
    }
}