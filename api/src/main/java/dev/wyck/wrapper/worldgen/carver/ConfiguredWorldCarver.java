package dev.wyck.wrapper.worldgen.carver;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.internal.Wrapper;
import dev.wyck.wrapper.worldgen.carver.custom.CustomCarver;
import dev.wyck.wrapper.worldgen.carver.types.ComposedCarver;
import dev.wyck.wrapper.worldgen.carver.types.CustomComposedCarver;
import dev.wyck.wrapper.worldgen.carver.types.ReferencedCarver;
import net.kyori.adventure.key.Keyed;
import org.jspecify.annotations.NullMarked;

/**
 * Wraps Minecraft's ConfiguredWorldCarver,
 * a carver paired with its configuration or a reference to a carver already registered.
 *
 * @since 2.3.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public interface ConfiguredWorldCarver extends Wrapper, Keyed {

    /**
     * References a configured carver already registered under the given key.
     * @param key the registry key of the configured carver
     * @return a reference to the registered configured carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ReferencedCarver reference(ResourceKey key) {
        return ReferencedCarver.of(key);
    }

    /**
     * Authors a configured carver on the vanilla CAVE algorithm.
     * @param configuration the cave carver configuration
     * @return a configured cave carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ComposedCarver.Builder cave() {
        return ComposedCarver.cave();
    }

    /**
     * Authors a configured carver on the vanilla NETHER_CAVE algorithm.
     * @param configuration the cave carver configuration
     * @return a configured nether-cave carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ComposedCarver.Builder netherCave() {
        return ComposedCarver.netherCave();
    }

    /**
     * Authors a configured carver on the vanilla CANYON algorithm.
     * @param configuration the canyon carver configuration
     * @return a configured canyon carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ComposedCarver.Builder canyon() {
        return ComposedCarver.canyon();
    }

    /**
     * Composes a registered custom carver with a config instance.
     * @param customCarver the custom carver to compose
     * @param config the config instance to carve with
     * @return an authored configured carver
     * @param <C> the config type
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static <C> CustomComposedCarver<C> custom(CustomCarver<C> customCarver, C config) {
        return CustomComposedCarver.of(customCarver, config);
    }

    /**
     * Creates a new builder for a custom carver.
     * @return a new custom carver builder
     * @param <C> the config type
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static <C> CustomComposedCarver.Builder<C> custom() {
        return CustomComposedCarver.builder();
    }
}