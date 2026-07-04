package dev.wyck.registry.internal;

import dev.wyck.annotations.WireFactory;
import dev.wyck.keys.ResourceKey;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Collection;

@NullMarked
@WireFactory
@ApiStatus.Internal
public class WyckRegistryFactoryImpl implements WyckRegistry.Factory {
    @Override
    public WyckRegistry create(ResourceKey key) {
        return new WyckRegistryImpl<>(key);
    }

    @Override
    public WyckRegistry create(Collection<ResourceKey> keys) {
        return new WyckRegistryImpl<>(keys);
    }
}
