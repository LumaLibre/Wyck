//6180dcf697626cda31ff4ea3e50c8b9c
package dev.wyck.wrapper.environment;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.Generated;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.wrapper.internal.RegisteredConstantTranslator;
import dev.wyck.wrapper.internal.WrappedConstant;
import org.jspecify.annotations.NullMarked;

/**
 * Auto-generated. Do not modify!
 * Run ./gradlew generateSources to regenerate.
 * <p>
 * This enum represents the various activities that entities in Minecraft can perform.
 * Each enum value carries a vanilla key which the impl module translates to the underlying NMS Activity value.
 * </p>
 *
 * @since 1.1.0
 * @version 3.0.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("1.1.0")
@Generated("2026-07-15T07:01:58.789561Z")
public enum Activity implements WrappedConstant<Activity> {

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

    public static final RegisteredConstantTranslator<Activity> TRANSLATOR = RegisteredConstantTranslator.of(RegistryId.ACTIVITY, Activity::resourceKey, Activity.values());
    private final String key;

    @AsOf("1.1.0")
    Activity(String key) {
        this.key = key;
    }

    /**
     * The vanilla registry path for this activity.
     * @return the registry path for this activity
     * @since 1.1.0
     */
    @AsOf("1.1.0")
    public String key() {
        return this.key;
    }

    @AsOf("1.1.0")
    public ResourceKey resourceKey() {
        return ResourceKey.minecraft(this.key);
    }

    @Override
    @AsOf("1.1.0")
    public RegisteredConstantTranslator<Activity> translator() {
        return TRANSLATOR;
    }
}
