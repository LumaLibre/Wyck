package dev.wyck.worldgen.structure.templatesystem.processor;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.WireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Replaces jigsaw blocks with the block they are set to turn into once placed. Every pool element runs it.
 *
 * @see <a href="https://minecraft.wiki/w/Processor_list">Processor list</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
public interface JigsawReplacementProcessor extends StructureProcessor {

    /**
     * The processor.
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    JigsawReplacementProcessor INSTANCE = instance();

    private static JigsawReplacementProcessor instance() {
        record Holder() {
            static final WireProvider<JigsawReplacementProcessor> WIRE =
                WireProvider.create("dev.wyck.worldgen.structure.templatesystem.processor.JigsawReplacementProcessorImpl");
        }
        return Holder.WIRE.get();
    }
}
