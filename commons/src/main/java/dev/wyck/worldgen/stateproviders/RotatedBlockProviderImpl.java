package dev.wyck.worldgen.stateproviders;

import com.google.common.base.Preconditions;
import net.minecraft.world.level.block.Block;
import org.bukkit.Material;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record RotatedBlockProviderImpl(@Override Material state) implements RotatedBlockProvider {
    @Override
    public Object toMinecraft() {
        Block block = CraftMagicNumbers.getBlock(state);
        Preconditions.checkNotNull(block, "material '" + state + "' does not map to a block");
        return new net.minecraft.world.level.levelgen.feature.stateproviders.RotatedBlockProvider(block);
    }
}
