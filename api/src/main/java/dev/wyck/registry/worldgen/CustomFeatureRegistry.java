package dev.wyck.registry.worldgen;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.WireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.worldgen.feature.custom.CustomFeature;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Wire contract for registering a CustomFeature into the FEATURE registry.
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
@ApiStatus.Internal
public interface CustomFeatureRegistry {

    @ApiStatus.Internal
    WireProvider<CustomFeatureRegistry> WIRE = WireProvider.create("dev.wyck.registry.worldgen.CustomFeatureRegistryImpl");

    @AsOf("2.3.0")
    void register(ResourceKey key, CustomFeature<?> feature);

    @AsOf("2.4.0")
    static CustomFeatureRegistry registry() {
        return WIRE.get();
    }
}