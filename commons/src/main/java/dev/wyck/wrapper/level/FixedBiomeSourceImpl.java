package dev.wyck.wrapper.level;

import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.bootstrap.util.BootstrapSafeMinecraftRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.FixedBiomeSource;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public record FixedBiomeSourceImpl(ResourceKey biome) implements BiomeSource {

    @Override
    public Object toMinecraft() {
        Registry<Biome> biomes = BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.BIOME);
        Identifier id = (Identifier) biome.resourceLocation();
        Holder<Biome> holder = biomes.getOrThrow(net.minecraft.resources.ResourceKey.create(Registries.BIOME, id));
        return new FixedBiomeSource(holder);
    }

    @Override
    public ResourceKey fixedBiome() {
        return this.biome;
    }

    @Override
    public @Nullable List<MultiNoiseEntry> entries() {
        return null;
    }

    @Override
    public @Nullable ResourceKey preset() {
        return null;
    }
}