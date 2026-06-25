package me.outspending.biomesapi.keys;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.level.dimension.Dimension;
import me.outspending.biomesapi.wrapper.level.noise.function.DensityFunction;
import me.outspending.biomesapi.wrapper.level.spawner.KeyedSpawnTick;
import me.outspending.biomesapi.wrapper.worldgen.feature.ConfiguredFeature;
import me.outspending.biomesapi.wrapper.worldgen.placement.PlacedFeature;
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

    @AsOf("2.4.0")
    KeyChain<ConfiguredFeature> CONFIGURED_FEATURES = KeyChain.mutable();

    @AsOf("2.4.0")
    KeyChain<PlacedFeature> PLACED_FEATURES = KeyChain.mutable();

    @AsOf("2.4.0")
    KeyChain<DensityFunction> DENSITY_FUNCTIONS = KeyChain.mutable();

    @AsOf("2.4.0")
    KeyChain<ResourceKey> NOISE = KeyChain.mutable();

    @AsOf("2.4.0")
    KeyChain<KeyedSpawnTick> CUSTOM_LEVEL_SPAWNERS = KeyChain.mutable();

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

    /**
     * @return {@link #CONFIGURED_FEATURES}
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static KeyChain<ConfiguredFeature> configuredFeatures() {
        return CONFIGURED_FEATURES;
    }

    /**
     * @return {@link #PLACED_FEATURES}
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static KeyChain<PlacedFeature> placedFeatures() {
        return PLACED_FEATURES;
    }

    /**
     * @return {@link #DENSITY_FUNCTIONS}
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static KeyChain<DensityFunction> densityFunctions() {
        return DENSITY_FUNCTIONS;
    }

    /**
     * @return {@link #NOISE}
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static KeyChain<ResourceKey> noise() {
        return NOISE;
    }

    /**
     * @return {@link #CUSTOM_LEVEL_SPAWNERS}
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static KeyChain<KeyedSpawnTick> customLevelSpawners() {
        return CUSTOM_LEVEL_SPAWNERS;
    }
}
