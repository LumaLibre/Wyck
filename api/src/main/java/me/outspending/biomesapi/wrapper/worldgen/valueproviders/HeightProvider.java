package me.outspending.biomesapi.wrapper.worldgen.valueproviders;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.wrapper.internal.NmsDecoder;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;

/**
 * Wraps the HeightProvider value-provider family.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public sealed interface HeightProvider extends NmsHandle permits HeightProvider.Constant, HeightProvider.Uniform, HeightProvider.Trapezoid, HeightProvider.BiasedToBottom, HeightProvider.VeryBiasedToBottom {

    Codec<HeightProvider> CODEC = Codec.xor(
        Constant.CODEC, Codec.xor(Uniform.CODEC, Codec.xor(Trapezoid.CODEC,
            Codec.xor(BiasedToBottom.CODEC, VeryBiasedToBottom.CODEC)))
    ).xmap(
        e -> e.map(Function.identity(),
            e1 -> e1.map(Function.identity(),
                e2 -> e2.map(Function.identity(),
                    e3 -> e3.map(b -> (HeightProvider) b, v -> (HeightProvider) v)
                )
            )
        ),
        p -> p instanceof Constant c ? Either.left(c)
            : Either.right(p instanceof Uniform u ? Either.left(u)
            : Either.right(p instanceof Trapezoid t ? Either.left(t)
            : Either.right(p instanceof BiasedToBottom b ? Either.left(b)
            : Either.right((VeryBiasedToBottom) p))))
    );

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.worldgen.valueproviders.HeightProviderFactoryImpl");

    @ApiStatus.Internal
    interface Factory extends NmsDecoder<HeightProvider> {
        Object toNms(HeightProvider provider);
    }

    /** A single fixed anchor. */
    @AsOf("2.3.0")
    static HeightProvider constant(VerticalAnchor value) {
        return new Constant(value);
    }

    /** Uniformly distributed between two anchors (inclusive). */
    @AsOf("2.3.0")
    static HeightProvider uniform(VerticalAnchor minInclusive, VerticalAnchor maxInclusive) {
        return new Uniform(minInclusive, maxInclusive);
    }

    /** Trapezoidal distribution with a flat plateau of the given height. */
    @AsOf("2.3.0")
    static HeightProvider trapezoid(VerticalAnchor minInclusive, VerticalAnchor maxInclusive, int plateau) {
        return new Trapezoid(minInclusive, maxInclusive, plateau);
    }

    /** Weighted toward the bottom of the range. */
    @AsOf("2.3.0")
    static HeightProvider biasedToBottom(VerticalAnchor minInclusive, VerticalAnchor maxInclusive, int inner) {
        return new BiasedToBottom(minInclusive, maxInclusive, inner);
    }

    /** Weighted very strongly toward the bottom of the range. */
    @AsOf("2.3.0")
    static HeightProvider veryBiasedToBottom(VerticalAnchor minInclusive, VerticalAnchor maxInclusive, int inner) {
        return new VeryBiasedToBottom(minInclusive, maxInclusive, inner);
    }

    @Override
    @AsOf("2.3.0")
    default Object toMinecraft() {
        return WIRE.get().toNms(this);
    }

    @AsOf("2.4.0")
    static HeightProvider fromMinecraft(Object nms) {
        return WIRE.get().fromMinecraft(nms);
    }

    @AsOf("2.3.0")
    record Constant(VerticalAnchor value) implements HeightProvider {
        public static final Codec<Constant> CODEC = VerticalAnchor.CODEC.fieldOf("constant")
            .xmap(Constant::new, Constant::value).codec();
    }

    @AsOf("2.3.0")
    record Uniform(VerticalAnchor minInclusive, VerticalAnchor maxInclusive) implements HeightProvider {
        public static final Codec<Uniform> CODEC = RecordCodecBuilder.<Uniform>create(i -> i.group(
            VerticalAnchor.CODEC.fieldOf("min_inclusive").forGetter(Uniform::minInclusive),
            VerticalAnchor.CODEC.fieldOf("max_inclusive").forGetter(Uniform::maxInclusive)
        ).apply(i, Uniform::new)).fieldOf("uniform").codec();
    }

    @AsOf("2.3.0")
    record Trapezoid(VerticalAnchor minInclusive, VerticalAnchor maxInclusive, int plateau) implements HeightProvider {
        public static final Codec<Trapezoid> CODEC = RecordCodecBuilder.<Trapezoid>create(i -> i.group(
            VerticalAnchor.CODEC.fieldOf("min_inclusive").forGetter(Trapezoid::minInclusive),
            VerticalAnchor.CODEC.fieldOf("max_inclusive").forGetter(Trapezoid::maxInclusive),
            Codec.INT.fieldOf("plateau").forGetter(Trapezoid::plateau)
        ).apply(i, Trapezoid::new)).fieldOf("trapezoid").codec();
    }

    @AsOf("2.3.0")
    record BiasedToBottom(VerticalAnchor minInclusive, VerticalAnchor maxInclusive, int inner) implements HeightProvider {
        public static final Codec<BiasedToBottom> CODEC = RecordCodecBuilder.<BiasedToBottom>create(i -> i.group(
            VerticalAnchor.CODEC.fieldOf("min_inclusive").forGetter(BiasedToBottom::minInclusive),
            VerticalAnchor.CODEC.fieldOf("max_inclusive").forGetter(BiasedToBottom::maxInclusive),
            Codec.INT.fieldOf("inner").forGetter(BiasedToBottom::inner)
        ).apply(i, BiasedToBottom::new)).fieldOf("biased_to_bottom").codec();
    }

    @AsOf("2.3.0")
    record VeryBiasedToBottom(VerticalAnchor minInclusive, VerticalAnchor maxInclusive, int inner) implements HeightProvider {
        public static final Codec<VeryBiasedToBottom> CODEC = RecordCodecBuilder.<VeryBiasedToBottom>create(i -> i.group(
            VerticalAnchor.CODEC.fieldOf("min_inclusive").forGetter(VeryBiasedToBottom::minInclusive),
            VerticalAnchor.CODEC.fieldOf("max_inclusive").forGetter(VeryBiasedToBottom::maxInclusive),
            Codec.INT.fieldOf("inner").forGetter(VeryBiasedToBottom::inner)
        ).apply(i, VeryBiasedToBottom::new)).fieldOf("very_biased_to_bottom").codec();
    }
}