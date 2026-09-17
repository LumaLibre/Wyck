package dev.wyck.worldgen.feature.configurations;

import dev.wyck.worldgen.blockpredicates.BlockPredicate;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record HugeFungusFeatureImpl(
    BlockData validBaseState,
    BlockData stemState,
    BlockData hatState,
    BlockData decorState,
    BlockPredicate replaceableBlocks,
    boolean planted
) implements HugeFungusFeature {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.HugeFungusFeature(
            ((CraftBlockData) validBaseState).getState(),
            ((CraftBlockData) stemState).getState(),
            ((CraftBlockData) hatState).getState(),
            ((CraftBlockData) decorState).getState(),
            replaceableBlocks.asHandle(),
            planted
        );
    }
}
