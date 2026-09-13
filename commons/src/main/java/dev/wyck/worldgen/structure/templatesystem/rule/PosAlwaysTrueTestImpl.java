package dev.wyck.worldgen.structure.templatesystem.rule;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record PosAlwaysTrueTestImpl() implements PosAlwaysTrueTest {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.structure.templatesystem.PosAlwaysTrueTest.INSTANCE;
    }
}
