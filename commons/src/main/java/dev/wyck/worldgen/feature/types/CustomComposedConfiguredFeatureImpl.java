package dev.wyck.worldgen.feature.types;

import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.Lazy;
import dev.wyck.worldgen.feature.custom.CustomFeature;
import dev.wyck.worldgen.feature.custom.CustomFeatureBridge;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record CustomComposedConfiguredFeatureImpl<C>(
    @Override Optional<ResourceKey> resourceKey,
    @Override CustomFeature<C> feature,
    @Override C config
) implements CustomComposedConfiguredFeature<C> {

    private static final Lazy<WyckRegistry> REGISTRY = WyckRegistry.lazy(RegistryId.CONFIGURED_FEATURE);

    @Override
    public Object toMinecraft() {
        Identifier featureId = this.key().asHandle();
        net.minecraft.world.level.levelgen.feature.Feature feature = BuiltInRegistries.FEATURE.getValue(featureId);
        if (feature == null) {
            throw new IllegalArgumentException("Custom feature not registered: " + featureId + " (did you call #register()?)");
        }
        // full on custom decoration are backed by bridges
        CustomFeatureBridge.Holder<Object> holder = new CustomFeatureBridge.Holder<>(config);
        net.minecraft.world.level.levelgen.feature.ConfiguredFeature<?, ?> configured =
            new net.minecraft.world.level.levelgen.feature.ConfiguredFeature(feature, holder);
        return Holder.direct(configured);
    }

    @Override
    public CustomComposedConfiguredFeature<C> register() {
        ResourceKey key = this.resourceKey.orElseThrow(() -> new IllegalStateException("Cannot register a configured feature without a resource key."));
        REGISTRY.get().register(key, this);
        return this;
    }

    @Override
    public ResourceKey key() {
        return this.resourceKey.orElseThrow();
    }
}
