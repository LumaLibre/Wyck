package dev.wyck.worldgen.surface.rule;

import dev.wyck.worldgen.surface.rule.BlockRuleSource;
import org.bukkit.Material;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record BlockRuleSourceImpl(
    @Override Material block
) implements BlockRuleSource {
    @Override
    public Object toMinecraft() {
        net.minecraft.world.level.block.Block nmsBlock = CraftMagicNumbers.getBlock(this.block);
        if (nmsBlock == null) {
            throw new IllegalArgumentException("Material " + this.block + " does not map to a block");
        }
        return net.minecraft.world.level.levelgen.SurfaceRules.state(nmsBlock.defaultBlockState());
    }
}
