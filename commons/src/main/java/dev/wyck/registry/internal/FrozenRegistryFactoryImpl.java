package dev.wyck.registry.internal;

import dev.wyck.annotations.WireFactory;
import dev.wyck.keys.ResourceKey;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Collection;

@NullMarked
@WireFactory
@ApiStatus.Internal
public class FrozenRegistryFactoryImpl implements FrozenRegistry.Factory {
    @Override
    public FrozenRegistry create(ResourceKey key) {
        return new FrozenRegistryImpl<>(key);
    }

    @Override
    public FrozenRegistry create(Collection<ResourceKey> keys) {
        return new FrozenRegistryImpl<>(keys);
    }
}
