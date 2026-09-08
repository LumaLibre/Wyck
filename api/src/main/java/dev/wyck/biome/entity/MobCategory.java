package dev.wyck.biome.entity;

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
 * Represents the category of a mob in Minecraft.
 * </p>
 *
 *
 * @since 2.3.0
 * @version 3.4.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("2.3.0")
@Generated("2026-09-08T15:00:53.408082100Z")
public enum MobCategory implements WrappedEnumerator<MobCategory> {
    MONSTER("monster"),

    CREATURE("creature"),

    AMBIENT("ambient"),

    AXOLOTLS("axolotls"),

    UNDERGROUND_WATER_CREATURE("underground_water_creature"),

    WATER_CREATURE("water_creature"),

    WATER_AMBIENT("water_ambient"),

    MISC("misc");

    public static final KeyedEnumTranslator<MobCategory> TRANSLATOR = KeyedEnumTranslator.byKey(MobCategory::getKey, MobCategory.values());

    private final String key;

    @AsOf("2.3.0")
    MobCategory(String key) {
        this.key = key;
    }

    @AsOf("2.3.0")
    @Override
    public KeyedEnumTranslator<MobCategory> translator() {
        return TRANSLATOR;
    }

    /**
     *  The vanilla name for this MobCategory
     *  @return the vanilla key for this enum value
     *  @since 2.3.0
     */
    @AsOf("2.3.0")
    public String getKey() {
        return this.key;
    }
}
