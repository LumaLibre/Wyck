package dev.wyck.wrapper.worldgen.material;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.internal.Wrapper;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("3.0.0")
public interface FluidState extends Wrapper {

    @ApiStatus.Internal
    ConstructWireProvider<FluidState> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.material.FluidStateImpl");

    @AsOf("3.0.0")
    FluidType fluid();

    @AsOf("3.0.0")
    static FluidState source(FluidType fluid) {
        Preconditions.checkNotNull(fluid, "fluid cannot be null");
        return WIRE.construct(fluid);
    }

    @AsOf("3.0.0")
    static FluidState empty() {
        return source(FluidType.EMPTY);
    }
}