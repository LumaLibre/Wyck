package me.outspending.biomesapi.wrapper.worldgen.valueproviders;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;

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

    Codec<VerticalAnchor> CODEC = Codec.xor(Absolute.CODEC, Codec.xor(AboveBottom.CODEC, BelowTop.CODEC))
        .xmap(VerticalAnchor::merge, VerticalAnchor::split);

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

    private static VerticalAnchor merge(Either<Absolute, Either<AboveBottom, BelowTop>> either) {
        return either.map(Function.identity(), Either::unwrap);
    }

    private static Either<Absolute, Either<AboveBottom, BelowTop>> split(VerticalAnchor anchor) {
        return anchor instanceof Absolute a
            ? Either.left(a)
            : Either.right(anchor instanceof AboveBottom ab ? Either.left(ab) : Either.right((BelowTop) anchor));
    }

    @AsOf("2.3.0")
    record Absolute(int y) implements VerticalAnchor {
        public static final Codec<Absolute> CODEC = Codec.INT.fieldOf("absolute")
            .xmap(Absolute::new, Absolute::y).codec();
    }

    @AsOf("2.3.0")
    record AboveBottom(int offset) implements VerticalAnchor {
        public static final Codec<AboveBottom> CODEC = Codec.INT.fieldOf("above_bottom")
            .xmap(AboveBottom::new, AboveBottom::offset).codec();
    }

    @AsOf("2.3.0")
    record BelowTop(int offset) implements VerticalAnchor {
        public static final Codec<BelowTop> CODEC = Codec.INT.fieldOf("below_top")
            .xmap(BelowTop::new, BelowTop::offset).codec();
    }
}