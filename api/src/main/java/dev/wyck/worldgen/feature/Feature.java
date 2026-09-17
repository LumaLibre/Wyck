package dev.wyck.worldgen.feature;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.worldgen.feature.configurations.FeatureConfiguration;
import dev.wyck.worldgen.feature.custom.CustomFeature;
import dev.wyck.worldgen.feature.types.ComposedFeature;
import dev.wyck.worldgen.feature.types.CustomComposedFeature;
import dev.wyck.worldgen.feature.types.ReferencedFeature;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

/**
 * A direct Minecraft feature or a reference to one already registered.
 * Features are added to biomes through placed features.
 *
 * @since 4.0.0
 * @version 4.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("4.0.0")
public interface Feature extends Wrapper, Keyed {

    /**
     * The resource key of the feature, if present.
     * @return the resource key of the feature, if present
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    Optional<ResourceKey> resourceKey();

    /**
     * Authors a feature from a vanilla feature type and its direct feature settings.
     * @param featureType the vanilla feature type
     * @param config the configuration
     * @return an authored feature
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    @ApiStatus.Obsolete
    static ComposedFeature of(FeatureType featureType, FeatureConfiguration config) {
        return ComposedFeature.of(featureType, config);
    }

    /**
     * Composes a custom feature with a config instance.
     * @param feature the custom feature to compose
     * @param config the config instance to place with
     * @return an authored feature
     * @param <C> the config type
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static <C> CustomComposedFeature<C> custom(CustomFeature<C> feature, C config) {
        return CustomComposedFeature.of(feature, config);
    }

    /**
     * Composes a registered custom feature with a config instance.
     * @param feature the custom feature to compose
     * @param config the config instance to place with
     * @return an authored feature
     * @param <C> the config type
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    @ApiStatus.Obsolete
    static <C> CustomComposedFeature<C> of(CustomFeature<C> feature, C config) {
        return CustomComposedFeature.of(feature, config);
    }

    /**
     * References a feature already registered under the given key.
     * @param key the registry key of the feature
     * @return a reference to the registered feature
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static ReferencedFeature reference(ResourceKey key) {
        return ReferencedFeature.of(key);
    }

    /**
     * Resolves this object's key in Minecraft's direct feature registry and decodes the registered value.
     * @return the decoded feature
     * @throws IllegalStateException if this object has no resource key
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    @ApiStatus.Experimental
    default Feature wrap() {
        ResourceKey key = resourceKey().orElseThrow(() -> new IllegalStateException("Cannot wrap a feature without a resource key"));
        Object minecraft = WyckRegistry.of(RegistryId.FEATURE).retrieveOrThrow(key);
        return decode(minecraft);
    }

    /**
     * Authors a feature from a vanilla feature type and its direct feature settings.
     * @return an authored feature
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static ComposedFeature.Builder composed() {
        return ComposedFeature.builder();
    }

    /**
     * Composes a registered custom feature with a config instance. The feature
     * @return an authored configured feature
     * @param <C> the config type
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static <C> CustomComposedFeature.Builder<C> custom() {
        return CustomComposedFeature.builder();
    }

    /**
     * Reads a keyed Minecraft feature holder into a wrapper.
     * @param minecraftFeature the feature holder to read
     * @return the wrapper for the feature
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static Feature decode(Object minecraftFeature) {
        record Holder() {
            static final Decoder<Feature> DECODER = Decoder.create("dev.wyck.decode.worldgen.feature.FeatureDecoder");
        }
        return Holder.DECODER.decode(minecraftFeature);
    }

}
