package dev.wyck.wrapper.worldgen.blockpredicates;

import dev.wyck.wrapper.worldgen.WorldgenConversions;
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
        return net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.wouldSurvive(
            ((CraftBlockData) state).getState(),
            WorldgenConversions.toVec3i(offset)
        );
    }
}