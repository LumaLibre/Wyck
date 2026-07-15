package dev.wyck.wrapper.worldgen.heightproviders;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * A relative height starting at the top of the world. Higher values move the height down.
 *
 * @see <a href="https://minecraft.wiki/w/Custom_world_generation/vertical_anchor">Vertical anchor</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface BelowTop extends VerticalAnchor {

    @ApiStatus.Internal
    ConstructWireProvider<BelowTop> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.heightproviders.BelowTopImpl");

    /** The top of the world. */
    @AsOf("3.0.0")
    BelowTop TOP = of(0);

    /**
     * The offset from the top of the generation range.
     * @return the offset from the top of the generation range
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int offset();

    /**
     * Creates a new below-top vertical anchor.
     * @param offset the offset from the top of the generation range
     * @return a new below-top vertical anchor
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BelowTop of(int offset) {
        return WIRE.construct(offset);
    }
}
