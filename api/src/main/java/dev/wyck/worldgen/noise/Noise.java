package dev.wyck.worldgen.noise;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.chunk.NoiseBasedChunkGenerator;
import dev.wyck.worldgen.noise.types.NoiseGeneratorSettings;
import dev.wyck.worldgen.noise.types.ReferencedNoise;
import dev.wyck.wrapper.Wrapper;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

/**
 * Represents an abstract noise function.
 *
 * @see NoiseGeneratorSettings
 * @see NoiseBasedChunkGenerator
 * @since 2.4.0
 * @version 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
public interface Noise extends Wrapper {

    Noise OVERWORLD = reference(ResourceKey.minecraft("overworld"));
    Noise NETHER = reference(ResourceKey.minecraft("nether"));
    Noise END = reference(ResourceKey.minecraft("end"));
    Noise FLOATING_ISLANDS = reference(ResourceKey.minecraft("floating_islands"));
    Noise AMPLIFIED = reference(ResourceKey.minecraft("amplified"));
    Noise CAVES = reference(ResourceKey.minecraft("caves"));
    Noise LARGE_BIOMES = reference(ResourceKey.minecraft("large_biomes"));

    /**
     * The key of the noise function.
     * @apiNote This may be null if the noise function is not registered or built-in.
     * @return the key of the noise function, if present
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    Optional<ResourceKey> resourceKey();

    /**
     * Creates a new noise generator settings builder.
     * @return a new noise generator settings builder
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static NoiseGeneratorSettings.Builder builder() {
        return NoiseGeneratorSettings.builder();
    }

    /**
     * Creates a reference to a noise function with the given key.
     * @param key the key of the noise function
     * @return a reference to the noise function with the given key
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Noise reference(ResourceKey key) {
        return ReferencedNoise.of(key);
    }


    /**
     * The overworld noise function.
     * @return the overworld noise function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Noise overworld() {
        return OVERWORLD;
    }

    /**
     * The nether noise function.
     * @return the nether noise function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Noise nether() {
        return NETHER;
    }

    /**
     * The end noise function.
     * @return the end noise function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Noise end() {
        return END;
    }

    /**
     * The floating islands noise function.
     * @return the floating islands noise function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Noise floatingIslands() {
        return FLOATING_ISLANDS;
    }

    /**
     * The amplified noise function.
     * @return the amplified noise function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Noise amplified() {
        return AMPLIFIED;
    }

    /**
     * The caves noise function.
     * @return the caves noise function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Noise caves() {
        return CAVES;
    }

    /**
     * The large biomes noise function.
     * @return the large biomes noise function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Noise largeBiomes() {
        return LARGE_BIOMES;
    }
}
