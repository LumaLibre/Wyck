package dev.wyck.wrapper.worldgen.blockpredicates;

import dev.wyck.wrapper.worldgen.FluidType;
import dev.wyck.wrapper.worldgen.WorldgenConversions;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public record MatchingFluidsPredicateImpl(
    @Override BlockVector offset,
    @Override List<FluidType> fluids
) implements MatchingFluidsPredicate {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.matchesFluids(
            WorldgenConversions.toVec3i(offset),
            this.fluids.stream().map(it -> it.<net.minecraft.world.level.material.Fluid>toNms()).toList()
        );
    }
}