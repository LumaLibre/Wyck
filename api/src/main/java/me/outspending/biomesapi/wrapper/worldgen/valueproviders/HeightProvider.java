package me.outspending.biomesapi.wrapper.worldgen.valueproviders;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.wrapper.NmsHandle;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

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

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.worldgen.valueproviders.HeightProviderFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
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

    @AsOf("2.3.0")
    record Constant(VerticalAnchor value) implements HeightProvider {}

    @AsOf("2.3.0")
    record Uniform(VerticalAnchor minInclusive, VerticalAnchor maxInclusive) implements HeightProvider {}

    @AsOf("2.3.0")
    record Trapezoid(VerticalAnchor minInclusive, VerticalAnchor maxInclusive, int plateau) implements HeightProvider {}

    @AsOf("2.3.0")
    record BiasedToBottom(VerticalAnchor minInclusive, VerticalAnchor maxInclusive, int inner) implements HeightProvider {}

    @AsOf("2.3.0")
    record VeryBiasedToBottom(VerticalAnchor minInclusive, VerticalAnchor maxInclusive, int inner) implements HeightProvider {}
}