package dev.wyck.wrapper.worldgen.feature.configurations;

import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record BlockStateConfigurationImpl(@Override BlockData state) implements BlockStateConfiguration {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration(
            ((CraftBlockData) state).getState()
        );
    }
}