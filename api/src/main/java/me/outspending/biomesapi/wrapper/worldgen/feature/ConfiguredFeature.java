package me.outspending.biomesapi.wrapper.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.registry.worldgen.CustomFeatureRegistry;
import me.outspending.biomesapi.serialization.StringRepresentable;
import me.outspending.biomesapi.wrapper.internal.NmsDecoder;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import me.outspending.biomesapi.wrapper.worldgen.feature.config.FeatureConfiguration;
import me.outspending.biomesapi.wrapper.worldgen.feature.custom.CustomFeature;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.stream.Stream;

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
public sealed interface ConfiguredFeature extends NmsHandle, Keyed, StringRepresentable permits ConfiguredFeature.Reference, ConfiguredFeature.VanillaConfigured, ConfiguredFeature.CustomConfigured {

    Codec<ConfiguredFeature> CODEC = Codec.STRING.dispatch(
        "type",
        ConfiguredFeature::type,
        type -> switch (type) {
            case "reference" -> Reference.MAP_CODEC;
            case "vanilla" -> VanillaConfigured.MAP_CODEC;
            case "custom" -> CustomConfigured.MAP_CODEC;
            default -> throw new IllegalStateException("unknown configured feature: " + type);
        }
    );

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.worldgen.feature.ConfiguredFeatureFactoryImpl");

    @ApiStatus.Internal
    interface Factory extends NmsDecoder<ConfiguredFeature> {
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

    @AsOf("2.4.0")
    static ConfiguredFeature fromMinecraft(Object nms) {
        return WIRE.get().fromMinecraft(nms);
    }

    @ApiStatus.Internal
    private static <T extends ConfiguredFeature> MapCodec<T> unsupported(String reason) {
        return new MapCodec<>() {
            @Override
            public <O> DataResult<T> decode(DynamicOps<O> ops, MapLike<O> input) {
                return DataResult.error(() -> reason);
            }
            @Override
            public <O> RecordBuilder<O> encode(T value, DynamicOps<O> ops, RecordBuilder<O> prefix) {
                return prefix.withErrorsFrom(DataResult.error(() -> reason));
            }
            @Override
            public <O> Stream<O> keys(DynamicOps<O> ops) {
                return Stream.empty();
            }
        };
    }

    /**
     * A reference to an already-registered configured feature.
     * @param key the key of the configured feature
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    record Reference(ResourceKey key) implements ConfiguredFeature {
        public static final MapCodec<Reference> MAP_CODEC =
            ResourceKey.CODEC.fieldOf("key").xmap(Reference::new, Reference::key);
    }

    /**
     * A configured feature authored from a vanilla feature type and configuration.
     * @param featureKey the vanilla feature type
     * @param configuration the vanilla configuration
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    record VanillaConfigured(ResourceKey featureKey, FeatureConfiguration configuration) implements ConfiguredFeature {
        public static final MapCodec<VanillaConfigured> MAP_CODEC = unsupported("vanilla configured features are not supported; use a reference instead");
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
    record CustomConfigured(ResourceKey featureKey, Object config) implements ConfiguredFeature { // TODO: Should not be Object for config
        @SuppressWarnings({"unchecked", "rawtypes"})
        public static final MapCodec<CustomConfigured> MAP_CODEC = ResourceKey.CODEC.dispatchMap(
            "feature_key",
            CustomConfigured::featureKey,
            key -> {
                CustomFeature feature = CustomFeatureRegistry.registry().get(key);
                Codec codec = feature.configCodec();
                if (codec == null) {
                    return unsupported("custom feature " + key + " has no config codec; pass one to its constructor");
                }
                return ((Codec<Object>) codec).fieldOf("config")
                    .xmap(cfg -> new CustomConfigured(key, cfg), CustomConfigured::config);
            }
        );

        @Override
        public Key key() {
            return this.featureKey;
        }
    }
}