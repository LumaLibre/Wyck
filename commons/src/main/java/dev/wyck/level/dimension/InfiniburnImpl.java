package dev.wyck.level.dimension;

import dev.wyck.keys.ResourceKey;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record InfiniburnImpl(ResourceKey tag) implements Infiniburn {
    @Override
    public Object toMinecraft() {
        throw new UnsupportedOperationException("Use #asTagKey() or #asHolderSet()");
    }

    public TagKey<Block> asTagKey() {
        Identifier id = (Identifier) this.tag.resourceLocation();
        return TagKey.create(Registries.BLOCK, id);
    }

    public HolderSet<Block> asHolderSet() {
        Registry<Block> registry = BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.BLOCK);
        return registry.getOrThrow(this.asTagKey());
    }
}
