package me.outspending.biomesapi.registry.internal;

import me.outspending.biomesapi.keys.ResourceKey;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.stream.Stream;

@ApiStatus.Internal
public enum RegistryReference {
    BIOME("worldgen/biome"),
    FEATURE("worldgen/feature"),
    DIMENSION_TYPE("dimension_type"),
    DIMENSION("dimension"),
    NOISE_SETTINGS("worldgen/noise_settings");

    private final List<ResourceKey> registryKeys;

    RegistryReference(String... registryKeys) {
        this.registryKeys = Stream.of(registryKeys).map(ResourceKey::of).toList();
    }

    public List<ResourceKey> getRegistryKeys() {
        return registryKeys;
    }
}
