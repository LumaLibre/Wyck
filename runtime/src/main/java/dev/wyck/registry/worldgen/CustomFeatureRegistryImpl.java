package dev.wyck.registry.worldgen;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.WireFactory;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.Lazy;
import dev.wyck.worldgen.feature.custom.CustomFeature;
import dev.wyck.worldgen.feature.types.CustomComposedFeature;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public final class CustomFeatureRegistryImpl implements CustomFeatureRegistry {

    private final Lazy<WyckRegistry> featureRegistry = WyckRegistry.lazy(RegistryId.FEATURE);

    @Override
    public void register(ResourceKey key, CustomFeature<?> feature) {
        registerTyped(key, feature);
    }

    private <C> void registerTyped(ResourceKey key, CustomFeature<C> feature) {
        this.featureRegistry.get().register(
            key,
            CustomComposedFeature.of(key, feature, feature.newConfig())
        );
    }
}
