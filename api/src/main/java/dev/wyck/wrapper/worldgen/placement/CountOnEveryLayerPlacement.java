package dev.wyck.wrapper.worldgen.placement;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * In the horizontal relative range (0,0) to (16,16), at each vertical layer separated by air,
 * lava or water, tries to randomly select the specified number of horizontal positions, whose Y coordinate is one block above this layer at this selected horizontal position.
 * Return these selected positions.
 *
 * @apiNote Deprecated in Minecraft.
 * @see <a href="https://minecraft.wiki/w/Placed_feature#Placement_modifiers">Placement modifiers</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface CountOnEveryLayerPlacement extends PlacementModifier {

    @ApiStatus.Internal
    ConstructWireProvider<CountOnEveryLayerPlacement> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.placement.CountOnEveryLayerPlacementImpl");

    /**
     * Count on each layer between 0 and 256.
     * @return the count
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider count();

    /**
     * Creates a new count on each layer placement.
     * @deprecated deprecated in Minecraft.
     * @param count the count
     * @return a new count on each layer placement
     * @since 3.0.0
     */
    @Deprecated
    @AsOf("3.0.0")
    static CountOnEveryLayerPlacement of(IntProvider count) {
        Preconditions.checkArgument(count.minInclusive() >= 0 && count.maxInclusive() <= 256, "count must be between 0 and 256");
        return WIRE.construct(count);
    }
}
