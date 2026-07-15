package dev.wyck.worldgen.feature.configurations;

import dev.wyck.worldgen.blockpredicates.BlockPredicate;
import dev.wyck.worldgen.feature.configurations.BlockBlobConfiguration;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record BlockBlobConfigurationImpl(
    @Override BlockData state,
    @Override BlockPredicate canPlaceOn
) implements BlockBlobConfiguration {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.configurations.BlockBlobConfiguration(
            ((CraftBlockData) state).getState(),
            canPlaceOn.asHandle()
        );
    }
}
