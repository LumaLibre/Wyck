package dev.wyck.wrapper.worldgen.blockpredicates;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Always matches, independent of the block.
 *
 * @see <a href="https://minecraft.wiki/w/Block_predicate#true">Block predicate (true)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface TrueBlockPredicate extends BlockPredicate {

    @ApiStatus.Internal
    ConstructWireProvider<TrueBlockPredicate> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.blockpredicates.TrueBlockPredicateImpl");

    /** The singleton instance of {@link TrueBlockPredicate}. */
    @AsOf("3.0.0")
    TrueBlockPredicate INSTANCE = of();

    private static TrueBlockPredicate of() {
        return WIRE.construct();
    }
}
