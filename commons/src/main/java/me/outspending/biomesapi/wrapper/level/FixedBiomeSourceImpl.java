package me.outspending.biomesapi.wrapper.level;

import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.registry.bootstrap.util.BootstrapSafeMinecraftRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.FixedBiomeSource;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record FixedBiomeSourceImpl(ResourceKey biome) implements BiomeSource {

    @Override
    public Object toMinecraft() {
        Registry<Biome> biomes = BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.BIOME);
        Identifier id = (Identifier) biome.resourceLocation();
        Holder<Biome> holder = biomes.getOrThrow(net.minecraft.resources.ResourceKey.create(Registries.BIOME, id));
        // TODO: review this
        return new FixedBiomeSource(holder);
    }
}