package me.outspending.biomesapi.wrapper.worldgen.valueproviders;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.wrapper.NmsHandle;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Wraps the HeightProvider value-provider family.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@AsOf("2.3.0")
public sealed interface HeightProvider extends NmsHandle permits HeightProvider.Constant, HeightProvider.Uniform, HeightProvider.Trapezoid, HeightProvider.BiasedToBottom, HeightProvider.VeryBiasedToBottom {

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.worldgen.valueproviders.HeightProviderFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        @NotNull Object toNms(@NotNull HeightProvider provider);
    }

    /** A single fixed anchor. */
    @AsOf("2.3.0")
    static @NotNull HeightProvider constant(@NotNull VerticalAnchor value) {
        return new Constant(value);
    }

    /** Uniformly distributed between two anchors (inclusive). */
    @AsOf("2.3.0")
    static @NotNull HeightProvider uniform(@NotNull VerticalAnchor minInclusive, @NotNull VerticalAnchor maxInclusive) {
        return new Uniform(minInclusive, maxInclusive);
    }

    /** Trapezoidal distribution with a flat plateau of the given height. */
    @AsOf("2.3.0")
    static @NotNull HeightProvider trapezoid(@NotNull VerticalAnchor minInclusive, @NotNull VerticalAnchor maxInclusive, int plateau) {
        return new Trapezoid(minInclusive, maxInclusive, plateau);
    }

    /** Weighted toward the bottom of the range. */
    @AsOf("2.3.0")
    static @NotNull HeightProvider biasedToBottom(@NotNull VerticalAnchor minInclusive, @NotNull VerticalAnchor maxInclusive, int inner) {
        return new BiasedToBottom(minInclusive, maxInclusive, inner);
    }

    /** Weighted very strongly toward the bottom of the range. */
    @AsOf("2.3.0")
    static @NotNull HeightProvider veryBiasedToBottom(@NotNull VerticalAnchor minInclusive, @NotNull VerticalAnchor maxInclusive, int inner) {
        return new VeryBiasedToBottom(minInclusive, maxInclusive, inner);
    }

    @Override
    @AsOf("2.3.0")
    default @NotNull Object toMinecraft() {
        return WIRE.get().toNms(this);
    }

    @AsOf("2.3.0")
    record Constant(@NotNull VerticalAnchor value) implements HeightProvider {}

    @AsOf("2.3.0")
    record Uniform(@NotNull VerticalAnchor minInclusive, @NotNull VerticalAnchor maxInclusive) implements HeightProvider {}

    @AsOf("2.3.0")
    record Trapezoid(@NotNull VerticalAnchor minInclusive, @NotNull VerticalAnchor maxInclusive, int plateau) implements HeightProvider {}

    @AsOf("2.3.0")
    record BiasedToBottom(@NotNull VerticalAnchor minInclusive, @NotNull VerticalAnchor maxInclusive, int inner) implements HeightProvider {}

    @AsOf("2.3.0")
    record VeryBiasedToBottom(@NotNull VerticalAnchor minInclusive, @NotNull VerticalAnchor maxInclusive, int inner) implements HeightProvider {}
}