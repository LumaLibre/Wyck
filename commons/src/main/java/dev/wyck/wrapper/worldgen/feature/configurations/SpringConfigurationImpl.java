package dev.wyck.wrapper.worldgen.feature.configurations;

import dev.wyck.wrapper.worldgen.WorldgenConversions;
import dev.wyck.wrapper.worldgen.material.FluidState;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Set;

@NullMarked
@ApiStatus.Internal
public record SpringConfigurationImpl(
    @Override FluidState state,
    @Override boolean requiresBlockBelow,
    @Override int rockCount,
    @Override int holeCount,
    @Override Set<Material> validBlocks
) implements SpringConfiguration {
    @Override
    public Object toMinecraft() {
        net.minecraft.core.HolderSet<net.minecraft.world.level.block.Block> blocks = WorldgenConversions.toBlockHolderSet(validBlocks);
        return new net.minecraft.world.level.levelgen.feature.configurations.SpringConfiguration(
            state.asHandle(),
            requiresBlockBelow,
            rockCount,
            holeCount,
            blocks
        );
    }
}