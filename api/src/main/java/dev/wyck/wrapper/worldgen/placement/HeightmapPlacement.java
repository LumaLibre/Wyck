package dev.wyck.wrapper.worldgen.placement;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.worldgen.HeightmapType;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Sets the Y coordinate to one block above the heightmap and returns the new position.
 *
 * @see <a href="https://minecraft.wiki/w/Placed_feature#Placement_modifiers">Placement modifiers</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface HeightmapPlacement extends PlacementModifier {

    @ApiStatus.Internal
    ConstructWireProvider<HeightmapPlacement> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.placement.HeightmapPlacementImpl");

    /**
     * The heightmap to use.
     * @return the heightmap type
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    HeightmapType heightmap();

    /**
     * Creates a new heightmap placement.
     * @param heightmap the heightmap type
     * @return a new heightmap placement
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static HeightmapPlacement of(HeightmapType heightmap) {
        return WIRE.construct(heightmap);
    }
}
