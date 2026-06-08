package me.outspending.biomesapi.keys;

import net.kyori.adventure.key.KeyPattern;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
@ApiStatus.Internal
@SuppressWarnings("removal")
public record ResourceKeyImpl(Identifier identifier) implements ResourceKey, me.outspending.biomesapi.registry.BiomeResourceKey {

    public ResourceKeyImpl(String namespace, String path) {
        this(Identifier.fromNamespaceAndPath(namespace, path));
    }

    @Override
    @KeyPattern.Namespace
    @SuppressWarnings("PatternValidation")
    public String namespace() {
        return identifier.getNamespace();
    }

    @Override
    public String path() {
        return identifier.getPath();
    }

    @Override
    public Identifier resourceLocation() {
        return identifier;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ResourceKey other)) return false;
        return namespace().equals(other.namespace()) && path().equals(other.path());
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace(), path());
    }

    @Override
    public String toString() {
        return namespace() + ResourceKey.NAMESPACE_SEPARATOR + path();
    }
}