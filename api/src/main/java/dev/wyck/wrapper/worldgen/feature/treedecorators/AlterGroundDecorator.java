package dev.wyck.wrapper.worldgen.feature.treedecorators;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.worldgen.stateproviders.BlockStateProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("3.0.0")
public interface AlterGroundDecorator extends TreeDecorator {

    @ApiStatus.Internal
    ConstructWireProvider<AlterGroundDecorator> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.feature.treedecorators.AlterGroundDecoratorImpl");

    @AsOf("3.0.0")
    BlockStateProvider provider();

    @AsOf("3.0.0")
    static AlterGroundDecorator of(BlockStateProvider provider) {
        return WIRE.construct(provider);
    }
}
