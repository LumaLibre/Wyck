package dev.wyck.worldgen.structure.templatesystem.types;

import dev.wyck.keys.ResourceKey;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record ReferencedProcessorListImpl(@Override ResourceKey key) implements ReferencedProcessorList {

    @Override
    public Object toMinecraft() {
        net.minecraft.core.HolderGetter<net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList> getter =
            BootstrapSafeMinecraftRegistries.getter(net.minecraft.core.registries.Registries.PROCESSOR_LIST);

        net.minecraft.resources.ResourceKey<net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList> resourceKey =
            net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.PROCESSOR_LIST, this.key.identifier());

        return getter.getOrThrow(resourceKey);
    }
}
