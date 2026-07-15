package dev.wyck.worldgen.feature.treedecorators;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record TrunkVineDecoratorImpl() implements TrunkVineDecorator {
    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.levelgen.feature.treedecorators.TrunkVineDecorator.INSTANCE;
    }
}