package dev.wyck.wrapper.worldgen.feature.treedecorators;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record LeaveVineDecoratorImpl(@Override float probability) implements LeaveVineDecorator {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator(probability);
    }
}