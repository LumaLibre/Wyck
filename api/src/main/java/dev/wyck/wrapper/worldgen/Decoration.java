//a85d4002ae1614585eb1558a06eb52e3
package dev.wyck.wrapper.worldgen;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.Generated;
import dev.wyck.wrapper.internal.KeyedEnumTranslator;
import dev.wyck.wrapper.internal.WrappedEnumerator;
import org.jspecify.annotations.NullMarked;

/**
 * Auto-generated. Do not modify!
 * Run ./gradlew generateSources to regenerate.
 * <p>
 * Wraps Minecraft's {@code GenerationStep.Decoration}.
 * </p>
 *
 * @since 2.3.0
 * @version 3.0.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("2.3.0")
@Generated("2026-07-15T07:01:58.779385Z")
public enum Decoration implements WrappedEnumerator<Decoration> {

    RAW_GENERATION("RAW_GENERATION"),
    LAKES("LAKES"),
    LOCAL_MODIFICATIONS("LOCAL_MODIFICATIONS"),
    UNDERGROUND_STRUCTURES("UNDERGROUND_STRUCTURES"),
    SURFACE_STRUCTURES("SURFACE_STRUCTURES"),
    STRONGHOLDS("STRONGHOLDS"),
    UNDERGROUND_ORES("UNDERGROUND_ORES"),
    UNDERGROUND_DECORATION("UNDERGROUND_DECORATION"),
    FLUID_SPRINGS("FLUID_SPRINGS"),
    VEGETAL_DECORATION("VEGETAL_DECORATION"),
    TOP_LAYER_MODIFICATION("TOP_LAYER_MODIFICATION");

    public static final KeyedEnumTranslator<Decoration> TRANSLATOR = KeyedEnumTranslator.byKey(Decoration::getKey, Decoration.values());

    private final String key;

    @AsOf("2.3.0")
    Decoration(String key) {
        this.key = key;
    }

    /**
     * The vanilla name for this Decoration value.
     * @return the vanilla key for this enum value
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public String getKey() {
        return this.key;
    }

    @Override
    @AsOf("2.3.0")
    public KeyedEnumTranslator<Decoration> translator() {
        return TRANSLATOR;
    }
}
