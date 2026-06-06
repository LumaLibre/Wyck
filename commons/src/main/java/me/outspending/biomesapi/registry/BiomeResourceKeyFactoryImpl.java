package me.outspending.biomesapi.registry;

import me.outspending.biomesapi.annotations.WireFactory;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@ApiStatus.Internal
public final class BiomeResourceKeyFactoryImpl implements BiomeResourceKey.Factory {
    @Override
    public BiomeResourceKey create(String namespace, String path) {
        return new BiomeResourceKeyImpl(namespace, path);
    }
}