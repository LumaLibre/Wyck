package dev.wyck.registry.worldgen;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.WireFactory;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.FrozenRegistry;
import dev.wyck.util.Lazy;
import dev.wyck.wrapper.worldgen.feature.custom.CustomFeature;
import dev.wyck.wrapper.worldgen.feature.custom.CustomFeatureBridge;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public final class CustomFeatureRegistryImpl implements CustomFeatureRegistry {

    private final Lazy<FrozenRegistry> featureRegistry = FrozenRegistry.lazy("worldgen/feature");

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void register(ResourceKey key, CustomFeature<?> feature) {
        CustomFeatureBridge<?> bridge = new CustomFeatureBridge(feature, feature.configSupplier());
        Identifier id = (Identifier) key.resourceLocation();

        this.featureRegistry.get().whileUnfrozen(() -> Registry.register(BuiltInRegistries.FEATURE, id, bridge));
    }
}