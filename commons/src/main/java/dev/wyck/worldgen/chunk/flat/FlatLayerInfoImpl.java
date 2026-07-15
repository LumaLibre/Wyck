package dev.wyck.worldgen.chunk.flat;

import dev.wyck.worldgen.chunk.flat.FlatLayerInfo;
import org.bukkit.Material;
import org.bukkit.craftbukkit.block.CraftBlockType;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record FlatLayerInfoImpl(
    @Override Material block,
    @Override int height
) implements FlatLayerInfo {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.flat.FlatLayerInfo(
            height,
            CraftBlockType.bukkitToMinecraft(block)
        );
    }
}
