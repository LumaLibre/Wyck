package me.outspending.biomesapi.wrapper.worldgen.valueproviders;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Wraps the FloatProvider value-provider family. Sampling occurs
 * Minecraft code side, so this wrapper only carries the bounds/shape parameters.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public sealed interface FloatProvider extends NmsHandle permits FloatProvider.Constant, FloatProvider.Uniform, FloatProvider.ClampedNormal, FloatProvider.Trapezoid {

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.worldgen.valueproviders.FloatProviderFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        Object toNms(FloatProvider provider);
    }

    @AsOf("2.3.0")
    static FloatProvider constant(float value) { return new Constant(value); }

    @AsOf("2.3.0")
    static FloatProvider uniform(float minInclusive, float maxExclusive) { return new Uniform(minInclusive, maxExclusive); }

    @AsOf("2.3.0")
    static FloatProvider clampedNormal(float mean, float deviation, float min, float max) { return new ClampedNormal(mean, deviation, min, max); }

    @AsOf("2.3.0")
    static FloatProvider trapezoid(float min, float max, float plateau) { return new Trapezoid(min, max, plateau); }

    /**
     * @return the smallest value this provider can yield.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    float minValue();

    /**
     * @return the largest value this provider can yield.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    float maxValue();

    @Override
    @AsOf("2.3.0")
    default Object toMinecraft() {
        return WIRE.get().toNms(this);
    }

    @AsOf("2.3.0")
    record Constant(float value) implements FloatProvider {
        @Override
        public float minValue() {
            return value;
        }
        @Override
        public float maxValue() {
            return value;
        }
    }

    @AsOf("2.3.0")
    record Uniform(float minInclusive, float maxExclusive) implements FloatProvider {
        @Override
        public float minValue() {
            return minInclusive;
        }
        @Override
        public float maxValue() {
            return maxExclusive;
        }
    }

    @AsOf("2.3.0")
    record ClampedNormal(float mean, float deviation, float min, float max) implements FloatProvider {
        @Override
        public float minValue() {
            return min;
        }
        @Override
        public float maxValue() {
            return max;
        }
    }

    @AsOf("2.3.0")
    record Trapezoid(float min, float max, float plateau) implements FloatProvider {
        @Override
        public float minValue() {
            return min;
        }
        @Override
        public float maxValue() {
            return max;
        }
    }
}