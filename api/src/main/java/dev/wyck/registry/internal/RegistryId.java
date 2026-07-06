package dev.wyck.registry.internal;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.stream.Stream;

@AsOf("2.4.0")
@ApiStatus.Internal
public enum RegistryId {
    BIOME("worldgen/biome"),
    FEATURE("worldgen/feature"),
    DIMENSION_TYPE("dimension_type"),
    DIMENSION("dimension"),
    NOISE_SETTINGS("worldgen/noise_settings"),
    PARTICLE_TYPE("particle_type"),
    ENVIRONMENT_ATTRIBUTE("environment_attribute"),
    FLUID("fluid");

    private final List<ResourceKey> keys;

    RegistryId(String... keys) {
        this.keys = Stream.of(keys).map(ResourceKey::minecraft).toList();
    }

    @AsOf("2.4.0")
    public List<ResourceKey> keys() {
        return keys;
    }
}
