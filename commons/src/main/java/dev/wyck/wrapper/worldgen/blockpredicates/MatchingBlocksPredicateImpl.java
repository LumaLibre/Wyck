package dev.wyck.wrapper.worldgen.blockpredicates;

import dev.wyck.util.WorldgenConversions;
import org.bukkit.Material;
import org.bukkit.craftbukkit.block.CraftBlockType;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public record MatchingBlocksPredicateImpl(
    @Override BlockVector offset,
    @Override List<Material> blocks
) implements MatchingBlocksPredicate {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.matchesBlocks(
            WorldgenConversions.toVec3i(offset),
            this.blocks.stream().map(CraftBlockType::bukkitToMinecraft).toList()
        );
    }
}