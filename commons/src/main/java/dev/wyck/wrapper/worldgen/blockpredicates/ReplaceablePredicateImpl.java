package dev.wyck.wrapper.worldgen.blockpredicates;

import dev.wyck.wrapper.worldgen.WorldgenConversions;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record ReplaceablePredicateImpl(
    @Override BlockVector offset
) implements ReplaceablePredicate {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.replaceable(
            WorldgenConversions.toVec3i(offset)
        );
    }
}