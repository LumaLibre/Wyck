package dev.wyck.worldgen.ruletest;

import dev.wyck.worldgen.ruletest.AlwaysTrueTest;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record AlwaysTrueTestImpl() implements AlwaysTrueTest {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest.INSTANCE;
    }
}
