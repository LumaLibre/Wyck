package dev.wyck.wrapper.worldgen.ruletest;

import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record RandomBlockStateMatchTestImpl(
    @Override BlockData blockState,
    @Override float probability
) implements RandomBlockStateMatchTest {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockStateMatchTest(
            ((CraftBlockData) blockState).getState(),
            probability
        );
    }
}
