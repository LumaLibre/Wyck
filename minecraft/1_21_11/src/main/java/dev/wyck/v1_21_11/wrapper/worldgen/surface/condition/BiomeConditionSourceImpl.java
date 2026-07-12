package dev.wyck.v1_21_11.wrapper.worldgen.surface.condition;

import dev.wyck.model.biome.Biome;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.wrapper.worldgen.surface.condition.BiomeConditionSource;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public record BiomeConditionSourceImpl(
    @Override List<Biome> targets
) implements BiomeConditionSource {
    @Override
    public Object toMinecraft() {
        @SuppressWarnings("unchecked")
        net.minecraft.resources.ResourceKey<net.minecraft.world.level.biome.Biome>[] keys = new net.minecraft.resources.ResourceKey[targets.size()];

        for (int i = 0; i < targets.size(); i++) {
            Biome biome = targets.get(i);
            keys[i] = net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.BIOME, biome.resourceKey().identifier());
        }

        return net.minecraft.world.level.levelgen.SurfaceRules.isBiome(keys);
    }
}
