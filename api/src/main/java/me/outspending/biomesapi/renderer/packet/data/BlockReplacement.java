package me.outspending.biomesapi.renderer.packet.data;

import me.outspending.biomesapi.annotations.AsOf;
import org.bukkit.Material;

/**
 * A data class representing a block replacement rule.
 *
 * @version 0.0.6
 * @since 0.0.6
 * @author Jsinco
 */
@AsOf("0.0.6")
public record BlockReplacement(Material originalBlock, Material replacementBlock) {

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
}
