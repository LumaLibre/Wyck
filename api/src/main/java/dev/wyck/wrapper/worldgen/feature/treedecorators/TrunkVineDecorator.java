package dev.wyck.wrapper.worldgen.feature.treedecorators;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("3.0.0")
public interface TrunkVineDecorator extends TreeDecorator {

    @ApiStatus.Internal
    ConstructWireProvider<TrunkVineDecorator> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.feature.treedecorators.TrunkVineDecoratorImpl");

    @AsOf("3.0.0")
    static TrunkVineDecorator of() {
        return WIRE.construct();
    }
}