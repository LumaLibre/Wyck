package dev.wyck.worldgen.feature.configurations;

import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record LayerConfigurationImpl(
    @Override int height,
    @Override BlockData state
) implements LayerConfiguration {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.configurations.LayerConfiguration(
            height,
            ((CraftBlockData) state).getState()
        );
    }
}