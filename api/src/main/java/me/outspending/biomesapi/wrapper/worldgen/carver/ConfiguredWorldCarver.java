package me.outspending.biomesapi.wrapper.worldgen.carver;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.wrapper.NmsHandle;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Wraps Minecraft's ConfiguredWorldCarver, a carver paired with its configuration.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public sealed interface ConfiguredWorldCarver extends NmsHandle permits ConfiguredWorldCarver.Reference, ConfiguredWorldCarver.Custom {

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.worldgen.carver.ConfiguredWorldCarverFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        Object toNms(ConfiguredWorldCarver carver);
    }

    /**
     * References a configured carver already registered under the given key.
     * @param key the registry key of the configured carver
     * @return a reference to the registered configured carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ConfiguredWorldCarver reference(BiomeResourceKey key) {
        return new Reference(key);
    }

    /**
     * Authors a configured carver on the vanilla CAVE algorithm.
     * @param configuration the cave carver configuration
     * @return a configured cave carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ConfiguredWorldCarver cave(CaveCarverConfiguration configuration) {
        return new Custom(WorldCarverType.CAVE, configuration);
    }

    /**
     * Authors a configured carver on the vanilla NETHER_CAVE algorithm.
     * @param configuration the cave carver configuration
     * @return a configured nether-cave carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ConfiguredWorldCarver netherCave(CaveCarverConfiguration configuration) {
        return new Custom(WorldCarverType.NETHER_CAVE, configuration);
    }

    /**
     * Authors a configured carver on the vanilla CANYON algorithm.
     * @param configuration the canyon carver configuration
     * @return a configured canyon carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ConfiguredWorldCarver canyon(CanyonCarverConfiguration configuration) {
        return new Custom(WorldCarverType.CANYON, configuration);
    }

    @Override
    @AsOf("2.3.0")
    default Object toMinecraft() {
        return WIRE.get().toNms(this);
    }

    /**
     * A reference to an already-registered configured carver.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    record Reference(BiomeResourceKey key) implements ConfiguredWorldCarver {}

    /**
     * A configured carver authored from a vanilla algorithm and a custom config.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    record Custom(WorldCarverType type, CarverConfiguration config) implements ConfiguredWorldCarver {}
}