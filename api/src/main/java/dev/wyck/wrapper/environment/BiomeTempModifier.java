package dev.wyck.wrapper.environment;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.internal.KeyedEnumTranslator;
import dev.wyck.wrapper.internal.NmsEnumTranslatable;
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
public enum BiomeTempModifier implements NmsEnumTranslatable<BiomeTempModifier> {

    NONE("none"),
    FROZEN("frozen");

    public static final KeyedEnumTranslator<BiomeTempModifier> TRANSLATOR = KeyedEnumTranslator.byKey(BiomeTempModifier::getKey, BiomeTempModifier.values());

    private final String key;

    BiomeTempModifier(String key) {
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
    public KeyedEnumTranslator<BiomeTempModifier> translator() {
        return TRANSLATOR;
    }
}
