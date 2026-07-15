//4ce41da62f732037aa1b1902e0ff1cab
package dev.wyck.wrapper.biome;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.Generated;
import dev.wyck.wrapper.internal.KeyedEnumTranslator;
import dev.wyck.wrapper.internal.WrappedEnumerator;
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
 * @since 0.0.1
 * @version 3.0.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("0.0.1")
@Generated("2026-07-15T07:01:58.785986Z")
public enum TemperatureModifier implements WrappedEnumerator<TemperatureModifier> {

    NONE("none"),
    FROZEN("frozen");

    public static final KeyedEnumTranslator<TemperatureModifier> TRANSLATOR = KeyedEnumTranslator.byKey(TemperatureModifier::getKey, TemperatureModifier.values());

    private final String key;

    @AsOf("0.0.1")
    TemperatureModifier(String key) {
        this.key = key;
    }

    /**
     * The vanilla name for this TemperatureModifier value.
     * @return the vanilla key for this enum value
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    public String getKey() {
        return this.key;
    }

    @Override
    @AsOf("0.0.1")
    public KeyedEnumTranslator<TemperatureModifier> translator() {
        return TRANSLATOR;
    }
}
