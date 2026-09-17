package dev.wyck.worldgen.function.misc;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.function.DensityFunction;
import dev.wyck.wrapper.Registerable;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * Samples at the current position using the noise algorithm used for end islands. Its minimum value is
 * {@code -0.84375} and its maximum value is {@code 0.5625}.
 *
 * @see <a href="https://minecraft.wiki/w/Density_function#end_islands">Density function - end_islands</a>
 * @since 3.0.0
 * @version 4.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface EndIslands extends DensityFunction, Registerable<EndIslands> {

    /**
     * Creates a new end islands density function.
     * @param resourceKey the resource key, or null
     * @return a new end islands density function
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static EndIslands of(@Nullable ResourceKey resourceKey) {
        record Holder() {
            static final ConstructWireProvider<EndIslands> WIRE = ConstructWireProvider.create(
                "dev.wyck.*?.worldgen.function.misc.EndIslandsImpl"
            );
        }
        return Holder.WIRE.construct(Optional.ofNullable(resourceKey));
    }

    /**
     * Creates a new end islands density function.
     * @return a new end islands density function
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static EndIslands of() {
        return of(null);
    }
}
