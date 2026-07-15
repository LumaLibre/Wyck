package dev.wyck.worldgen.blockpredicates;

import dev.wyck.util.WorldgenConversions;
import dev.wyck.worldgen.blockpredicates.HasSturdyFacePredicate;
import org.bukkit.block.BlockFace;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record HasSturdyFacePredicateImpl(
    @Override BlockVector offset,
    @Override BlockFace direction
) implements HasSturdyFacePredicate {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.hasSturdyFace(
            WorldgenConversions.toVec3i(offset),
            WorldgenConversions.toNmsDirection(direction)
        );
    }
}
