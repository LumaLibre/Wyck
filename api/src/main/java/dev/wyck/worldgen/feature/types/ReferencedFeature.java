package dev.wyck.worldgen.feature.types;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.feature.Feature;
import dev.wyck.worldgen.feature.Features;
import org.jspecify.annotations.NullMarked;

/**
 * A reference to an existing configured feature.
 *
 * @see <a href="https://minecraft.wiki/w/Configured_feature">Configured feature</a>
 * @see Features
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface ReferencedFeature extends Feature {

    /**
     * Creates a new reference to the configured feature with the given key.
     * @param key the key of the configured feature
     * @return the reference to the configured feature
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ReferencedFeature of(ResourceKey key) {
        record Holder() {
            static final ConstructWireProvider<ReferencedFeature> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.types.ReferencedFeatureImpl");
        }
        return Holder.WIRE.construct(key);
    }
}
