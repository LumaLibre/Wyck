package dev.wyck.worldgen.feature.treedecorators;

import dev.wyck.worldgen.feature.treedecorators.CocoaDecorator;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record CocoaDecoratorImpl(@Override float probability) implements CocoaDecorator {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.treedecorators.CocoaDecorator(probability);
    }
}