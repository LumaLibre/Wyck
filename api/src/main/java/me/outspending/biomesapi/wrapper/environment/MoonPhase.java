package me.outspending.biomesapi.wrapper.environment;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.internal.KeyedEnumTranslator;
import me.outspending.biomesapi.wrapper.internal.NmsEnumTranslatable;
import org.jspecify.annotations.NullMarked;

/**
 * This enum represents the phases of the moon in Minecraft.
 * Each enum value carries a vanilla key, retrievable via {@link #getKey()}, which the impl module translates to the underlying NMS MoonPhase value.
 *
 * @version 1.1.0
 * @since 1.1.0
 * @author Jsinco
 */
@NullMarked
@AsOf("1.1.0")
public enum MoonPhase implements NmsEnumTranslatable<MoonPhase> {

    FULL_MOON("full_moon"),
    WANING_GIBBOUS("waning_gibbous"),
    THIRD_QUARTER("third_quarter"),
    WANING_CRESCENT("waning_crescent"),
    NEW_MOON("new_moon"),
    WAXING_CRESCENT("waxing_crescent"),
    FIRST_QUARTER("first_quarter"),
    WAXING_GIBBOUS("waxing_gibbous");

    public static final KeyedEnumTranslator<MoonPhase> TRANSLATOR = KeyedEnumTranslator.byKey(MoonPhase::getKey, MoonPhase.values());

    private final String key;

    @AsOf("1.1.0")
    MoonPhase(String key) {
        this.key = key;
    }

    /**
     * Returns the vanilla key for this moon phase (e.g. "full_moon", "waning_crescent").
     * The impl module uses this key to resolve the underlying Minecraft MoonPhase value.
     * @return The vanilla key for this enum value.
     */
    @AsOf("1.1.0")
    public String getKey() {
        return key;
    }

    @Override
    public KeyedEnumTranslator<MoonPhase> translator() {
        return TRANSLATOR;
    }
}