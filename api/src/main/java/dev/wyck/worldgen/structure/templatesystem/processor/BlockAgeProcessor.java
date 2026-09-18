package dev.wyck.worldgen.structure.templatesystem.processor;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Ages stone bricks and similar blocks as they are placed, cracking, mossing and turning some to stairs or slabs. Ruined portals use it.
 *
 * @see <a href="https://minecraft.wiki/w/Processor_list">Processor list</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
public interface BlockAgeProcessor extends StructureProcessor {

    /**
     * The chance, from 0 to 1, of an aged block becoming mossy.
     * @return the chance, from 0 to 1, of an aged block becoming mossy
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    float mossiness();

    /**
     * Creates a new processor.
     * @param mossiness the chance, from 0 to 1, of an aged block becoming mossy
     * @return a new processor
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static BlockAgeProcessor of(float mossiness) {
        record Holder() {
            static final ConstructWireProvider<BlockAgeProcessor> WIRE =
                ConstructWireProvider.create("dev.wyck.worldgen.structure.templatesystem.processor.BlockAgeProcessorImpl");
        }
        return Holder.WIRE.construct(mossiness);
    }
}
