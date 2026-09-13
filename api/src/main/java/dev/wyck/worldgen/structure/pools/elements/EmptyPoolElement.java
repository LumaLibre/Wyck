package dev.wyck.worldgen.structure.pools.elements;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.WireProvider;
import dev.wyck.worldgen.structure.pools.PoolElement;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Places nothing. Weighted against other elements, it makes a jigsaw block sometimes stay empty.
 *
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
@ApiStatus.Experimental
public interface EmptyPoolElement extends PoolElement {

    /**
     * The empty element.
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    EmptyPoolElement INSTANCE = instance();

    private static EmptyPoolElement instance() {
        record Holder() {
            static final WireProvider<EmptyPoolElement> WIRE = WireProvider.create("dev.wyck.worldgen.structure.pools.elements.EmptyPoolElementImpl");
        }
        return Holder.WIRE.get();
    }
}
