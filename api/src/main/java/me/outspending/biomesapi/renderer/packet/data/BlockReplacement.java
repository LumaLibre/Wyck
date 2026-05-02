package me.outspending.biomesapi.renderer.packet.data;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.AsOf;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A data class representing a block replacement rule.
 *
 * @version 2.1.0
 * @since 0.0.6
 * @author Jsinco
 */
@AsOf("0.0.6")
public record BlockReplacement(Material original, Material replacement) {

    /**
     * Returns the original block material.
     * @return the original block material
     */
    @AsOf("0.0.6")
    public Material originalBlock() {
        return original;
    }

    /**
     * Returns the replacement block material.
     * @return the replacement block material
     */
    @AsOf("0.0.6")
    public Material replacementBlock() {
        return replacement;
    }

    /**
     * Creates a new BlockReplacement instance.
     *
     * @param originalBlock   the original block material
     * @param replacementBlock the replacement block material
     * @return a new BlockReplacement instance
     */
    @AsOf("0.0.6")
    public static BlockReplacement of(Material originalBlock, Material replacementBlock) {
        return new BlockReplacement(originalBlock, replacementBlock);
    }

    /**
     * Creates a collection of BlockReplacement instances from a map of original and replacement block materials.
     * @param replacements the map of original and replacement block materials
     * @return a collection of BlockReplacement instances
     */
    @AsOf("2.1.0")
    public static Collection<BlockReplacement> of(Map<Material, Material> replacements) {
        return replacements.entrySet().stream().map(entry -> BlockReplacement.of(entry.getKey(), entry.getValue())).toList();
    }

    /**
     * Creates a collection of BlockReplacement instances from a collection of original block materials and a collection of replacement block materials.
     * @param originals the collection of original block materials
     * @param replacements the collection of replacement block materials
     * @return a collection of BlockReplacement instances
     */
    @AsOf("2.1.0")
    public static Collection<BlockReplacement> of(Collection<Material> originals, Collection<Material> replacements) {
        Preconditions.checkArgument(originals.size() == replacements.size(), "Originals and replacements must be of the same size.");

        List<BlockReplacement> result = new ArrayList<>(originals.size());
        Iterator<Material> originalIt = originals.iterator();
        Iterator<Material> replacementIt = replacements.iterator();
        while (originalIt.hasNext()) {
            result.add(BlockReplacement.of(originalIt.next(), replacementIt.next()));
        }
        return result;
    }

    /**
     * Creates a collection of BlockReplacement instances from an array of materials.
     * @param replacements the array of materials
     * @return a collection of BlockReplacement instances
     */
    @AsOf("2.1.0")
    public static Collection<BlockReplacement> of(Material... replacements) {
        Preconditions.checkArgument(replacements.length % 2 == 0, "Replacements must be in pairs.");

        List<BlockReplacement> result = new ArrayList<>(replacements.length / 2);
        for (int i = 0; i < replacements.length; i += 2) {
            result.add(BlockReplacement.of(replacements[i], replacements[i + 1]));
        }
        return result;
    }
}
