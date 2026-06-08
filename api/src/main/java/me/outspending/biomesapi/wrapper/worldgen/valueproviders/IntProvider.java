package me.outspending.biomesapi.wrapper.worldgen.valueproviders;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.util.WeightedList;
import me.outspending.biomesapi.wrapper.NmsHandle;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Objects;

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
public sealed interface IntProvider extends NmsHandle permits IntProvider.Constant, IntProvider.Uniform, IntProvider.BiasedToBottom, IntProvider.ClampedNormal, IntProvider.Trapezoid, IntProvider.Clamped, IntProvider.WeightedListInt {

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

    /** Smallest value this provider can yield. */
    @AsOf("2.3.0") int minInclusive();

    /** Largest value this provider can yield. */
    @AsOf("2.3.0") int maxInclusive();

    @Override
    @AsOf("2.3.0")
    default Object toMinecraft() {
        return WIRE.get().toNms(this);
    }

    @AsOf("2.3.0")
    record Constant(int value) implements IntProvider {

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
    record Uniform(int minInclusive, int maxInclusive) implements IntProvider {}

    @AsOf("2.3.0")
    record BiasedToBottom(int minInclusive, int maxInclusive) implements IntProvider {}

    @AsOf("2.3.0")
    record ClampedNormal(float mean, float deviation, int minInclusive, int maxInclusive) implements IntProvider {}

    @AsOf("2.3.0")
    record Trapezoid(int minInclusive, int maxInclusive, int plateau) implements IntProvider {}

    // recursive - composes a source provider, narrowing its range
    @AsOf("2.3.0")
    record Clamped(IntProvider source, int minInclusive, int maxInclusive) implements IntProvider {

        @AsOf("2.3.0")
        public Clamped {
            Objects.requireNonNull(source, "source");
        }
    }

    // recursive - composes a weighted distribution of providers
    @AsOf("2.3.0")
    record WeightedListInt(WeightedList<IntProvider> distribution) implements IntProvider {

        @AsOf("2.3.0")
        public WeightedListInt {
            Objects.requireNonNull(distribution, "distribution");
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
    }
}