package dev.wyck.wrapper.biome;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.internal.KeyedEnumTranslator;
import dev.wyck.wrapper.internal.WrappedEnumerator;
import org.jspecify.annotations.NullMarked;

/**
 * This enum represents the temperature modifier for a biome in Minecraft.
 * It includes two values: NONE and FROZEN, which correspond to the TemperatureModifier values in the Biome class in the Minecraft code.
 * Each enum value includes a TemperatureModifier object, which can be retrieved using the getModifier method.
 *
 * @version 0.0.1
 * @since 0.0.1
 * @author Outspending
 */
@NullMarked
@AsOf("0.0.1")
public enum TemperatureModifier implements WrappedEnumerator<TemperatureModifier> {

    NONE("none"),
    FROZEN("frozen");

    public static final KeyedEnumTranslator<TemperatureModifier> TRANSLATOR = KeyedEnumTranslator.byKey(TemperatureModifier::getKey, TemperatureModifier.values());

    private final String key;

    TemperatureModifier(String key) {
        this.key = key;
    }

    /**
     * The vanilla key for this temperature modifier (e.g. "none", "frozen").
     */
    @AsOf("0.0.1")
    public String getKey() {
        return key;
    }

    @Override
    public KeyedEnumTranslator<TemperatureModifier> translator() {
        return TRANSLATOR;
    }
}
