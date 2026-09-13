package dev.wyck.worldgen.structure.templatesystem.processor;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.WireProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Swaps stone and cobblestone blocks for their blackstone equivalents, as ruined portals in the nether do.
 *
 * @see <a href="https://minecraft.wiki/w/Processor_list">Processor list</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
@ApiStatus.Experimental
public interface BlackstoneReplaceProcessor extends StructureProcessor {

    /**
     * The processor.
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    BlackstoneReplaceProcessor INSTANCE = instance();

    private static BlackstoneReplaceProcessor instance() {
        record Holder() {
            static final WireProvider<BlackstoneReplaceProcessor> WIRE = WireProvider.create("dev.wyck.worldgen.structure.templatesystem.processor.BlackstoneReplaceProcessorImpl");
        }
        return Holder.WIRE.get();
    }
}
