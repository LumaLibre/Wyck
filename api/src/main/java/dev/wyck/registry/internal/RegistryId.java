package dev.wyck.registry.internal;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.stream.Stream;

@AsOf("2.4.0")
@ApiStatus.Internal
public enum RegistryId {
    // minor TODO: sort these
    BLOCK("block"),
    ITEM("item"),
    ENTITY_TYPE("entity_type"),
    BIOME("worldgen/biome"),
    FEATURE("worldgen/feature"),
    DIMENSION_TYPE("dimension_type"),
    DIMENSION("dimension"),
    NOISE_SETTINGS("worldgen/noise_settings"),
    PARTICLE_TYPE("particle_type"),
    ENVIRONMENT_ATTRIBUTE("environment_attribute"),
    FLUID("fluid"),
    CONFIGURED_CARVER("worldgen/configured_carver"),
    CARVER("worldgen/carver"),
    NOISE("worldgen/noise"),
    CONFIGURED_FEATURE("worldgen/configured_feature"),
    DENSITY_FUNCTION("worldgen/density_function"),
    ACTIVITY("activity"),
    WORLD_CLOCK("world_clock");

    private final List<ResourceKey> keys;

    RegistryId(String... keys) {
        this.keys = Stream.of(keys).map(ResourceKey::minecraft).toList();
    }

    @AsOf("2.4.0")
    public List<ResourceKey> keys() {
        return keys;
    }
}
