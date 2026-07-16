package dev.wyck.level.dimension;

import dev.wyck.tags.TagSet;
import dev.wyck.tags.TagSetImpl;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record InfiniburnImpl(
    @Override TagSet<Material> blocks
) implements Infiniburn {
    @Override
    public Object toMinecraft() {
        return blocks.toMinecraft();
    }

    @ApiStatus.Obsolete
    public net.minecraft.tags.TagKey<net.minecraft.world.level.block.Block> asTagKey() {
        return ((TagSetImpl<@NonNull Material, net.minecraft.world.level.block.@NonNull Block>) blocks).asTagKey();
    }

    @ApiStatus.Obsolete
    public net.minecraft.core.HolderSet<net.minecraft.world.level.block.Block> asHolderSet() {
        return ((TagSetImpl<@NonNull Material, net.minecraft.world.level.block.@NonNull Block>) blocks).asHolderSet();
    }
}
