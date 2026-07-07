package dev.wyck.wrapper.worldgen.placement;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Adds a random value between 0 and 15 (inclusive) to both the X and Z coordinates. This is a
 * shortcut for a random offset modifier with a zero Y spread and a uniform XZ spread from 0 to 15.
 *
 * @see <a href="https://minecraft.wiki/w/Placed_feature#Placement_modifiers">Placement modifiers</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface InSquarePlacement extends PlacementModifier {

    @ApiStatus.Internal
    ConstructWireProvider<InSquarePlacement> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.placement.InSquarePlacementImpl");

    /** The singleton instance of {@link InSquarePlacement}. */
    @AsOf("3.0.0")
    InSquarePlacement INSTANCE = of();

    private static InSquarePlacement of() {
        return WIRE.construct();
    }
}