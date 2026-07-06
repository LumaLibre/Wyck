package dev.wyck.util;

import org.bukkit.Material;
import org.bukkit.block.BlockType;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class BukkitBootstrapUtilImpl implements BukkitBootstrapUtil {
    @Override
    public BlockData createBlockData(Material material) {
        BlockType type = material.asBlockType(); // TODO: Ehhh? Maybe use internals?
        return CraftBlockData.fromString(type, null);
    }
}
