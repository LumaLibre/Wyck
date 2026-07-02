package dev.wyck.wrapper.environment;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.internal.KeyedEnumTranslator;
import dev.wyck.wrapper.internal.NmsEnumTranslatable;
import org.jspecify.annotations.NullMarked;

/**
 * This enum represents the various activities that entities in Minecraft can perform.
 * Each enum value carries a vanilla key, retrievable via {@link #getKey()}, which the impl module translates to the underlying NMS Activity value.
 *
 * @version 1.1.0
 * @since 1.1.0
 * @author Jsinco
 */
@NullMarked
@AsOf("1.1.0")
public enum Activity implements NmsEnumTranslatable<Activity> {

    CORE("core"),
    IDLE("idle"),
    WORK("work"),
    PLAY("play"),
    REST("rest"),
    MEET("meet"),
    PANIC("panic"),
    RAID("raid"),
    PRE_RAID("pre_raid"),
    HIDE("hide"),
    FIGHT("fight"),
    CELEBRATE("celebrate"),
    ADMIRE_ITEM("admire_item"),
    AVOID("avoid"),
    RIDE("ride"),
    PLAY_DEAD("play_dead"),
    LONG_JUMP("long_jump"),
    RAM("ram"),
    TONGUE("tongue"),
    SWIM("swim"),
    LAY_SPAWN("lay_spawn"),
    SNIFF("sniff"),
    INVESTIGATE("investigate"),
    ROAR("roar"),
    EMERGE("emerge"),
    DIG("dig");

    public static final KeyedEnumTranslator<Activity> TRANSLATOR = KeyedEnumTranslator.byKey(Activity::getKey, Activity.values());

    private final String key;

    @AsOf("1.1.0")
    Activity(String key) {
        this.key = key;
    }

    /**
     * Returns the vanilla key for this activity (e.g. "core", "idle", "play_dead").
     * The impl module uses this key to resolve the underlying Minecraft Activity value.
     * @return The vanilla key for this enum value.
     */
    @AsOf("1.1.0")
    public String getKey() {
        return key;
    }

    @Override
    public KeyedEnumTranslator<Activity> translator() {
        return TRANSLATOR;
    }
}