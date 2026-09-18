package dev.wyck.worldgen.structure.templatesystem.processor;

import dev.wyck.tags.TagSet;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

import org.bukkit.Material;

/**
 * Keeps the template from overwriting the listed world blocks. Vanilla protects the blocks in {@code #minecraft:features_cannot_replace} this way.
 *
 * @see <a href="https://minecraft.wiki/w/Processor_list">Processor list</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
public interface ProtectedBlocksProcessor extends StructureProcessor {

    /**
     * The world blocks the template may not overwrite.
     * @return the world blocks the template may not overwrite
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    TagSet<Material> cannotReplace();

    /**
     * Creates a new processor.
     * @param cannotReplace the world blocks the template may not overwrite
     * @return a new processor
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static ProtectedBlocksProcessor of(TagSet<Material> cannotReplace) {
        record Holder() {
            static final ConstructWireProvider<ProtectedBlocksProcessor> WIRE =
                ConstructWireProvider.create("dev.wyck.worldgen.structure.templatesystem.processor.ProtectedBlocksProcessorImpl");
        }
        return Holder.WIRE.construct(cannotReplace);
    }
}
