package dev.wyck.decode.worldgen.feature;

import dev.wyck.decode.Decoders;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.feature.ConfiguredFeature;
import dev.wyck.worldgen.feature.FeatureType;
import dev.wyck.worldgen.feature.configurations.FeatureConfiguration;
import dev.wyck.worldgen.feature.custom.CustomFeatureBridge;
import dev.wyck.worldgen.feature.types.ComposedConfiguredFeature;
import dev.wyck.worldgen.feature.types.CustomComposedConfiguredFeature;
import dev.wyck.wrapper.decode.Decodable;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class ConfiguredFeatureDecoder implements Decodable<ConfiguredFeature, Object> {

    @Override
    public ConfiguredFeature decode(Object minecraftObject) {
        if (minecraftObject instanceof Holder<?> holder && holder.unwrapKey().isPresent()) {
            return ConfiguredFeature.reference(Decoders.referenceKey(holder));
        }

        net.minecraft.world.level.levelgen.feature.ConfiguredFeature<?, ?> configured = Decoders.value(minecraftObject);
        if (configured.feature() instanceof CustomFeatureBridge<?> bridge && configured.config() instanceof CustomFeatureBridge.Holder<?> config) {
            return custom(bridge, config);
        }

        return ComposedConfiguredFeature.of(
            featureType(configured.feature()),
            FeatureConfiguration.decode(configured)
        );
    }

    @SuppressWarnings("unchecked")
    private static <C> CustomComposedConfiguredFeature<C> custom(CustomFeatureBridge<C> bridge, CustomFeatureBridge.Holder<?> config) {
        return CustomComposedConfiguredFeature.of(
            Decoders.registryKey(BuiltInRegistries.FEATURE, bridge),
            bridge.delegate(),
            (C) config.config()
        );
    }

    private static FeatureType featureType(net.minecraft.world.level.levelgen.feature.Feature<?> feature) {
        try {
            return FeatureType.TRANSLATOR.fromNms(feature);
        } catch (IllegalArgumentException exception) {
            ResourceKey key = Decoders.registryKey(BuiltInRegistries.FEATURE, feature);
            throw new IllegalArgumentException("No Wyck feature type maps to '" + key + "'", exception);
        }
    }
}
