package dev.wyck.worldgen.structure.templatesystem.processor;

import dev.wyck.worldgen.valueproviders.IntProvider;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Applies another processor to at most a limited number of blocks, chosen at random. Trail ruins use it to bury a handful of suspicious gravel blocks.
 *
 * @see <a href="https://minecraft.wiki/w/Processor_list">Processor list</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
public interface CappedProcessor extends StructureProcessor {

    /**
     * The processor that is applied.
     * @return the processor that is applied
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    StructureProcessor delegate();

    /**
     * How many blocks the processor is applied to.
     * @return how many blocks the processor is applied to
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    IntProvider limit();

    /**
     * Creates a new processor.
     * @param delegate the processor that is applied
     * @param limit how many blocks the processor is applied to
     * @return a new processor
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static CappedProcessor of(StructureProcessor delegate, IntProvider limit) {
        record Holder() {
            static final ConstructWireProvider<CappedProcessor> WIRE =
                ConstructWireProvider.create("dev.wyck.worldgen.structure.templatesystem.processor.CappedProcessorImpl");
        }
        return Holder.WIRE.construct(delegate, limit);
    }
}
