package dev.wyck.worldgen.blockpredicates;

import dev.wyck.util.WorldgenConversions;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record WouldSurvivePredicateImpl(
    @Override BlockVector offset,
    @Override BlockData state
) implements WouldSurvivePredicate {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.blockpredicates.WouldSurvivePredicate(
            WorldgenConversions.toVec3i(offset),
            ((CraftBlockData) state).getState()
        );
    }
}
