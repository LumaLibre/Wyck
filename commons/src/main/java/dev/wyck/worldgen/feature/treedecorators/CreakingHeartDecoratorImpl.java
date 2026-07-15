package dev.wyck.worldgen.feature.treedecorators;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record CreakingHeartDecoratorImpl(@Override float probability) implements CreakingHeartDecorator {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.treedecorators.CreakingHeartDecorator(probability);
    }
}