package me.outspending.biomesapi.wrapper.level.noise;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.wrapper.level.noise.chunk.LevelChunkGenerator;
import org.jspecify.annotations.Nullable;

/**
 * Represents an abstract noise function.
 *
 * @see LevelNoiseGeneratorSettings
 * @see LevelChunkGenerator
 * @since 2.4.0
 * @version 2.4.0
 * @author Jsinco
 */
@AsOf("2.4.0")
public interface Noise {

    ResourceKey OVERWORLD = ResourceKey.minecraft("overworld");
    ResourceKey NETHER = ResourceKey.minecraft("the_nether");
    ResourceKey END = ResourceKey.minecraft("end");
    ResourceKey FLOATING_ISLANDS = ResourceKey.minecraft("floating_islands");
    ResourceKey AMPLIFIED = ResourceKey.minecraft("amplified");
    ResourceKey CAVERNS = ResourceKey.minecraft("caves");

    /**
     * The key of the noise function.
     * @apiNote This may be null if the noise function is not registered or built-in.
     * @return the key of the noise function, if present
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    @Nullable ResourceKey key();

    /**
     * @return the default overworld noise function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Noise overworld() {
        return reference(OVERWORLD);
    }

    /**
     * @return the default nether noise function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Noise nether() {
        return reference(NETHER);
    }

    /**
     * @return the default end noise function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Noise end() {
        return reference(END);
    }

    /**
     * @return the floating islands noise function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Noise floatingIslands() {
        return reference(FLOATING_ISLANDS);
    }

    /**
     * @return the amplified noise function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Noise amplified() {
        return reference(AMPLIFIED);
    }

    /**
     * @return the caves noise function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Noise caves() {
        return reference(CAVERNS);
    }

    /**
     * @return a new noise generator settings builder
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static LevelNoiseGeneratorSettings.Builder builder() {
        return LevelNoiseGeneratorSettings.builder();
    }

    /**
     * Creates a reference to a noise function with the given key.
     * @param key the key of the noise function
     * @return a reference to the noise function with the given key
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Noise reference(ResourceKey key) {
        return new Reference(key);
    }

    /**
     * A reference to a noise function.
     * @param key the key of the noise function
     * @since 2.4.0
     * @version 2.4.0
     * @author Jsinco
     */
    @AsOf("2.4.0")
    record Reference(ResourceKey key) implements Noise {}
}
