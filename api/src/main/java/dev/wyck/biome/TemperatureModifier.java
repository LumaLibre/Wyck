package dev.wyck.biome;

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
 * This enum represents the temperature modifier for a biome in Minecraft.
 * It includes two values: NONE and FROZEN, which correspond to the TemperatureModifier values in the Biome class in the Minecraft code.
 * Each enum value carries a vanilla key, retrievable via {@link #getKey()}, which the impl module translates to the underlying NMS value.
 * </p>
 *
 *
 * @since 0.0.1
 * @version 3.4.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("0.0.1")
@Generated("2026-09-08T15:00:53.405987500Z")
public enum TemperatureModifier implements WrappedEnumerator<TemperatureModifier> {
    NONE("none"),

    FROZEN("frozen");

    public static final KeyedEnumTranslator<TemperatureModifier> TRANSLATOR = KeyedEnumTranslator.byKey(TemperatureModifier::getKey, TemperatureModifier.values());

    private final String key;

    @AsOf("0.0.1")
    TemperatureModifier(String key) {
        this.key = key;
    }

    @AsOf("0.0.1")
    @Override
    public KeyedEnumTranslator<TemperatureModifier> translator() {
        return TRANSLATOR;
    }

    /**
     *  The vanilla name for this TemperatureModifier
     *  @return the vanilla key for this enum value
     *  @since 0.0.1
     */
    @AsOf("0.0.1")
    public String getKey() {
        return this.key;
    }
}
