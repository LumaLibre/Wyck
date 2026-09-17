package dev.wyck.worldgen.feature.types;

import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.Lazy;
import dev.wyck.util.DatapackPromotion;
import dev.wyck.worldgen.feature.custom.CustomFeature;
import dev.wyck.worldgen.feature.custom.CustomFeatureBridge;
import net.minecraft.core.Holder;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record CustomComposedFeatureImpl<C>(
    @Override Optional<ResourceKey> resourceKey,
    @Override CustomFeature<C> feature,
    @Override C config
) implements CustomComposedFeature<C> {

    private static final Lazy<WyckRegistry> REGISTRY = WyckRegistry.lazy(RegistryId.FEATURE);

    @Override
    public Object toMinecraft() {
        if (DatapackPromotion.isReferenceMode()) {
            return DatapackPromotion.current().reference(this, net.minecraft.core.registries.Registries.FEATURE);
        }
        CustomFeatureBridge<C> bridge = new CustomFeatureBridge<>(feature, config);
        if (DatapackPromotion.isCollectMode()) {
            DatapackPromotion.current().collectConfiguredFeature(this, bridge);
        }
        return Holder.direct(bridge);
    }

    @Override
    public CustomComposedFeature<C> register() {
        ResourceKey key = this.resourceKey.orElseThrow(() -> new IllegalStateException("Cannot register a configured feature without a resource key."));
        REGISTRY.get().register(key, this.toMinecraft());
        return this;
    }

    @Override
    public ResourceKey key() {
        return this.resourceKey.orElseThrow();
    }
}
