package me.outspending.biomesapi.wrapper.worldgen.valueproviders;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.wrapper.NmsHandle;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Wraps the VerticalAnchor value-provider family. Sampling occurs
 * Minecraft code side, so this wrapper only carries the bounds/shape parameters.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@AsOf("2.3.0")
public sealed interface VerticalAnchor extends NmsHandle permits VerticalAnchor.Absolute, VerticalAnchor.AboveBottom, VerticalAnchor.BelowTop {

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.worldgen.valueproviders.VerticalAnchorFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        @NotNull Object toNms(@NotNull VerticalAnchor anchor);
    }

    /** An absolute Y coordinate. */
    @AsOf("2.3.0")
    static @NotNull VerticalAnchor absolute(int y) {
        return new Absolute(y);
    }

    /** Y measured upward from the bottom of the generation range. */
    @AsOf("2.3.0")
    static @NotNull VerticalAnchor aboveBottom(int offset) {
        return new AboveBottom(offset);
    }

    /** Y measured downward from the top of the generation range. */
    @AsOf("2.3.0")
    static @NotNull VerticalAnchor belowTop(int offset) {
        return new BelowTop(offset);
    }

    @AsOf("2.3.0")
    static @NotNull VerticalAnchor bottom() {
        return new AboveBottom(0);
    }

    @AsOf("2.3.0")
    static @NotNull VerticalAnchor top() {
        return new BelowTop(0);
    }

    @Override
    @AsOf("2.3.0")
    default @NotNull Object toMinecraft() {
        return WIRE.get().toNms(this);
    }

    @AsOf("2.3.0")
    record Absolute(int y) implements VerticalAnchor {}

    @AsOf("2.3.0")
    record AboveBottom(int offset) implements VerticalAnchor {}

    @AsOf("2.3.0")
    record BelowTop(int offset) implements VerticalAnchor {}
}