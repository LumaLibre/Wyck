package dev.wyck.worldgen.structure.types;

import dev.wyck.keys.ResourceKey;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record ReferencedStructureImpl(@Override ResourceKey key) implements ReferencedStructure {

    @Override
    public Object toMinecraft() {
        net.minecraft.core.HolderGetter<net.minecraft.world.level.levelgen.structure.Structure> getter =
            BootstrapSafeMinecraftRegistries.getter(net.minecraft.core.registries.Registries.STRUCTURE);

        net.minecraft.resources.ResourceKey<net.minecraft.world.level.levelgen.structure.Structure> resourceKey =
            net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.STRUCTURE, this.key.identifier());

        return getter.getOrThrow(resourceKey);
    }
}
