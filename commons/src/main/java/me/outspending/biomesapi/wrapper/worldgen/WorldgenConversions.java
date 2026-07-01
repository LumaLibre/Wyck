package me.outspending.biomesapi.wrapper.worldgen;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.util.internal.InternalReflectUtil;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Internal conversion helpers shared across the worldgen carver factories.
 * @since 2.3.0
 */
@NullMarked
@AsOf("2.3.0")
@ApiStatus.Internal
public final class WorldgenConversions {

    private WorldgenConversions() {
    }

    public static HolderSet<Block> toBlockHolderSet(Collection<Material> materials) {
        List<Holder<Block>> holders = new ArrayList<>(materials.size());
        for (Material material : materials) {
            Block block = CraftMagicNumbers.getBlock(material);
            if (block == null) {
                throw new IllegalArgumentException("Material " + material + " does not map to a block");
            }

            holders.add(block.builtInRegistryHolder());
        }

        return HolderSet.direct(holders);
    }

    public static List<Material> fromBlockHolderSet(HolderSet<Block> holderSet) {
        return holderSet.stream()
            .map(blockHolder -> blockHolder.value().defaultBlockState().getBukkitMaterial())
            .toList();
    }

    public static List<Block> toBlockList(Collection<Material> materials) {
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

    public static Vec3i toVec3i(BlockVector vector) {
        return new Vec3i(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());
    }

    public static BlockVector toBlockVector(Vec3i vec3i) {
        return new BlockVector(vec3i.getX(), vec3i.getY(), vec3i.getZ());
    }


    public static Direction toNmsDirection(BlockFace face) {
        Direction direction = CraftBlock.blockFaceToNotch(face);
        if (direction == null) {
            throw new IllegalArgumentException("BlockFace " + face + " has no cardinal Direction equivalent");
        }

        return direction;
    }

    public static Tag<Material> toBukkitMaterialTag(TagKey<Block> tagKey) {
        Identifier location = tagKey.location();
        NamespacedKey key = new NamespacedKey(location.getNamespace(), location.getPath());

        Tag<Material> bukkitTag = Bukkit.getTag(Tag.REGISTRY_BLOCKS, key, Material.class);
        if (bukkitTag == null) {
            throw new IllegalArgumentException("Block tag " + key + " is not registered with Bukkit");
        }
        return bukkitTag;
    }
}