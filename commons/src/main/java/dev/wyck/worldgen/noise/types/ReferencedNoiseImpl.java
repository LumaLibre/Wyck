package dev.wyck.worldgen.noise.types;

import dev.wyck.keys.ResourceKey;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.worldgen.noise.types.ReferencedNoise;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record ReferencedNoiseImpl(
    @Override ResourceKey key
) implements ReferencedNoise {
    @Override
    public Object toMinecraft() {
        net.minecraft.resources.Identifier id = this.key.identifier();
        return BootstrapSafeMinecraftRegistries.mappedRegistry(net.minecraft.core.registries.Registries.NOISE_SETTINGS)
            .getOrThrow(net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.NOISE_SETTINGS, id))
            .value(); // don't return holders
    }

    @Override
    public Optional<ResourceKey> resourceKey() {
        return Optional.of(this.key);
    }
}
