package dev.wyck.worldgen.structure.pools;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.KeyedEnumTranslator;
import dev.wyck.wrapper.WrappedEnumerator;
import org.jspecify.annotations.NullMarked;

/**
 * How a pool element is fitted to the terrain it is placed on.
 *
 * @see <a href="https://minecraft.wiki/w/Template_pool">Template pool</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
public enum Projection implements WrappedEnumerator<Projection> {
    /** The element follows the terrain, each column dropped onto the world surface. Used for paths. */
    TERRAIN_MATCHING("TERRAIN_MATCHING"),

    /** The element is placed exactly as saved. Used for buildings. */
    RIGID("RIGID");

    public static final KeyedEnumTranslator<Projection> TRANSLATOR = KeyedEnumTranslator.byKey(Projection::getKey, Projection.values());

    private final String key;

    @AsOf("3.4.0")
    Projection(String key) {
        this.key = key;
    }

    @AsOf("3.4.0")
    @Override
    public KeyedEnumTranslator<Projection> translator() {
        return TRANSLATOR;
    }

    /**
     * The vanilla name for this Projection
     * @return the vanilla key for this enum value
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    public String getKey() {
        return this.key;
    }
}
