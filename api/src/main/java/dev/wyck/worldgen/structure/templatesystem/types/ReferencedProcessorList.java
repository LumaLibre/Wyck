package dev.wyck.worldgen.structure.templatesystem.types;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.structure.templatesystem.ProcessorList;
import org.jspecify.annotations.NullMarked;

/**
 * A reference to an existing registered processor list.
 *
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
public interface ReferencedProcessorList extends ProcessorList {

    /**
     * The resource key of the referenced processor list.
     * @return the resource key of the processor list
     * @since 3.4.0
     */
    @Override
    @AsOf("3.4.0")
    ResourceKey key();

    /**
     * Creates a new reference to the given processor list.
     * @param key the key of the processor list
     * @return a new reference to the given processor list
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static ReferencedProcessorList of(ResourceKey key) {
        record Holder() {
            static final ConstructWireProvider<ReferencedProcessorList> WIRE =
                ConstructWireProvider.create("dev.wyck.worldgen.structure.templatesystem.types.ReferencedProcessorListImpl");
        }
        return Holder.WIRE.construct(key);
    }
}
