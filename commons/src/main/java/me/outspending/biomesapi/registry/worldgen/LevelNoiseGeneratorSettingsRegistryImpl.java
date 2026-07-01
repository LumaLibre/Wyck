package me.outspending.biomesapi.registry.worldgen;

import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.registry.internal.FrozenRegistry;
import me.outspending.biomesapi.util.Lazy;
import me.outspending.biomesapi.wrapper.level.noise.NoiseGeneratorSettings;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class LevelNoiseGeneratorSettingsRegistryImpl implements LevelNoiseGeneratorSettingsRegistry {

    private final Lazy<FrozenRegistry> noiseSettingsRegistry = FrozenRegistry.lazy("worldgen/noise_settings");

    @Override
    @SuppressWarnings("unchecked")
    public void register(ResourceKey key, NoiseGeneratorSettings settings) {
        net.minecraft.world.level.levelgen.NoiseGeneratorSettings noiseSettings = (net.minecraft.world.level.levelgen.NoiseGeneratorSettings) settings.toMinecraft();
        Identifier id = (Identifier) key.resourceLocation();
        this.noiseSettingsRegistry.get().whileUnfrozen(raw -> {
            MappedRegistry<net.minecraft.world.level.levelgen.NoiseGeneratorSettings> registry = (MappedRegistry<@NonNull net.minecraft.world.level.levelgen.NoiseGeneratorSettings>) raw.toMinecraft();

            if (!registry.containsKey(id)) {
                Registry.register(registry, id, noiseSettings);
            }
        });
    }
}
