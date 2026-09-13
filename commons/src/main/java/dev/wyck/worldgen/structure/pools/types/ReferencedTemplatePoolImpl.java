package dev.wyck.worldgen.structure.pools.types;

import dev.wyck.keys.ResourceKey;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record ReferencedTemplatePoolImpl(@Override ResourceKey key) implements ReferencedTemplatePool {

    @Override
    public Object toMinecraft() {
        net.minecraft.core.HolderGetter<net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool> getter =
            BootstrapSafeMinecraftRegistries.getter(net.minecraft.core.registries.Registries.TEMPLATE_POOL);

        net.minecraft.resources.ResourceKey<net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool> resourceKey =
            net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.TEMPLATE_POOL, this.key.identifier());

        return getter.getOrThrow(resourceKey);
    }
}
