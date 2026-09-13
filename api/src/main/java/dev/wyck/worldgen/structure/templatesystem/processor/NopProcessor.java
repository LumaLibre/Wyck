package dev.wyck.worldgen.structure.templatesystem.processor;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.WireProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Does nothing. The processor a capped processor or rule falls back to when there is nothing to apply.
 *
 * @see <a href="https://minecraft.wiki/w/Processor_list">Processor list</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
@ApiStatus.Experimental
public interface NopProcessor extends StructureProcessor {

    /**
     * The processor.
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    NopProcessor INSTANCE = instance();

    private static NopProcessor instance() {
        record Holder() {
            static final WireProvider<NopProcessor> WIRE =
                WireProvider.create("dev.wyck.worldgen.structure.templatesystem.processor.NopProcessorImpl");
        }
        return Holder.WIRE.get();
    }
}
