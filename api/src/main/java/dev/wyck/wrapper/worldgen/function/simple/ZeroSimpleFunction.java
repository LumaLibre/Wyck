package dev.wyck.wrapper.worldgen.function.simple;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * A density function that always returns zero.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface ZeroSimpleFunction extends SimpleFunction {
    @ApiStatus.Internal
    ConstructWireProvider<ZeroSimpleFunction> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.function.simple.ZeroSimpleFunctionImpl");

    /** The zero-density function. */
    @AsOf("3.0.0")
    ZeroSimpleFunction INSTANCE = of();

    private static ZeroSimpleFunction of() {
        return WIRE.construct();
    }
}
