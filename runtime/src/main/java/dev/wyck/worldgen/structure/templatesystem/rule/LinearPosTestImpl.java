package dev.wyck.worldgen.structure.templatesystem.rule;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record LinearPosTestImpl(
    @Override float minChance,
    @Override float maxChance,
    @Override int minDist,
    @Override int maxDist
) implements LinearPosTest {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.structure.templatesystem.LinearPosTest(minChance, maxChance, minDist, maxDist);
    }
}
