package dev.wyck.environment;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.Generated;
import dev.wyck.wrapper.KeyedEnumTranslator;
import dev.wyck.wrapper.WrappedEnumerator;
import java.lang.Override;
import java.lang.String;
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
 *
 * @since 0.0.24
 * @version 3.4.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("0.0.24")
@Generated("2026-09-08T15:00:53.403990300Z")
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

    @AsOf("0.0.24")
    @Override
    public KeyedEnumTranslator<GrassColorModifier> translator() {
        return TRANSLATOR;
    }

    /**
     *  The vanilla name for this GrassColorModifier
     *  @return the vanilla key for this enum value
     *  @since 0.0.24
     */
    @AsOf("0.0.24")
    public String getKey() {
        return this.key;
    }
}
