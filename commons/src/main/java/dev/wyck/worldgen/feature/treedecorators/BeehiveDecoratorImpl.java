package dev.wyck.worldgen.feature.treedecorators;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record BeehiveDecoratorImpl(@Override float probability) implements BeehiveDecorator {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator(probability);
    }
}