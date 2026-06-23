package me.outspending.biomesapi.registry.worldgen;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.wrapper.level.noise.LevelNoiseGeneratorSettings;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("2.4.0")
@ApiStatus.Internal
public interface LevelNoiseGeneratorSettingsRegistry {

    @ApiStatus.Internal
    WireProvider<LevelNoiseGeneratorSettingsRegistry> WIRE = WireProvider.create("me.outspending.biomesapi.registry.worldgen.LevelNoiseGeneratorSettingsRegistryImpl");

    @AsOf("2.4.0")
    void register(ResourceKey key, LevelNoiseGeneratorSettings settings);

    @AsOf("2.4.0")
    static LevelNoiseGeneratorSettingsRegistry registry() {
        return WIRE.get();
    }
}
