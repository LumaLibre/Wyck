package me.outspending.biomesapi.wrapper.worldgen.feature;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.registry.bootstrap.util.BootstrapSafeMinecraftRegistries;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public final class ConfiguredFeatureFactoryImpl implements ConfiguredFeature.Factory {

    @Override
    public Object toNms(ConfiguredFeature feature) {
        return switch (feature) {
            case ConfiguredFeature.Reference reference -> resolveReference(reference);
            // TODO: Custom ConfiguredFeature
        };
    }

    private Object resolveReference(ConfiguredFeature.Reference reference) {
        HolderGetter<net.minecraft.world.level.levelgen.feature.ConfiguredFeature<?, ?>> getter =
            BootstrapSafeMinecraftRegistries.getter(Registries.CONFIGURED_FEATURE);

        Identifier location = (Identifier) reference.key().resourceLocation();
        ResourceKey<net.minecraft.world.level.levelgen.feature.ConfiguredFeature<?, ?>> resourceKey =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, location);

        return getter.getOrThrow(resourceKey);
    }
}