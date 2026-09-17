package dev.wyck.worldgen.structure.types;

import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.Lazy;
import dev.wyck.wrapper.Wrapper;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.NoSuchElementException;
import java.util.Optional;

@NullMarked
@ApiStatus.Internal
final class StructureRegistration {

    private static final Lazy<WyckRegistry> REGISTRY = WyckRegistry.lazy(RegistryId.STRUCTURE);

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    static void register(Optional<ResourceKey> resourceKey, Wrapper structure) {
        ResourceKey key = resourceKey.orElseThrow(() -> new NoSuchElementException("Cannot register a structure without a resource key."));
        REGISTRY.get().register(key, structure);
    }
}
