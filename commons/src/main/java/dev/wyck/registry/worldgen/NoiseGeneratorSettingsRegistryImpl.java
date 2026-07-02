package dev.wyck.registry.worldgen;

import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.FrozenRegistry;
import dev.wyck.util.Lazy;
import dev.wyck.wrapper.level.noise.NoiseGeneratorSettings;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class NoiseGeneratorSettingsRegistryImpl implements NoiseGeneratorSettingsRegistry {

    private final Lazy<FrozenRegistry> noiseSettingsRegistry = FrozenRegistry.lazy("worldgen/noise_settings");

    @Override
    @SuppressWarnings("unchecked")
    public void register(ResourceKey key, NoiseGeneratorSettings settings) {
        net.minecraft.world.level.levelgen.NoiseGeneratorSettings noiseSettings = (net.minecraft.world.level.levelgen.NoiseGeneratorSettings) settings.toMinecraft();
        Identifier id = (Identifier) key.resourceLocation();
        this.noiseSettingsRegistry.get().whileUnfrozen(raw -> {
            MappedRegistry<net.minecraft.world.level.levelgen.NoiseGeneratorSettings> registry = (MappedRegistry<net.minecraft.world.level.levelgen.NoiseGeneratorSettings>) raw.toMinecraft();

            if (!registry.containsKey(id)) {
                Registry.register(registry, id, noiseSettings);
            }
        });
    }
}
