package dev.wyck.worldgen.structure;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.KeyedEnumTranslator;
import dev.wyck.wrapper.WrappedEnumerator;
import org.jspecify.annotations.NullMarked;

/**
 * How a structure reshapes the terrain it lands on.
 *
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
public enum TerrainAdaptation implements WrappedEnumerator<TerrainAdaptation> {
    NONE("NONE"),
    BURY("BURY"),
    BEARD_THIN("BEARD_THIN"),
    BEARD_BOX("BEARD_BOX"),
    ENCAPSULATE("ENCAPSULATE");

    public static final KeyedEnumTranslator<TerrainAdaptation> TRANSLATOR = KeyedEnumTranslator.byKey(TerrainAdaptation::getKey, TerrainAdaptation.values());

    private final String key;

    @AsOf("3.4.0")
    TerrainAdaptation(String key) {
        this.key = key;
    }

    @AsOf("3.4.0")
    @Override
    public KeyedEnumTranslator<TerrainAdaptation> translator() {
        return TRANSLATOR;
    }

    /**
     * The vanilla name for this TerrainAdaptation
     * @return the vanilla key for this enum value
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    public String getKey() {
        return this.key;
    }
}
