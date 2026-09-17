package dev.wyck.worldgen.feature.configurations;

import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record IcebergFeatureImpl(@Override BlockData state) implements IcebergFeature {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.IcebergFeature(
            ((CraftBlockData) state).getState()
        );
    }
}
