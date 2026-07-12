package dev.wyck.wrapper.worldgen.ruletest;

import org.bukkit.Material;
import org.bukkit.craftbukkit.block.CraftBlockType;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record RandomBlockMatchTestImpl(
    @Override Material block,
    @Override float probability
) implements RandomBlockMatchTest {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest(
            CraftBlockType.bukkitToMinecraft(block),
            probability
        );
    }
}
