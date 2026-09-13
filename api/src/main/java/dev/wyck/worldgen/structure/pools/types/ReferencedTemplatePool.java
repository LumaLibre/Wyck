package dev.wyck.worldgen.structure.pools.types;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.structure.pools.TemplatePool;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * A reference to an existing registered template pool.
 *
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
@ApiStatus.Experimental
public interface ReferencedTemplatePool extends TemplatePool {

    /**
     * Creates a new reference to the given template pool.
     * @param key the key of the template pool
     * @return a new reference to the given template pool
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static ReferencedTemplatePool of(ResourceKey key) {
        record Holder() {
            static final ConstructWireProvider<ReferencedTemplatePool> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.structure.pools.types.ReferencedTemplatePoolImpl");
        }
        return Holder.WIRE.construct(key);
    }
}
