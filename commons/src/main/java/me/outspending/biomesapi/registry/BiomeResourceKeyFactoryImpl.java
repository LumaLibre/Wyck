package me.outspending.biomesapi.registry;

import me.outspending.biomesapi.annotations.WireFactory;

@WireFactory
public final class BiomeResourceKeyFactoryImpl implements BiomeResourceKey.Factory {
    @Override
    public BiomeResourceKey create(String namespace, String path) {
        return new BiomeResourceKeyImpl(namespace, path);
    }
}