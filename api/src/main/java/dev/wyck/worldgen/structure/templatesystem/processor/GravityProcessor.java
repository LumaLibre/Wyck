package dev.wyck.worldgen.structure.templatesystem.processor;

import dev.wyck.worldgen.HeightmapType;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Drops every template block onto the terrain, measured from a heightmap. It is what terrain-matching pool elements use to follow the ground.
 *
 * @see <a href="https://minecraft.wiki/w/Processor_list">Processor list</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
@ApiStatus.Experimental
public interface GravityProcessor extends StructureProcessor {

    /**
     * The heightmap blocks are placed relative to.
     * @return the heightmap blocks are placed relative to
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    HeightmapType heightmap();

    /**
     * The offset from the heightmap blocks are placed at.
     * @return the offset from the heightmap blocks are placed at
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    int offset();

    /**
     * Creates a new processor.
     * @param heightmap the heightmap blocks are placed relative to
     * @param offset the offset from the heightmap blocks are placed at
     * @return a new processor
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static GravityProcessor of(HeightmapType heightmap, int offset) {
        record Holder() {
            static final ConstructWireProvider<GravityProcessor> WIRE =
                ConstructWireProvider.create("dev.wyck.worldgen.structure.templatesystem.processor.GravityProcessorImpl");
        }
        return Holder.WIRE.construct(heightmap, offset);
    }
}
