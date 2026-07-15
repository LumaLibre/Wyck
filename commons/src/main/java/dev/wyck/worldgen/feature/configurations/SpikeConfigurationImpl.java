package dev.wyck.worldgen.feature.configurations;

import dev.wyck.worldgen.blockpredicates.BlockPredicate;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record SpikeConfigurationImpl(
    @Override BlockData state,
    @Override BlockPredicate canPlaceOn,
    @Override BlockPredicate canReplace
) implements SpikeConfiguration {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.configurations.SpikeConfiguration(
            ((CraftBlockData) state).getState(),
            canPlaceOn.asHandle(),
            canReplace.asHandle()
        );
    }
}