package dev.wyck.model.level;

import dev.wyck.annotations.AsOf;

/**
 * Represents the persistence mode of a level stem.
 *
 * @since 2.4.0
 * @version 2.4.0
 * @author Jsinco
 */
@AsOf("2.4.0")
public enum StemPersistence {
    /** The level stem lives only for the session and is injected straight into the world's dimensions. */
    TRANSIENT,
    /** The level stem is also registered into the dimension registry, so other registry callers can see it. */
    PERSISTENT
}
