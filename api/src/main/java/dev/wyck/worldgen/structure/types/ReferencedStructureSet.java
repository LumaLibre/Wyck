package dev.wyck.worldgen.structure.types;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.structure.StructureSet;
import dev.wyck.worldgen.structure.StructureSets;
import org.jspecify.annotations.NullMarked;

/**
 * A reference to an existing registered structure set.
 *
 * @see StructureSets
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
public interface ReferencedStructureSet extends StructureSet {

    /**
     * Creates a new reference to the given structure set.
     * @param key the key of the structure set
     * @return a new reference to the given structure set
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static ReferencedStructureSet of(ResourceKey key) {
        record Holder() {
            static final ConstructWireProvider<ReferencedStructureSet> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.structure.types.ReferencedStructureSetImpl");
        }
        return Holder.WIRE.construct(key);
    }
}
