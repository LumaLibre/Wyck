package me.outspending.biomesapi.keys;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.level.dimension.Dimension;
import org.jetbrains.annotations.ApiStatus;

/**
 * Objects kept in memory by this implementation of BiomesAPI.
 *
 * @since 2.4.0
 * @version 2.4.0
 * @author Jsinco
 */
@AsOf("2.4.0")
@ApiStatus.NonExtendable
public interface KeyChains {

    @AsOf("2.4.0")
    KeyChain<CustomBiome> BIOMES = KeyChain.mutable();

    @AsOf("2.4.0")
    KeyChain<Dimension> DIMENSIONS = KeyChain.mutable();

    /**
     * @return {@link #BIOMES}
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static KeyChain<CustomBiome> biomes() {
        return BIOMES;
    }

    /**
     * @return {@link #DIMENSIONS}
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static KeyChain<Dimension> dimensions() {
        return DIMENSIONS;
    }
}
