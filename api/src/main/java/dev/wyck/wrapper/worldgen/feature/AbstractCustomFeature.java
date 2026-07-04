package dev.wyck.wrapper.worldgen.feature;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import net.kyori.adventure.key.Keyed;
import org.jspecify.annotations.NullMarked;

/**
 * Interface for authored custom features.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface AbstractCustomFeature extends Keyed {
    @Override
    @AsOf("3.0.0")
    ResourceKey key();
}
