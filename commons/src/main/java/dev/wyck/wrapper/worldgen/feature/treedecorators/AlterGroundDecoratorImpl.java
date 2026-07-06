package dev.wyck.wrapper.worldgen.feature.treedecorators;

import dev.wyck.wrapper.worldgen.stateproviders.BlockStateProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record AlterGroundDecoratorImpl(@Override BlockStateProvider provider) implements AlterGroundDecorator {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.treedecorators.AlterGroundDecorator(
            provider.asHandle()
        );
    }
}
