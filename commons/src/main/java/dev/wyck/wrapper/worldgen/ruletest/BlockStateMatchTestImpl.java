package dev.wyck.wrapper.worldgen.ruletest;

import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record BlockStateMatchTestImpl(@Override BlockData blockState) implements BlockStateMatchTest {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.structure.templatesystem.BlockStateMatchTest(
            ((CraftBlockData) blockState).getState()
        );
    }
}
