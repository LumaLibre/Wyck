package me.outspending.biomesapi.registry.worldgen;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.wrapper.worldgen.feature.custom.CustomFeature;
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
    @AsOf("2.3.0")
    void register(ResourceKey key, CustomFeature<?> feature);
}