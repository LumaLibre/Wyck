package dev.wyck.wrapper.worldgen.chunk;

import dev.wyck.keys.ResourceKey;
import dev.wyck.model.biome.Biome;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.wrapper.worldgen.biome.BiomeSource;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class DebugLevelSourceImpl extends ChunkGeneratorImpl implements DebugLevelSource {

    private final Biome biome;

    public DebugLevelSourceImpl(Biome biome) {
        super(BiomeSource.fixed(biome));
        this.biome = biome;
    }

    @Override
    public Biome biome() {
        return this.biome;
    }

    @Override
    public Object toMinecraft() {
        ResourceKey key = this.biome.resourceKey();
        net.minecraft.resources.ResourceKey<net.minecraft.world.level.biome.Biome> biomeKey =
            net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.BIOME, key.identifier());

        net.minecraft.core.HolderGetter<net.minecraft.world.level.biome.Biome> biomes = BootstrapSafeMinecraftRegistries.getter(net.minecraft.core.registries.Registries.BIOME);
        net.minecraft.core.Holder.Reference<net.minecraft.world.level.biome.Biome> biomeHolder = biomes.getOrThrow(biomeKey);

        return new net.minecraft.world.level.levelgen.DebugLevelSource(biomeHolder);
    }
}
