package me.outspending.biomesapi.level;

import me.outspending.biomesapi.annotations.AsOf;
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents the persistence mode of a level stem.
 *
 * @since 2.4.0
 * @version 2.4.0
 * @author Jsinco
 */
@AsOf("2.4.0")
public enum StemPersistence {
    /**
     * The level stem lives only for the session and is injected straight into the world's dimensions.
     */
    TRANSIENT,
    /**
     * The level stem is also registered into the level_stem registry, so other registry callers can see it.
     */
    PERSISTENT
}
