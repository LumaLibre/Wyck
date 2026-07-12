package dev.wyck.wrapper.worldgen.biome;

import dev.wyck.model.biome.Biome;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.util.WorldgenConversions;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Set;

@NullMarked
@ApiStatus.Internal
public record CheckerboardColumnBiomeSourceImpl(
    @Override Set<Biome> biomes,
    @Override int size
) implements CheckeredColumnBiomeSource {
    @Override
    public Object toMinecraft() {
        net.minecraft.core.Registry<net.minecraft.world.level.biome.Biome> biomeRegistry = BootstrapSafeMinecraftRegistries.mappedRegistry(net.minecraft.core.registries.Registries.BIOME);
        net.minecraft.core.HolderSet<net.minecraft.world.level.biome.Biome> allowedBiomes =
            WorldgenConversions.toHolderSet(biomes, (biome) -> {
                net.minecraft.resources.Identifier id = biome.resourceKey().identifier();
                return biomeRegistry.getOrThrow(net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.BIOME, id));
            });
        return new net.minecraft.world.level.biome.CheckerboardColumnBiomeSource(allowedBiomes, size);
    }
}
