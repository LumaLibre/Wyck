package me.outspending.biomesapi.wrapper.worldgen.valueproviders;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.wrapper.NmsHandle;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Wraps the VerticalAnchor value-provider family. Sampling occurs
 * Minecraft code side, so this wrapper only carries the bounds/shape parameters.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public sealed interface VerticalAnchor extends NmsHandle permits VerticalAnchor.Absolute, VerticalAnchor.AboveBottom, VerticalAnchor.BelowTop {

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.worldgen.valueproviders.VerticalAnchorFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        Object toNms(VerticalAnchor anchor);
    }

    /**
     * Y measured from the bottom of the world.
     * @param y Y measured from the bottom of the world
     * @return a vertical anchor with the given absolute Y value
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static VerticalAnchor absolute(int y) {
        return new Absolute(y);
    }

    /** Y measured upward from the bottom of the generation range.
     *
     * @param offset the offset from the bottom of the generation range
     * @return a vertical anchor with the given offset from the bottom of the generation range
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static VerticalAnchor aboveBottom(int offset) {
        return new AboveBottom(offset);
    }

    /**
     * Y measured downward from the top of the generation range.
     * @param offset the offset from the top of the generation range
     * @return a vertical anchor with the given offset from the top of the generation range
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static VerticalAnchor belowTop(int offset) {
        return new BelowTop(offset);
    }

    /**
     * Y measured from the bottom of the world.
     *
     * @return a vertical anchor with the Y value 0
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static VerticalAnchor bottom() {
        return new AboveBottom(0);
    }

    /**
     * Y measured from the top of the world.
     *
     * @return a vertical anchor with the Y value 0
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static VerticalAnchor top() {
        return new BelowTop(0);
    }

    /**
     * Converts this VerticalAnchor to an NMS VerticalAnchor.
     *
     * @return the NMS VerticalAnchor
     * @since 2.3.0
     */
    @Override
    @AsOf("2.3.0")
    default Object toMinecraft() {
        return WIRE.get().toNms(this);
    }

    @AsOf("2.3.0")
    record Absolute(int y) implements VerticalAnchor {}

    @AsOf("2.3.0")
    record AboveBottom(int offset) implements VerticalAnchor {}

    @AsOf("2.3.0")
    record BelowTop(int offset) implements VerticalAnchor {}
}