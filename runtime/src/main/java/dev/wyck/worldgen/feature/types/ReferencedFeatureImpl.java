package dev.wyck.worldgen.feature.types;

import dev.wyck.keys.ResourceKey;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.feature.Feature;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

// TODO: fixup imports
@NullMarked
@ApiStatus.Internal
public record ReferencedFeatureImpl(@Override ResourceKey key) implements ReferencedFeature {
    @Override
    public Object toMinecraft() {
        HolderGetter<Feature> getter = BootstrapSafeMinecraftRegistries.getter(Registries.FEATURE);

        Identifier location = key.asHandle();
        net.minecraft.resources.ResourceKey<Feature> resourceKey =
            net.minecraft.resources.ResourceKey.create(Registries.FEATURE, location);
        return getter.getOrThrow(resourceKey);
    }

    @Override
    public Optional<ResourceKey> resourceKey() {
        return Optional.of(this.key);
    }
}
