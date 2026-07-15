package dev.wyck.worldgen.feature.types;

import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.Lazy;
import dev.wyck.worldgen.feature.FeatureType;
import dev.wyck.worldgen.feature.configurations.FeatureConfiguration;
import net.kyori.adventure.key.Key;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.feature.Feature;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.NoSuchElementException;
import java.util.Optional;

// TODO: fixup imports
@NullMarked
@ApiStatus.Internal
public record ComposedConfiguredFeatureImpl(
    @Override Optional<ResourceKey> resourceKey,
    @Override FeatureType type,
    @Override FeatureConfiguration config
) implements ComposedConfiguredFeature {

    private static final Lazy<WyckRegistry> REGISTRY = WyckRegistry.lazy(RegistryId.CONFIGURED_FEATURE);

    @Override
    public Object toMinecraft() {
        Identifier featureId = type.resourceKey().asHandle();
        Feature<?> feature = BuiltInRegistries.FEATURE.getValue(featureId);

        if (feature == null) {
            throw new IllegalArgumentException("Unknown feature type: " + featureId);
        }
        net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration nmsConfig = config.asHandle();
        net.minecraft.world.level.levelgen.feature.ConfiguredFeature<?, ?> configured =
            new net.minecraft.world.level.levelgen.feature.ConfiguredFeature(feature, nmsConfig);
        return Holder.direct(configured);
    }

    @Override
    public ComposedConfiguredFeature register() {
        ResourceKey key = resourceKey.orElseThrow(() -> new NoSuchElementException("Cannot register a configured feature without a resource key."));
        REGISTRY.get().register(key, this);
        return this;
    }

    @Override
    public Key key() {
        return resourceKey.orElseThrow();
    }
}
