package dev.wyck.worldgen.feature.treedecorators;

import dev.wyck.worldgen.stateproviders.BlockStateProvider;
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
