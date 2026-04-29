package me.outspending.biomesapi.registry;

import me.outspending.biomesapi.api.registry.BiomeResourceKey;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record BiomeResourceKeyImpl(@NotNull Identifier resourceLocation) implements BiomeResourceKey {

    public BiomeResourceKeyImpl(String namespace, String path) {
        this(Identifier.fromNamespaceAndPath(namespace, path));
    }

    @Override
    public @NotNull String namespace() {
        return resourceLocation.getNamespace();
    }

    @Override
    public @NotNull String path() {
        return resourceLocation.getPath();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof BiomeResourceKey other)) return false;
        return namespace().equals(other.namespace()) && path().equals(other.path());
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace(), path());
    }

    @Override
    public @NotNull String toString() {
        return namespace() + NAMESPACE_SEPARATOR + path();
    }
}