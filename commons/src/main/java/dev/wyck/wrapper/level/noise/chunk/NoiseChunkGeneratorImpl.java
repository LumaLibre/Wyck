package dev.wyck.wrapper.level.noise.chunk;

import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.bootstrap.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.wrapper.level.BiomeSource;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record NoiseChunkGeneratorImpl(BiomeSource biomeSource, ResourceKey noiseSettings) implements ChunkGenerator {

    @Override
    public Object toMinecraft() {
        net.minecraft.world.level.biome.BiomeSource source = (net.minecraft.world.level.biome.BiomeSource) this.biomeSource.toMinecraft();
        Identifier id = (Identifier) this.noiseSettings.resourceLocation();
        Holder<NoiseGeneratorSettings> settings = BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.NOISE_SETTINGS)
            .getOrThrow(net.minecraft.resources.ResourceKey.create(Registries.NOISE_SETTINGS, id));
        return new NoiseBasedChunkGenerator(source, settings);
    }
}