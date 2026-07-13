package dev.wyck.wrapper.environment;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.Generated;
import dev.wyck.wrapper.internal.KeyedEnumTranslator;
import dev.wyck.wrapper.internal.WrappedEnumerator;
import org.jspecify.annotations.NullMarked;

/**
 * Auto-generated. Do not modify!
 * Run ./gradlew generateSources to regenerate.
 * <p>
 * This enum represents the grass color modifier for a biome in Minecraft.
 * It includes three values: NONE, SWAMP, and DARK_FOREST, which correspond to the GrassColorModifier values in the BiomeSpecialEffects class in the Minecraft code.
 * Each enum value carries a vanilla key, retrievable via {@link #getKey()}, which the impl module translates to the underlying NMS value.
 * </p>
 *
 * @since 0.0.24
 * @version 3.0.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("0.0.24")
@Generated("2026-07-13T07:21:53.856781Z")
public enum GrassColorModifier implements WrappedEnumerator<GrassColorModifier> {

    NONE("none"),
    DARK_FOREST("dark_forest"),
    SWAMP("swamp");

    public static final KeyedEnumTranslator<GrassColorModifier> TRANSLATOR = KeyedEnumTranslator.byKey(GrassColorModifier::getKey, GrassColorModifier.values());

    private final String key;

    @AsOf("0.0.24")
    GrassColorModifier(String key) {
        this.key = key;
    }

    /**
     * The vanilla name for this GrassColorModifier value.
     * @return the vanilla key for this enum value
     * @since 0.0.24
     */
    @AsOf("0.0.24")
    public String getKey() {
        return this.key;
    }

    @Override
    @AsOf("0.0.24")
    public KeyedEnumTranslator<GrassColorModifier> translator() {
        return TRANSLATOR;
    }
}
