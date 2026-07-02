package dev.wyck.registry.internal;

import dev.wyck.keys.ResourceKey;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.stream.Stream;

@ApiStatus.Internal
public enum RegistryId {
    BIOME("worldgen/biome"),
    FEATURE("worldgen/feature"),
    DIMENSION_TYPE("dimension_type"),
    DIMENSION("dimension"),
    NOISE_SETTINGS("worldgen/noise_settings"),
    PARTICLE_TYPE("particle_type"),
    ENVIRONMENT_ATTRIBUTE("environment_attribute");

    private final List<ResourceKey> registryKeys;

    RegistryId(String... registryKeys) {
        this.registryKeys = Stream.of(registryKeys).map(ResourceKey::of).toList();
    }

    public List<ResourceKey> getRegistryKeys() {
        return registryKeys;
    }
}
