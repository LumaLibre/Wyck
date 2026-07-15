package dev.wyck.worldgen.biome;

import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record TheEndBiomeSourceImpl() implements TheEndBiomeSource {
    @Override
    public Object toMinecraft() {
        net.minecraft.core.HolderGetter<net.minecraft.world.level.biome.Biome> biomes = BootstrapSafeMinecraftRegistries.getter(net.minecraft.core.registries.Registries.BIOME);
        return net.minecraft.world.level.biome.TheEndBiomeSource.create(biomes);
    }
}
