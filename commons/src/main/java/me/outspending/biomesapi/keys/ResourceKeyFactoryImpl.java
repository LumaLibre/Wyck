package me.outspending.biomesapi.keys;

import me.outspending.biomesapi.annotations.WireFactory;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@ApiStatus.Internal
public final class ResourceKeyFactoryImpl implements ResourceKey.Factory {
    @Override
    public ResourceKey create(String namespace, String path) {
        return new ResourceKeyImpl(namespace, path);
    }
}