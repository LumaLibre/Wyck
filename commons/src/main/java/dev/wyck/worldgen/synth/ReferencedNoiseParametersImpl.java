package dev.wyck.worldgen.synth;

import dev.wyck.keys.ResourceKey;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.worldgen.synth.ReferencedNoiseParameters;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record ReferencedNoiseParametersImpl(@Override ResourceKey key) implements ReferencedNoiseParameters {
    @Override
    public Object toMinecraft() {
        net.minecraft.resources.Identifier id = this.key.identifier();
        return BootstrapSafeMinecraftRegistries.mappedRegistry(net.minecraft.core.registries.Registries.NOISE)
            .getOrThrow(net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.NOISE, id))
            .value(); // don't return holders
    }

    @Override
    public Optional<ResourceKey> resourceKey() {
        return Optional.of(this.key);
    }
}
