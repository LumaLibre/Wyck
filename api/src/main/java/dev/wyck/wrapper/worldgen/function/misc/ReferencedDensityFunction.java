package dev.wyck.wrapper.worldgen.function.misc;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.worldgen.function.DensityFunction;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * A reference to a density function.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface ReferencedDensityFunction extends DensityFunction {

    @ApiStatus.Internal
    ConstructWireProvider<ReferencedDensityFunction> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.function.misc.ReferencedDensityFunctionImpl");

    /**
     * Creates a new {@link ReferencedDensityFunction} from the given key.
     * @param key the key of the density function
     * @return the density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ReferencedDensityFunction of(ResourceKey key) {
        return WIRE.construct(key);
    }
}
