package me.outspending.biomesapi.registry.worldgen;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.registry.internal.FrozenRegistry;
import me.outspending.biomesapi.util.Lazy;
import me.outspending.biomesapi.wrapper.worldgen.feature.custom.CustomFeature;
import me.outspending.biomesapi.wrapper.worldgen.feature.custom.CustomFeatureBridge;
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