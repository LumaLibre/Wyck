package dev.wyck.wrapper.worldgen.feature.treedecorators;

import dev.wyck.wrapper.worldgen.stateproviders.BlockStateProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record PlaceOnGroundDecoratorImpl(
    @Override int tries,
    @Override int radius,
    @Override int height,
    @Override BlockStateProvider blockStateProvider
) implements PlaceOnGroundDecorator {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.treedecorators.PlaceOnGroundDecorator(
            tries,
            radius,
            height,
            blockStateProvider.asHandle()
        );
    }
}