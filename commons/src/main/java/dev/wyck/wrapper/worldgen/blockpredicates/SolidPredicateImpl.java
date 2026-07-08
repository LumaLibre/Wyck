package dev.wyck.wrapper.worldgen.blockpredicates;

import dev.wyck.util.WorldgenConversions;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
@SuppressWarnings("deprecation")
public record SolidPredicateImpl(
    @Override BlockVector offset
) implements SolidPredicate {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.solid(
            WorldgenConversions.toVec3i(offset)
        );
    }
}