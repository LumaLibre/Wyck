package dev.wyck.decode.worldgen.feature;

import dev.wyck.decode.Decoders;
import dev.wyck.worldgen.feature.Feature;
import dev.wyck.worldgen.feature.FeatureType;
import dev.wyck.worldgen.feature.configurations.FeatureConfiguration;
import dev.wyck.worldgen.feature.custom.CustomFeatureBridge;
import dev.wyck.worldgen.feature.types.ComposedFeature;
import dev.wyck.worldgen.feature.types.CustomComposedFeature;
import dev.wyck.wrapper.decode.Decodable;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class FeatureDecoder implements Decodable<Feature, Object> {

    @Override
    public Feature decode(Object minecraftObject) {
        if (minecraftObject instanceof Holder<?> holder && holder.unwrapKey().isPresent()) {
            return Feature.reference(Decoders.referenceKey(holder));
        }

        net.minecraft.world.level.levelgen.feature.Feature feature = Decoders.value(minecraftObject);
        if (feature instanceof CustomFeatureBridge<?> bridge) {
            return custom(bridge);
        }

        return ComposedFeature.of(
            featureType(feature),
            FeatureConfiguration.decode(feature)
        );
    }

    @SuppressWarnings("unchecked")
    private static <C> CustomComposedFeature<C> custom(CustomFeatureBridge<?> bridge) {
        return CustomComposedFeature.of(
            (dev.wyck.worldgen.feature.custom.CustomFeature<C>) bridge.delegate(),
            (C) bridge.config()
        );
    }

    private static FeatureType featureType(net.minecraft.world.level.levelgen.feature.Feature feature) {
        dev.wyck.keys.ResourceKey typeKey = Decoders.registryKey(BuiltInRegistries.FEATURE_TYPE, feature.codec());
        return java.util.Arrays.stream(FeatureType.values())
            .filter(candidate -> candidate.key().equals(typeKey.value()))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unsupported feature type '" + typeKey + "'"));
    }
}
