package me.outspending.biomesapi.wrapper.worldgen.feature;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.wrapper.NmsHandle;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

// TODO: Custom ConfiguredFeature implementations
/**
 * Wraps Minecraft's ConfiguredFeature, a feature paired with its configuration.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
@ApiStatus.Experimental
public sealed interface ConfiguredFeature extends NmsHandle permits ConfiguredFeature.Reference {

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.worldgen.feature.ConfiguredFeatureFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        Object toNms(ConfiguredFeature feature);
    }

    /**
     * References a configured feature already registered under the given key.
     * @param key the registry key of the configured feature
     * @return a reference to the registered configured feature
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ConfiguredFeature reference(BiomeResourceKey key) {
        return new Reference(key);
    }

    @Override
    @AsOf("2.3.0")
    default Object toMinecraft() {
        return WIRE.get().toNms(this);
    }

    /**
     * A reference to an already-registered configured feature.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    record Reference(BiomeResourceKey key) implements ConfiguredFeature {}
}