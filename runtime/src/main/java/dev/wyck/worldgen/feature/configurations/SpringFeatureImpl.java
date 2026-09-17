package dev.wyck.worldgen.feature.configurations;

import dev.wyck.util.WorldgenConversions;
import dev.wyck.worldgen.material.FluidState;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Set;

@NullMarked
@ApiStatus.Internal
public record SpringFeatureImpl(
    @Override FluidState state,
    @Override boolean requiresBlockBelow,
    @Override int rockCount,
    @Override int holeCount,
    @Override Set<Material> validBlocks
) implements SpringFeature {
    @Override
    public Object toMinecraft() {
        net.minecraft.core.HolderSet<net.minecraft.world.level.block.Block> blocks = WorldgenConversions.toBlockHolderSet(validBlocks);
        return new net.minecraft.world.level.levelgen.feature.SpringFeature(
            state.asHandle(),
            requiresBlockBelow,
            rockCount,
            holeCount,
            blocks
        );
    }
}