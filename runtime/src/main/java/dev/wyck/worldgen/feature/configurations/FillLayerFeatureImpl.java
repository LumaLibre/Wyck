package dev.wyck.worldgen.feature.configurations;

import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record FillLayerFeatureImpl(
    @Override int height,
    @Override BlockData state
) implements FillLayerFeature {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.FillLayerFeature(
            height,
            ((CraftBlockData) state).getState()
        );
    }
}