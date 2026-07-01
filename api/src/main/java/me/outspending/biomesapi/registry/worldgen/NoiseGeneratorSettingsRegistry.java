package me.outspending.biomesapi.registry.worldgen;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.wrapper.level.noise.NoiseGeneratorSettings;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("2.4.0")
@ApiStatus.Internal
public interface NoiseGeneratorSettingsRegistry {

    @ApiStatus.Internal
    WireProvider<NoiseGeneratorSettingsRegistry> WIRE = WireProvider.create("me.outspending.biomesapi.registry.worldgen.NoiseGeneratorSettingsRegistryImpl");

    @AsOf("2.4.0")
    void register(ResourceKey key, NoiseGeneratorSettings settings);

    @AsOf("2.4.0")
    static NoiseGeneratorSettingsRegistry registry() {
        return WIRE.get();
    }
}
