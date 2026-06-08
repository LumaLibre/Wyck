package me.outspending.biomesapi.registry.worldgen;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.registry.BuiltInRegistryLocks;
import me.outspending.biomesapi.wrapper.worldgen.feature.custom.CustomFeature;
import me.outspending.biomesapi.wrapper.worldgen.feature.custom.CustomFeatureBridge;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.feature.Feature;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public final class CustomFeatureRegistryImpl implements CustomFeatureRegistry {

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void register(BiomeResourceKey key, CustomFeature<?> feature) {
        CustomFeatureBridge<?> bridge = new CustomFeatureBridge(feature, feature.configSupplier());
        Identifier id = (Identifier) key.resourceLocation();

        registerBridge(id, bridge);
    }

    @AsOf("2.3.0")
    public static void registerBridge(Identifier id, CustomFeatureBridge<?> bridge) {
        MappedRegistry<Feature<?>> registry = (MappedRegistry<@NotNull Feature<?>>) BuiltInRegistries.FEATURE;
        BuiltInRegistryLocks.whileUnfrozen(registry, () -> Registry.register(BuiltInRegistries.FEATURE, id, bridge));
    }
}