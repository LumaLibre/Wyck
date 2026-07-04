package dev.wyck.registry.worldgen;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.WireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.level.noise.NoiseGeneratorSettings;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("2.4.0")
@ApiStatus.Internal
public interface NoiseGeneratorSettingsRegistry {

    @ApiStatus.Internal
    WireProvider<NoiseGeneratorSettingsRegistry> WIRE = WireProvider.create("dev.wyck.registry.worldgen.NoiseGeneratorSettingsRegistryImpl");

    @AsOf("2.4.0")
    void register(ResourceKey key, NoiseGeneratorSettings settings);

    @AsOf("2.4.0")
    static NoiseGeneratorSettingsRegistry registry() {
        return WIRE.get();
    }
}
