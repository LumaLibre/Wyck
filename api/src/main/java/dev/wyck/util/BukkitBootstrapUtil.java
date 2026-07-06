package dev.wyck.util;

import dev.wyck.factory.WireProvider;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Utility interface for specific bukkit types that may not need to be wrapped,
 * but cannot safely be called during bootstrap.
 *
 * @apiNote Methods in this interface may be removed or changed without warning.
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@ApiStatus.Experimental
public interface BukkitBootstrapUtil {

    @ApiStatus.Internal
    WireProvider<BukkitBootstrapUtil> WIRE = WireProvider.create("dev.wyck.util.BukkitBootstrapUtilImpl");

    static BukkitBootstrapUtil util() {
        return WIRE.get();
    }

    BlockData createBlockData(Material material);
}
