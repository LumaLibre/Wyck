package dev.wyck.worldgen.blockpredicates;

import dev.wyck.util.WorldgenConversions;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record InsideWorldBoundsPredicateImpl(
    @Override BlockVector offset
) implements InsideWorldBoundsPredicate {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.insideWorld(
            WorldgenConversions.toVec3i(offset)
        );
    }
}