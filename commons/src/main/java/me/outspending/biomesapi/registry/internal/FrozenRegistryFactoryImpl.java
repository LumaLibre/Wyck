package me.outspending.biomesapi.registry.internal;

import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.keys.ResourceKey;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@ApiStatus.Internal
public class FrozenRegistryFactoryImpl implements FrozenRegistry.Factory {
    @Override
    public FrozenRegistry create(ResourceKey key) {
        return new FrozenRegistryImpl<>(key);
    }
}
