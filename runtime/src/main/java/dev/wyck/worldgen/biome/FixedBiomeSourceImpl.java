package dev.wyck.worldgen.biome;

import dev.wyck.biome.Biome;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record FixedBiomeSourceImpl(@Override Biome biome) implements FixedBiomeSource {
    @Override
    public Object toMinecraft() {
        net.minecraft.core.Registry<net.minecraft.world.level.biome.Biome> biomeRegistry = BootstrapSafeMinecraftRegistries.mappedRegistry(net.minecraft.core.registries.Registries.BIOME);
        net.minecraft.resources.Identifier id = this.biome.resourceKey().identifier();
        net.minecraft.core.Holder<net.minecraft.world.level.biome.Biome> holder = biomeRegistry.getOrThrow(net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.BIOME, id));
        return new net.minecraft.world.level.biome.FixedBiomeSource(holder);
    }
}
