package dev.wyck.worldgen.structure.types;

import dev.wyck.keys.ResourceKey;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record ReferencedStructureSetImpl(@Override ResourceKey key) implements ReferencedStructureSet {

    @Override
    public Object toMinecraft() {
        net.minecraft.core.HolderGetter<net.minecraft.world.level.levelgen.structure.StructureSet> getter =
            BootstrapSafeMinecraftRegistries.getter(net.minecraft.core.registries.Registries.STRUCTURE_SET);

        net.minecraft.resources.ResourceKey<net.minecraft.world.level.levelgen.structure.StructureSet> resourceKey =
            net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.STRUCTURE_SET, this.key.identifier());

        return getter.getOrThrow(resourceKey);
    }
}
