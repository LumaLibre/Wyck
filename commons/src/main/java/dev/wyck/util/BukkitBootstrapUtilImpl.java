package dev.wyck.util;

import org.bukkit.Material;
import org.bukkit.block.BlockType;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jspecify.annotations.NullMarked;

@NullMarked
public record BukkitBootstrapUtilImpl() implements BukkitBootstrapUtil {
    @Override
    public BlockData createBlockData(Material material) {
        // TODO: dunno if this is bootstrap safe; might have to do this manually
        BlockType type = material.asBlockType();
        return CraftBlockData.fromString(type, null);
    }
}
