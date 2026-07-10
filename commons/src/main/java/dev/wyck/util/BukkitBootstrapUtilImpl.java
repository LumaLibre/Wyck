package dev.wyck.util;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.data.BlockData;
import org.jspecify.annotations.NullMarked;

@NullMarked
public record BukkitBootstrapUtilImpl() implements BukkitBootstrapUtil {

    static {
        try {
            //Class.forName(Feature.class.getName()); - handled by: BootstrapSafeMinecraftRegistries
            Class.forName(Blocks.class.getName());
        } catch (ClassNotFoundException exception) {
            throw new IllegalStateException("Vanilla block registry could not be bootstrapped", exception);
        }
    }

    @Override
    public BlockData createBlockData(Material material) {
        NamespacedKey namespacedKey = material.getKey();
        ResourceKey<Block> resourceKey = ResourceKey.create(
            Registries.BLOCK,
            Identifier.fromNamespaceAndPath(namespacedKey.getNamespace(), namespacedKey.getKey())
        );

        return BuiltInRegistries.BLOCK.get(resourceKey)
            .map(Holder.Reference::value)
            .map(block -> block.defaultBlockState().asBlockData().clone())
            .orElseThrow(() -> new IllegalArgumentException("Material '" + material + "' is not a block"));
    }
}