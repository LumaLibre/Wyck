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
 * This enum represents the phases of the moon in Minecraft.
 * Each enum value carries a vanilla key, retrievable via {@link #getKey()}, which the impl module translates to the underlying NMS MoonPhase value.
 * </p>
 *
 *
 * @since 1.1.0
 * @version 3.4.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("1.1.0")
@Generated("2026-09-08T15:00:53.402784900Z")
public enum MoonPhase implements WrappedEnumerator<MoonPhase> {
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

    @AsOf("1.1.0")
    @Override
    public KeyedEnumTranslator<MoonPhase> translator() {
        return TRANSLATOR;
    }

    /**
     *  The vanilla name for this MoonPhase
     *  @return the vanilla key for this enum value
     *  @since 1.1.0
     */
    @AsOf("1.1.0")
    public String getKey() {
        return this.key;
    }
}
