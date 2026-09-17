package dev.wyck.worldgen.blockpredicates;

import dev.wyck.util.WorldgenConversions;
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
        return new net.minecraft.world.level.levelgen.blockpredicates.ReplaceablePredicate(
            WorldgenConversions.toVec3i(offset)
        );
    }
}
