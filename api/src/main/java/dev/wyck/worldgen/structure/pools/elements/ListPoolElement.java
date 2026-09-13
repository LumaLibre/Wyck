package dev.wyck.worldgen.structure.pools.elements;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.structure.pools.PoolElement;
import dev.wyck.worldgen.structure.pools.Projection;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

/**
 * Places several elements at the same spot, in order. The first element decides the piece's size
 * and jigsaw connections; the rest are stamped over it.
 *
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
@ApiStatus.Experimental
public interface ListPoolElement extends PoolElement {

    /**
     * The elements placed, in order. Minecraft applies this element's projection to each of them.
     * @return the elements
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    List<PoolElement> elements();

    /**
     * Creates a new list element.
     * @param projection how the elements are fitted to the terrain
     * @param elements the elements to place, which must not be empty
     * @return a new list element
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static ListPoolElement of(Projection projection, List<PoolElement> elements) {
        Preconditions.checkArgument(!elements.isEmpty(), "elements must not be empty");
        record Holder() {
            static final ConstructWireProvider<ListPoolElement> WIRE =
                ConstructWireProvider.create("dev.wyck.worldgen.structure.pools.elements.ListPoolElementImpl");
        }
        return Holder.WIRE.construct(projection, List.copyOf(elements));
    }
}
