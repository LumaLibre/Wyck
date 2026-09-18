package dev.wyck.worldgen.structure.templatesystem.processor;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

import org.bukkit.Material;

import java.util.List;

/**
 * Skips the listed blocks when placing a template, so they never overwrite the world. Vanilla uses it to keep structure voids and air out of placed structures.
 *
 * @see <a href="https://minecraft.wiki/w/Processor_list">Processor list</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
public interface BlockIgnoreProcessor extends StructureProcessor {

    /**
     * The blocks that are not placed.
     * @return the blocks that are not placed
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    List<Material> blocks();

    /**
     * Creates a new processor.
     * @param blocks the blocks that are not placed
     * @return a new processor
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static BlockIgnoreProcessor of(List<Material> blocks) {
        record Holder() {
            static final ConstructWireProvider<BlockIgnoreProcessor> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.structure.templatesystem.processor.BlockIgnoreProcessorImpl");
        }
        return Holder.WIRE.construct(List.copyOf(blocks));
    }
}
