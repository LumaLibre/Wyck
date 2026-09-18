package dev.wyck.worldgen.structure.templatesystem.processor;

import dev.wyck.annotations.AsOf;
import dev.wyck.tags.TagSet;
import dev.wyck.worldgen.HeightmapType;
import dev.wyck.worldgen.valueproviders.IntProvider;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import org.bukkit.Material;
import org.jspecify.annotations.NullMarked;

import java.util.List;

/**
 * Wraps Minecraft's StructureProcessor: a step every block of a structure template passes through as
 * the template is placed, which can replace, age, drop or keep it.
 *
 * @see <a href="https://minecraft.wiki/w/Processor_list">Processor list</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
public interface StructureProcessor extends Wrapper {

    /**
     * Swaps stone and cobblestone blocks for their blackstone equivalents.
     * @return the blackstone-replace processor
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static BlackstoneReplaceProcessor blackstoneReplace() {
        return BlackstoneReplaceProcessor.INSTANCE;
    }

    /**
     * Ages stone bricks and similar blocks as they are placed.
     * @param mossiness the chance, from 0 to 1, of an aged block becoming mossy
     * @return a new block-age processor
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static BlockAgeProcessor blockAge(float mossiness) {
        return BlockAgeProcessor.of(mossiness);
    }

    /**
     * Skips the given blocks when placing a template.
     * @param blocks the blocks that are not placed
     * @return a new block-ignore processor
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static BlockIgnoreProcessor blockIgnore(Material... blocks) {
        return BlockIgnoreProcessor.of(List.of(blocks));
    }

    /**
     * Drops template blocks at random.
     * @param integrity the chance, from 0 to 1, that a block is kept
     * @return a new block-rot processor
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static BlockRotProcessor blockRot(float integrity) {
        return BlockRotProcessor.of(integrity);
    }

    /**
     * Drops template blocks from the given set at random.
     * @param rottableBlocks the blocks that may be dropped
     * @param integrity the chance, from 0 to 1, that a block is kept
     * @return a new block-rot processor
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static BlockRotProcessor blockRot(TagSet<Material> rottableBlocks, float integrity) {
        return BlockRotProcessor.of(rottableBlocks, integrity);
    }

    /**
     * Applies another processor to at most a limited number of blocks.
     * @param delegate the processor that is applied
     * @param limit how many blocks the processor is applied to
     * @return a new capped processor
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static CappedProcessor capped(StructureProcessor delegate, IntProvider limit) {
        return CappedProcessor.of(delegate, limit);
    }

    /**
     * Drops every template block onto the terrain.
     * @param heightmap the heightmap blocks are placed relative to
     * @param offset the offset from the heightmap blocks are placed at
     * @return a new gravity processor
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static GravityProcessor gravity(HeightmapType heightmap, int offset) {
        return GravityProcessor.of(heightmap, offset);
    }

    /**
     * Replaces jigsaw blocks with the block they are set to turn into.
     * @return the jigsaw-replacement processor
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static JigsawReplacementProcessor jigsawReplacement() {
        return JigsawReplacementProcessor.INSTANCE;
    }

    /**
     * Keeps template blocks from overwriting lava.
     * @return the lava-submerged-block processor
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static LavaSubmergedBlockProcessor lavaSubmergedBlock() {
        return LavaSubmergedBlockProcessor.INSTANCE;
    }

    /**
     * Does nothing.
     * @return the no-op processor
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static NopProcessor nop() {
        return NopProcessor.INSTANCE;
    }

    /**
     * Keeps the template from overwriting the given world blocks.
     * @param cannotReplace the world blocks the template may not overwrite
     * @return a new protected-blocks processor
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static ProtectedBlocksProcessor protectedBlocks(TagSet<Material> cannotReplace) {
        return ProtectedBlocksProcessor.of(cannotReplace);
    }

    /**
     * Replaces template blocks according to rules, the first matching rule winning.
     * @param rules the rules tried in order
     * @return a new rule processor
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static RuleProcessor rule(ProcessorRule... rules) {
        return RuleProcessor.of(List.of(rules));
    }

    /**
     * Reads a Minecraft structure processor.
     * @param minecraftProcessor the processor to read
     * @return the decoded processor
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static StructureProcessor decode(Object minecraftProcessor) {
        record Holder() {
            static final Decoder<StructureProcessor> DECODER = Decoder.create("dev.wyck.decode.worldgen.structure.templatesystem.StructureProcessorDecoders");
        }
        return Holder.DECODER.decode(minecraftProcessor);
    }
}
