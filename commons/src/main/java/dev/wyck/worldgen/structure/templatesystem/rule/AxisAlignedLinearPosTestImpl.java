package dev.wyck.worldgen.structure.templatesystem.rule;

import org.bukkit.Axis;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record AxisAlignedLinearPosTestImpl(
    @Override float minChance,
    @Override float maxChance,
    @Override int minDist,
    @Override int maxDist,
    @Override Axis axis
) implements AxisAlignedLinearPosTest {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.structure.templatesystem.AxisAlignedLinearPosTest(
            minChance, maxChance, minDist, maxDist,
            // Bukkit and Minecraft name their axes identically
            net.minecraft.core.Direction.Axis.valueOf(axis.name())
        );
    }
}
