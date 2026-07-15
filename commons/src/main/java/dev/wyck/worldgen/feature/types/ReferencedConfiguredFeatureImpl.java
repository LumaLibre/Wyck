package dev.wyck.worldgen.feature.types;

import dev.wyck.keys.ResourceKey;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.worldgen.feature.types.ReferencedConfiguredFeature;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

// TODO: fixup imports
@NullMarked
@ApiStatus.Internal
public record ReferencedConfiguredFeatureImpl(@Override ResourceKey key) implements ReferencedConfiguredFeature {
    @Override
    public Object toMinecraft() {
        HolderGetter<ConfiguredFeature<?, ?>> getter =
            BootstrapSafeMinecraftRegistries.getter(Registries.CONFIGURED_FEATURE);

        Identifier location = key.asHandle();
        net.minecraft.resources.ResourceKey<ConfiguredFeature<?, ?>> resourceKey =
            net.minecraft.resources.ResourceKey.create(Registries.CONFIGURED_FEATURE, location);
        return getter.getOrThrow(resourceKey);
    }

    @Override
    public Optional<ResourceKey> resourceKey() {
        return Optional.of(this.key);
    }
}
