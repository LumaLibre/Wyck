package dev.wyck.wrapper.worldgen.carver.types;

import dev.wyck.keys.ResourceKey;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record ReferencedCarverImpl(@Override ResourceKey key) implements ReferencedCarver {

    @Override
    public Object toMinecraft() {
        net.minecraft.core.HolderGetter<net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?>> getter =
            BootstrapSafeMinecraftRegistries.getter(net.minecraft.core.registries.Registries.CONFIGURED_CARVER);

        net.minecraft.resources.Identifier id = this.key.identifier();
        net.minecraft.resources.ResourceKey<net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?>> resourceKey =
            net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.CONFIGURED_CARVER, id);

        return getter.getOrThrow(resourceKey);
    }
}
