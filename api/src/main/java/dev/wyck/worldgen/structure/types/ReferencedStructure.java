package dev.wyck.worldgen.structure.types;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.structure.Structure;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * A reference to an existing registered structure.
 *
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
@ApiStatus.Experimental
public interface ReferencedStructure extends Structure {

    /**
     * Creates a new reference to the given structure.
     * @param key the key of the structure
     * @return a new reference to the given structure
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static ReferencedStructure of(ResourceKey key) {
        record Holder() {
            static final ConstructWireProvider<ReferencedStructure> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.structure.types.ReferencedStructureImpl");
        }
        return Holder.WIRE.construct(key);
    }
}
