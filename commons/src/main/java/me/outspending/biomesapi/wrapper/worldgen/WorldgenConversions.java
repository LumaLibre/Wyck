package me.outspending.biomesapi.wrapper.worldgen;

import me.outspending.biomesapi.annotations.AsOf;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.Block;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Internal conversion helpers shared across the worldgen carver factories.
 * @since 2.3.0
 */
@AsOf("2.3.0")
@ApiStatus.Internal
public final class WorldgenConversions {

    private WorldgenConversions() {
    }

    public static HolderSet<@NotNull Block> toBlockHolderSet(@NotNull Collection<Material> materials) {
        List<Holder<@NotNull Block>> holders = new ArrayList<>(materials.size());
        for (Material material : materials) {
            Block block = CraftMagicNumbers.getBlock(material);
            if (block == null) {
                throw new IllegalArgumentException("Material " + material + " does not map to a block");
            }

            holders.add(block.builtInRegistryHolder());
        }

        return HolderSet.direct(holders);
    }

    public static List<Block> toBlockList(@NotNull Collection<Material> materials) {
        List<Block> blocks = new ArrayList<>(materials.size());
        for (Material material : materials) {
            Block block = CraftMagicNumbers.getBlock(material);
            if (block == null) {
                throw new IllegalArgumentException("Material " + material + " does not map to a block");
            }

            blocks.add(block);
        }

        return blocks;
    }

    public static Vec3i toVec3i(@NotNull BlockVector vector) {
        return new Vec3i(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());
    }


    public static Direction toNmsDirection(@NotNull BlockFace face) {
        Direction direction = CraftBlock.blockFaceToNotch(face);
        if (direction == null) {
            throw new IllegalArgumentException("BlockFace " + face + " has no cardinal Direction equivalent");
        }

        return direction;
    }
}