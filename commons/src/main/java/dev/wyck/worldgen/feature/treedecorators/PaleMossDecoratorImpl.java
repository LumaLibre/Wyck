package dev.wyck.worldgen.feature.treedecorators;

import dev.wyck.worldgen.feature.treedecorators.PaleMossDecorator;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record PaleMossDecoratorImpl(
    @Override float leavesProbability,
    @Override float trunkProbability,
    @Override float groundProbability
) implements PaleMossDecorator {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.treedecorators.PaleMossDecorator(
            leavesProbability,
            trunkProbability,
            groundProbability
        );
    }
}