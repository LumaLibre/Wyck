package me.outspending.biomesapi.packet.data;

import me.outspending.biomesapi.annotations.AsOf;
import org.bukkit.Material;

/**
 * A data class representing a block replacement rule.
 *
 * @version 0.0.4
 * @since 0.0.4
 * @author Jsinco
 */
@AsOf("0.0.4")
public record BlockReplacement(Material originalBlock, Material replacementBlock) {
}
