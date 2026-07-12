package dev.wyck.wrapper.worldgen.noise.types;

import com.google.common.base.Preconditions;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.Lazy;
import dev.wyck.wrapper.internal.Wrapper;
import dev.wyck.wrapper.worldgen.climate.ClimatePoint;
import dev.wyck.wrapper.worldgen.noise.NoiseRouter;
import dev.wyck.wrapper.worldgen.noise.NoiseSettings;
import dev.wyck.wrapper.worldgen.surface.SurfaceRule;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record NoiseGeneratorSettingsImpl(
    @Override Optional<ResourceKey> resourceKey,
    @Override NoiseSettings noiseSettings,
    @Override BlockData defaultBlock,
    @Override BlockData defaultFluid,
    @Override NoiseRouter noiseRouter,
    @Override SurfaceRule surfaceRule,
    @Override List<ClimatePoint> spawnTarget,
    @Override int seaLevel,
    @Override boolean disableMobGeneration,
    @Override boolean aquifersEnabled,
    @Override boolean oreVeinsEnabled,
    @Override boolean useLegacyRandomSource
) implements NoiseGeneratorSettings {

    private static final Lazy<WyckRegistry> REGISTRY = WyckRegistry.lazy(RegistryId.NOISE_SETTINGS);

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.NoiseGeneratorSettings(
            noiseSettings.asHandle(),
            ((CraftBlockData) defaultBlock).getState(),
            ((CraftBlockData) defaultFluid).getState(),
            noiseRouter.asHandle(),
            surfaceRule.asHandle(),
            spawnTarget.stream().map(Wrapper::<net.minecraft.world.level.biome.Climate.ParameterPoint>asHandle).toList(),
            seaLevel,
            disableMobGeneration,
            aquifersEnabled,
            oreVeinsEnabled,
            useLegacyRandomSource
        );
    }

    @Override
    public NoiseGeneratorSettings register() {
        ResourceKey key = this.resourceKey.orElseThrow(() -> new NoSuchElementException("Noise settings must have a resource key"));
        REGISTRY.get().register(key, this);
        return this;
    }
}