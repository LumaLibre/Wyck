package dev.wyck.worldgen.noise.types;

import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.Lazy;
import dev.wyck.worldgen.noise.AquiferSettings;
import dev.wyck.worldgen.noise.NoiseRouter;
import dev.wyck.worldgen.noise.NoiseSettings;
import dev.wyck.worldgen.noise.SpawnTargetPoint;
import dev.wyck.worldgen.surface.SurfaceRule;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings.DebugFunctions;
import net.minecraft.world.level.levelgen.material.rule.MaterialRule;
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
    @Override List<SpawnTargetPoint> spawnTarget,
    @Override int seaLevel,
    @Override boolean disableMobGeneration,
    @Override Optional<AquiferSettings> aquifers,
    @Override boolean useLegacyRandomSource
) implements NoiseGeneratorSettings {
    private static final Lazy<WyckRegistry> REGISTRY = WyckRegistry.lazy(RegistryId.NOISE_SETTINGS);

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Object toMinecraft() {
        Optional minecraftAquifers = aquifers.map(AquiferSettings::toMinecraft);
        MaterialRule materialRule = surfaceRule.asHandle();
        return new net.minecraft.world.level.levelgen.NoiseGeneratorSettings(
            noiseSettings.asHandle(),
            ((CraftBlockData) defaultBlock).getState(),
            ((CraftBlockData) defaultFluid).getState(),
            noiseRouter.asHandle(),
            Holder.direct(materialRule),
            spawnTarget.stream().map(SpawnTargetPoint::<net.minecraft.world.level.levelgen.SpawnTargetPoint>asHandle).toList(),
            seaLevel,
            disableMobGeneration,
            minecraftAquifers,
            useLegacyRandomSource,
            DebugFunctions.EMPTY // TODO: implement
        );
    }

    @Override
    public NoiseGeneratorSettings register() {
        ResourceKey key = resourceKey.orElseThrow(() -> new NoSuchElementException("Noise settings must have a resource key"));
        REGISTRY.get().register(key, this);
        return this;
    }
}
