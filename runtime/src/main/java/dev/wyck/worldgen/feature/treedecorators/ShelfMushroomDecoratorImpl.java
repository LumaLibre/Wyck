package dev.wyck.worldgen.feature.treedecorators;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record ShelfMushroomDecoratorImpl(@Override float probability) implements ShelfMushroomDecorator {

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.treedecorators.ShelfMushroomDecorator(probability);
    }
}
