package me.outspending.biomesapi.wrapper.worldgen.feature;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.registry.bootstrap.util.BootstrapSafeMinecraftRegistries;
import me.outspending.biomesapi.wrapper.worldgen.feature.custom.CustomFeatureBridge;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public final class ConfiguredFeatureFactoryImpl implements ConfiguredFeature.Factory {

    @Override
    public Object toNms(ConfiguredFeature feature) {
        return switch (feature) {
            case ConfiguredFeature.Reference reference -> resolveReference(reference);
            case ConfiguredFeature.VanillaConfigured vanilla -> resolveVanilla(vanilla);
            case ConfiguredFeature.CustomConfigured custom -> resolveCustom(custom);
        };
    }

    @Override
    @SuppressWarnings("unchecked")
    public ConfiguredFeature fromMinecraft(Object nms) {
        Holder<net.minecraft.world.level.levelgen.feature.ConfiguredFeature<?, ?>> holder =
            (Holder<net.minecraft.world.level.levelgen.feature.ConfiguredFeature<?, ?>>) nms;

        Optional<ResourceKey<net.minecraft.world.level.levelgen.feature.ConfiguredFeature<?, ?>>> key = holder.unwrapKey();
        return key.map(this::reverseReference).orElseGet(() -> reverseDirect(holder.value()));

    }

    private Object resolveReference(ConfiguredFeature.Reference reference) {
        HolderGetter<net.minecraft.world.level.levelgen.feature.ConfiguredFeature<?, ?>> getter =
            BootstrapSafeMinecraftRegistries.getter(Registries.CONFIGURED_FEATURE);

        Identifier location = (Identifier) reference.key().resourceLocation();
        ResourceKey<net.minecraft.world.level.levelgen.feature.ConfiguredFeature<?, ?>> resourceKey =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, location);
        return getter.getOrThrow(resourceKey);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Object resolveVanilla(ConfiguredFeature.VanillaConfigured vanilla) {
        Identifier featureId = (Identifier) vanilla.featureKey().resourceLocation();
        Feature feature = BuiltInRegistries.FEATURE.getValue(featureId);

        if (feature == null) {
            throw new IllegalArgumentException("Unknown feature type: " + featureId);
        }
        FeatureConfiguration config = (FeatureConfiguration) vanilla.configuration().toMinecraft();
        net.minecraft.world.level.levelgen.feature.ConfiguredFeature<?, ?> configured =
            new net.minecraft.world.level.levelgen.feature.ConfiguredFeature(feature, config);
        return Holder.direct(configured);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Object resolveCustom(ConfiguredFeature.CustomConfigured custom) {
        Identifier featureId = (Identifier) custom.featureKey().resourceLocation();
        net.minecraft.world.level.levelgen.feature.Feature feature = BuiltInRegistries.FEATURE.getValue(featureId);
        if (feature == null) {
            throw new IllegalArgumentException("Custom feature not registered: " + featureId + " (did you call #register() before building?)");
        }
        // full on custom features are backed by bridges
        CustomFeatureBridge.Holder<Object> holder = new CustomFeatureBridge.Holder<>(custom.config());
        net.minecraft.world.level.levelgen.feature.ConfiguredFeature<?, ?> configured =
            new net.minecraft.world.level.levelgen.feature.ConfiguredFeature(feature, holder);
        return Holder.direct(configured);
    }

    private ConfiguredFeature reverseReference(ResourceKey<net.minecraft.world.level.levelgen.feature.ConfiguredFeature<?, ?>> key) {
        Identifier location = key.identifier();
        me.outspending.biomesapi.keys.ResourceKey wrapperKey = me.outspending.biomesapi.keys.ResourceKey.of(location.getNamespace(), location.getPath());
        return ConfiguredFeature.reference(wrapperKey);
    }

    private ConfiguredFeature reverseDirect(net.minecraft.world.level.levelgen.feature.ConfiguredFeature<?, ?> configured) {
        net.minecraft.world.level.levelgen.feature.Feature<?> feature = configured.feature();
        Identifier featureId = BuiltInRegistries.FEATURE.getKey(feature);
        if (featureId == null) {
            throw new IllegalArgumentException("feature is not registered in BuiltInRegistries.FEATURE: " + feature);
        }
        me.outspending.biomesapi.keys.ResourceKey featureKey = me.outspending.biomesapi.keys.ResourceKey.of(featureId.getNamespace(), featureId.getPath());

        FeatureConfiguration nmsConfig = configured.config();

        if (nmsConfig instanceof CustomFeatureBridge.Holder<?>(Object config)) {
            return new ConfiguredFeature.CustomConfigured(featureKey, config);
        }

        me.outspending.biomesapi.wrapper.worldgen.feature.config.FeatureConfiguration configuration =
            me.outspending.biomesapi.wrapper.worldgen.feature.config.FeatureConfiguration.fromMinecraft(nmsConfig);
        return new ConfiguredFeature.VanillaConfigured(featureKey, configuration);
    }
}