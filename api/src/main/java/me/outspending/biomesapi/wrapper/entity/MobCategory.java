package me.outspending.biomesapi.wrapper.entity;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.unsafe.KeyedEnumTranslator;
import me.outspending.biomesapi.unsafe.NmsEnumTranslatable;

/**
 * Represents the category of a mob in Minecraft.
 * @version 2.3.0
 * @since 2.3.0
 * @author Jsinco
 */
@AsOf("2.3.0")
public enum MobCategory implements NmsEnumTranslatable<MobCategory> {
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

    /**
     * The vanilla key for this mob category (e.g. "monster", "creature", "ambient").
     * @return The vanilla key for this enum value.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public String getKey() {
        return key;
    }

    /**
     * The translator for this enum.
     * @return The translator for this enum.
     * @since 2.3.0
     */
    @Override
    @AsOf("2.3.0")
    public KeyedEnumTranslator<MobCategory> translator() {
        return TRANSLATOR;
    }
}
