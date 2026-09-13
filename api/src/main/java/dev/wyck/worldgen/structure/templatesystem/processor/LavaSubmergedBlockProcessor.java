package dev.wyck.worldgen.structure.templatesystem.processor;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.WireProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Keeps template blocks from overwriting lava, so structures that meet a lava sea stay flooded rather than walled off.
 *
 * @see <a href="https://minecraft.wiki/w/Processor_list">Processor list</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
@ApiStatus.Experimental
public interface LavaSubmergedBlockProcessor extends StructureProcessor {

    /**
     * The processor.
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    LavaSubmergedBlockProcessor INSTANCE = instance();

    private static LavaSubmergedBlockProcessor instance() {
        record Holder() {
            static final WireProvider<LavaSubmergedBlockProcessor> WIRE =
                WireProvider.create("dev.wyck.worldgen.structure.templatesystem.processor.LavaSubmergedBlockProcessorImpl");
        }
        return Holder.WIRE.get();
    }
}
