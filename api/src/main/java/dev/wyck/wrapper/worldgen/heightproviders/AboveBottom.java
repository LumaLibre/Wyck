package dev.wyck.wrapper.worldgen.heightproviders;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * A relative height starting at the bottom of the world.
 *
 * @see <a href="https://minecraft.wiki/w/Custom_world_generation/vertical_anchor">Vertical anchor</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface AboveBottom extends VerticalAnchor {

    @ApiStatus.Internal
    ConstructWireProvider<AboveBottom> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.heightproviders.AboveBottomImpl");

    /** The bottom of the world. */
    @AsOf("3.0.0")
    AboveBottom BOTTOM = of(0);

    /**
     * The offset from the bottom of the generation range.
     * @return the offset from the bottom of the generation range
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int offset();

    /**
     * Creates a new above-bottom vertical anchor.
     * @param offset the offset from the bottom of the generation range
     * @return a new above-bottom vertical anchor
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static AboveBottom of(int offset) {
        return WIRE.construct(offset);
    }
}
