package dev.wyck.worldgen.structure.templatesystem.processor;

import com.google.common.base.Preconditions;
import dev.wyck.tags.TagSet;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import org.bukkit.Material;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * Drops template blocks at random, leaving the structure worn. Only the rottable blocks are affected when they are given.
 *
 * @see <a href="https://minecraft.wiki/w/Processor_list">Processor list</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
@ApiStatus.Experimental
public interface BlockRotProcessor extends StructureProcessor {

    /**
     * The blocks that may be dropped, every block when absent.
     * @return the blocks that may be dropped, every block when absent
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    Optional<TagSet<Material>> rottableBlocks();

    /**
     * The chance, from 0 to 1, that a block is kept.
     * @return the chance, from 0 to 1, that a block is kept
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    float integrity();

    /**
     * Creates a new processor.
     * @param rottableBlocks the blocks that may be dropped, every block when absent
     * @param integrity the chance, from 0 to 1, that a block is kept
     * @return a new processor
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static BlockRotProcessor of(@Nullable TagSet<Material> rottableBlocks, float integrity) {
        Preconditions.checkArgument(integrity >= 0 && integrity <= 1, "integrity must be between 0 and 1, got %s", integrity);
        record Holder() {
            static final ConstructWireProvider<BlockRotProcessor> WIRE =
                ConstructWireProvider.create("dev.wyck.worldgen.structure.templatesystem.processor.BlockRotProcessorImpl");
        }
        return Holder.WIRE.construct(Optional.ofNullable(rottableBlocks), integrity);
    }

    /**
     * Creates a new processor that may drop any block.
     * @param integrity the chance, from 0 to 1, that a block is kept
     * @return a new processor
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static BlockRotProcessor of(float integrity) {
        return of(null, integrity);
    }
}
