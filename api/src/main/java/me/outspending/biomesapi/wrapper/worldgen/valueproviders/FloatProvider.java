package me.outspending.biomesapi.wrapper.worldgen.valueproviders;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;

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

    Codec<FloatProvider> CODEC = Codec.xor(
        Constant.CODEC, Codec.xor(Uniform.CODEC, Codec.xor(ClampedNormal.CODEC, Trapezoid.CODEC))
    ).xmap(
        e -> e.map(Function.identity(), e1 -> e1.map(Function.identity(), Either::unwrap)),
        p -> p instanceof Constant c ? Either.left(c)
            : Either.right(p instanceof Uniform u ? Either.left(u)
            : Either.right(p instanceof ClampedNormal cn ? Either.left(cn)
            : Either.right((Trapezoid) p)))
    );

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.worldgen.valueproviders.FloatProviderFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        Object toNms(FloatProvider provider);
    }

    @AsOf("2.3.0")
    static FloatProvider constant(float value) {
        return new Constant(value);
    }

    @AsOf("2.3.0")
    static FloatProvider uniform(float minInclusive, float maxExclusive) {
        return new Uniform(minInclusive, maxExclusive);
    }

    @AsOf("2.3.0")
    static FloatProvider clampedNormal(float mean, float deviation, float min, float max) {
        return new ClampedNormal(mean, deviation, min, max);
    }

    @AsOf("2.3.0")
    static FloatProvider trapezoid(float min, float max, float plateau) {
        return new Trapezoid(min, max, plateau);
    }

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

        public static final Codec<Constant> CODEC = Codec.FLOAT.fieldOf("constant")
            .xmap(Constant::new, Constant::value).codec();

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

        public static final Codec<Uniform> CODEC = RecordCodecBuilder.<Uniform>create(i -> i.group(
            Codec.FLOAT.fieldOf("min_inclusive").forGetter(Uniform::minInclusive),
            Codec.FLOAT.fieldOf("max_exclusive").forGetter(Uniform::maxExclusive)
        ).apply(i, Uniform::new)).fieldOf("uniform").codec();

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

        public static final Codec<ClampedNormal> CODEC = RecordCodecBuilder.<ClampedNormal>create(i -> i.group(
            Codec.FLOAT.fieldOf("mean").forGetter(ClampedNormal::mean),
            Codec.FLOAT.fieldOf("deviation").forGetter(ClampedNormal::deviation),
            Codec.FLOAT.fieldOf("min").forGetter(ClampedNormal::min),
            Codec.FLOAT.fieldOf("max").forGetter(ClampedNormal::max)
        ).apply(i, ClampedNormal::new)).fieldOf("clamped_normal").codec();

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

        public static final Codec<Trapezoid> CODEC = RecordCodecBuilder.<Trapezoid>create(i -> i.group(
            Codec.FLOAT.fieldOf("min").forGetter(Trapezoid::min),
            Codec.FLOAT.fieldOf("max").forGetter(Trapezoid::max),
            Codec.FLOAT.fieldOf("plateau").forGetter(Trapezoid::plateau)
        ).apply(i, Trapezoid::new)).fieldOf("trapezoid").codec();

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