package me.outspending.biomesapi.wrapper.worldgen.feature;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import me.outspending.biomesapi.wrapper.worldgen.feature.config.FeatureConfiguration;
import me.outspending.biomesapi.wrapper.worldgen.feature.custom.CustomFeature;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Wraps Minecraft's ConfiguredFeature.
 * <p>
 *     <ul>
 *         <li>{@link Reference} is a reference to an already-registered configured feature.</li>
 *         <li>{@link VanillaConfigured} is a configured feature authored from a vanilla feature type and configuration.</li>
 *         <li>{@link CustomConfigured} is a configured feature composed of a registered custom feature with a config instance. The feature must already be registered under featureKey via {@link CustomFeature#register(ResourceKey)}.</li>
 *     </ul>
 * </p>
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public sealed interface ConfiguredFeature extends NmsHandle, Keyed permits ConfiguredFeature.Reference, ConfiguredFeature.VanillaConfigured, ConfiguredFeature.CustomConfigured {

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
    static ConfiguredFeature reference(ResourceKey key) {
        return new Reference(key);
    }

    /**
     * Authors a configured feature from a vanilla feature type and configuration.
     * @param featureType the vanilla feature algorithm
     * @param configuration the vanilla configuration for it
     * @return an authored configured feature
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ConfiguredFeature custom(FeatureType featureType, FeatureConfiguration configuration) {
        return new VanillaConfigured(featureType.resourceKey(), configuration);
    }

    /**
     * Composes a registered custom feature with a config instance. The feature
     * must already be registered under featureKey via CustomFeature.register(...).
     * The same feature may be composed with different configs.
     * @param featureKey the key the custom feature was registered under
     * @param config the config instance to place with
     * @return an authored configured feature
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ConfiguredFeature customFeature(ResourceKey featureKey, Object config) {
        return new CustomConfigured(featureKey, config);
    }

    @Override
    @AsOf("2.3.0")
    default Object toMinecraft() {
        return WIRE.get().toNms(this);
    }

    /**
     * A reference to an already-registered configured feature.
     * @param key the key of the configured feature
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    record Reference(ResourceKey key) implements ConfiguredFeature {}

    /**
     * A configured feature authored from a vanilla feature type and configuration.
     * @param featureKey the vanilla feature type
     * @param configuration the vanilla configuration
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    record VanillaConfigured(ResourceKey featureKey, FeatureConfiguration configuration) implements ConfiguredFeature {
        @Override
        public Key key() {
            return this.featureKey;
        }
    }

    /**
     * A configured feature composed of a registered custom feature with a config instance.
     * The feature must already be registered under featureKey via {@link CustomFeature#register(ResourceKey)}.
     * @param featureKey the key the custom feature was registered under
     * @param config the config instance to place with
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    record CustomConfigured(ResourceKey featureKey, Object config) implements ConfiguredFeature {
        @Override
        public Key key() {
            return this.featureKey;
        }
    }
}