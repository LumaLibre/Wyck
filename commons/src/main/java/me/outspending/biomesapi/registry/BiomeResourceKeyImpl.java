package me.outspending.biomesapi.registry;

import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
@ApiStatus.Internal
public record BiomeResourceKeyImpl(Identifier resourceLocation) implements BiomeResourceKey {

    public BiomeResourceKeyImpl(String namespace, String path) {
        this(Identifier.fromNamespaceAndPath(namespace, path));
    }

    @Override
    public String namespace() {
        return resourceLocation.getNamespace();
    }

    @Override
    public String path() {
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
    public String toString() {
        return namespace() + NAMESPACE_SEPARATOR + path();
    }
}