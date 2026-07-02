package dev.wyck.wrapper.worldgen.carver;

import dev.wyck.annotations.AsOf;
import org.jspecify.annotations.NullMarked;

/**
 * The vanilla world-carver algorithms that a configured carver can be built on.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public enum WorldCarverType {

    CAVE("cave"),
    NETHER_CAVE("nether_cave"),
    CANYON("canyon");

    // No translator because this isn't an enum in underlying Minecraft code

    private final String key;

    @AsOf("2.3.0")
    WorldCarverType(String key) {
        this.key = key;
    }

    /**
     * The vanilla registry key for this carver (e.g. "cave", "canyon").
     * @return the vanilla key for this carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public String key() {
        return this.key;
    }
}