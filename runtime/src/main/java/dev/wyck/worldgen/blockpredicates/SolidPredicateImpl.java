package dev.wyck.worldgen.blockpredicates;

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
        return new net.minecraft.world.level.levelgen.blockpredicates.SolidPredicate(
            WorldgenConversions.toVec3i(offset)
        );
    }
}
