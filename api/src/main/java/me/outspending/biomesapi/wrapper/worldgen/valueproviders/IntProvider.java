package me.outspending.biomesapi.wrapper.worldgen.valueproviders;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.serialization.StringRepresentable;
import me.outspending.biomesapi.util.WeightedList;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Map;

/**
 * Wraps the IntProvider value-provider family. Sampling occurs NMS-side,
 * so this wrapper only carries the bounds/shape parameters.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public sealed interface IntProvider extends NmsHandle, StringRepresentable permits IntProvider.Constant, IntProvider.Uniform, IntProvider.BiasedToBottom, IntProvider.ClampedNormal, IntProvider.Trapezoid, IntProvider.Clamped, IntProvider.WeightedListInt {

    Codec<IntProvider> CODEC = Codec.recursive("IntProvider", self -> {
        Map<String, MapCodec<? extends IntProvider>> byType = Map.of(
            "constant", Constant.MAP_CODEC,
            "uniform", Uniform.MAP_CODEC,
            "biased_to_bottom", BiasedToBottom.MAP_CODEC,
            "clamped_normal", ClampedNormal.MAP_CODEC,
            "trapezoid", Trapezoid.MAP_CODEC,
            "clamped", Clamped.mapCodec(self),
            "weighted_list", WeightedListInt.mapCodec(self)
        );
        return Codec.STRING.dispatch("type", IntProvider::type, byType::get);
    });

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.*.wrapper.worldgen.valueproviders.IntProviderFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        Object toNms(IntProvider provider);
    }

    @AsOf("2.3.0")
    static IntProvider constant(int value) {
        return new Constant(value);
    }

    @AsOf("2.3.0")
    static IntProvider uniform(int minInclusive, int maxInclusive) {
        return new Uniform(minInclusive, maxInclusive);
    }

    @AsOf("2.3.0")
    static IntProvider biasedToBottom(int minInclusive, int maxInclusive) {
        return new BiasedToBottom(minInclusive, maxInclusive);
    }

    @AsOf("2.3.0")
    static IntProvider clampedNormal(float mean, float deviation, int minInclusive, int maxInclusive) {
        return new ClampedNormal(mean, deviation, minInclusive, maxInclusive);
    }

    @AsOf("2.3.0")
    static IntProvider trapezoid(int minInclusive, int maxInclusive, int plateau) {
        return new Trapezoid(minInclusive, maxInclusive, plateau);
    }

    /** A symmetric triangular distribution over [-range, range], matching TrapezoidInt.triangle. */
    @AsOf("2.3.0")
    static IntProvider triangle(int range) {
        return new Trapezoid(-range, range, 0);
    }

    @AsOf("2.3.0")
    static IntProvider clamped(IntProvider source, int minInclusive, int maxInclusive) {
        return new Clamped(source, minInclusive, maxInclusive);
    }

    @AsOf("2.3.0")
    static IntProvider weightedList(WeightedList<IntProvider> distribution) {
        return new WeightedListInt(distribution);
    }

    /**
     * The smallest value this provider can yield.
     *
     * @return the smallest value this provider can yield.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    int minInclusive();

    /**
     * The largest value this provider can yield.
     *
     * @return the largest value this provider can yield.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    int maxInclusive();

    @Override
    @AsOf("2.3.0")
    default Object toMinecraft() {
        return WIRE.get().toNms(this);
    }

    @AsOf("2.3.0")
    record Constant(int value) implements IntProvider {

        public static final MapCodec<Constant> MAP_CODEC = Codec.INT.fieldOf("value")
            .xmap(Constant::new, Constant::value);

        @Override
        public int minInclusive() {
            return this.value;
        }

        @Override
        public int maxInclusive() {
            return this.value;
        }
    }

    @AsOf("2.3.0")
    record Uniform(int minInclusive, int maxInclusive) implements IntProvider {
        public static final MapCodec<Uniform> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codec.INT.fieldOf("min_inclusive").forGetter(Uniform::minInclusive),
            Codec.INT.fieldOf("max_inclusive").forGetter(Uniform::maxInclusive)
        ).apply(i, Uniform::new));
    }

    @AsOf("2.3.0")
    record BiasedToBottom(int minInclusive, int maxInclusive) implements IntProvider {
        public static final MapCodec<BiasedToBottom> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codec.INT.fieldOf("min_inclusive").forGetter(BiasedToBottom::minInclusive),
            Codec.INT.fieldOf("max_inclusive").forGetter(BiasedToBottom::maxInclusive)
        ).apply(i, BiasedToBottom::new));
    }

    @AsOf("2.3.0")
    record ClampedNormal(float mean, float deviation, int minInclusive, int maxInclusive) implements IntProvider {
        public static final MapCodec<ClampedNormal> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codec.FLOAT.fieldOf("mean").forGetter(ClampedNormal::mean),
            Codec.FLOAT.fieldOf("deviation").forGetter(ClampedNormal::deviation),
            Codec.INT.fieldOf("min_inclusive").forGetter(ClampedNormal::minInclusive),
            Codec.INT.fieldOf("max_inclusive").forGetter(ClampedNormal::maxInclusive)
        ).apply(i, ClampedNormal::new));
    }

    @AsOf("2.3.0")
    record Trapezoid(int minInclusive, int maxInclusive, int plateau) implements IntProvider {
        public static final MapCodec<Trapezoid> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codec.INT.fieldOf("min_inclusive").forGetter(Trapezoid::minInclusive),
            Codec.INT.fieldOf("max_inclusive").forGetter(Trapezoid::maxInclusive),
            Codec.INT.fieldOf("plateau").forGetter(Trapezoid::plateau)
        ).apply(i, Trapezoid::new));
    }

    // recursive - composes a source provider, narrowing its range
    @AsOf("2.3.0")
    record Clamped(IntProvider source, int minInclusive, int maxInclusive) implements IntProvider {
        @AsOf("2.3.0")
        public Clamped {
            Preconditions.checkNotNull(source, "source");
        }

        public static MapCodec<Clamped> mapCodec(Codec<IntProvider> self) {
            return RecordCodecBuilder.mapCodec(i -> i.group(
                self.fieldOf("source").forGetter(Clamped::source),
                Codec.INT.fieldOf("min_inclusive").forGetter(Clamped::minInclusive),
                Codec.INT.fieldOf("max_inclusive").forGetter(Clamped::maxInclusive)
            ).apply(i, Clamped::new));
        }
    }

    // recursive - composes a weighted distribution of providers
    @AsOf("2.3.0")
    record WeightedListInt(WeightedList<IntProvider> distribution) implements IntProvider {

        @AsOf("2.3.0")
        public WeightedListInt {
            Preconditions.checkNotNull(distribution, "distribution");
            Preconditions.checkArgument(!distribution.unwrap().isEmpty(), "distribution must not be empty");
        }

        @Override
        public int minInclusive() {
            int min = Integer.MAX_VALUE;
            for (WeightedList.Weighted<IntProvider> entry : this.distribution.unwrap()) {
                min = Math.min(min, entry.value().minInclusive());
            }
            return min;
        }

        @Override
        public int maxInclusive() {
            int max = Integer.MIN_VALUE;
            for (WeightedList.Weighted<IntProvider> entry : this.distribution.unwrap()) {
                max = Math.max(max, entry.value().maxInclusive());
            }
            return max;
        }

        public static MapCodec<WeightedListInt> mapCodec(Codec<IntProvider> self) {
            return WeightedList.codec(self).fieldOf("distribution")
                .xmap(WeightedListInt::new, WeightedListInt::distribution);
        }
    }
}