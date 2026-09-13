package dev.wyck.worldgen.structure.pools.elements;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.placement.PlacedFeature;
import dev.wyck.worldgen.structure.pools.PoolElement;
import dev.wyck.worldgen.structure.pools.Projection;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Places a {@link PlacedFeature} at a jigsaw block instead of a template.
 *
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
@ApiStatus.Experimental
public interface FeaturePoolElement extends PoolElement {

    /**
     * The feature this element places.
     * @return the placed feature
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    PlacedFeature feature();

    /**
     * Creates a new feature element.
     * @param projection how the feature is fitted to the terrain
     * @param feature the feature to place
     * @return a new feature element
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static FeaturePoolElement of(Projection projection, PlacedFeature feature) {
        record Holder() {
            static final ConstructWireProvider<FeaturePoolElement> WIRE =
                ConstructWireProvider.create("dev.wyck.worldgen.structure.pools.elements.FeaturePoolElementImpl");
        }
        return Holder.WIRE.construct(projection, feature);
    }
}
