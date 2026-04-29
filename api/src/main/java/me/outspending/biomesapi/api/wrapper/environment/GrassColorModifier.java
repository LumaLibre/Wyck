package me.outspending.biomesapi.api.wrapper.environment;

import me.outspending.biomesapi.api.KeyedEnumTranslator;
import me.outspending.biomesapi.api.NmsEnumTranslatable;
import me.outspending.biomesapi.api.annotations.AsOf;

/**
 * This enum represents the grass color modifier for a biome in Minecraft.
 * It includes three values: NONE, SWAMP, and DARK_FOREST, which correspond to the GrassColorModifier values in the BiomeSpecialEffects class in the Minecraft code.
 * Each enum value carries a vanilla key, retrievable via {@link #getKey()}, which the impl module translates to the underlying NMS value.
 *
 * @version 0.0.24
 * @since 0.0.24
 * @author Jsinco
 */
@AsOf("0.0.24")
public enum GrassColorModifier implements NmsEnumTranslatable<GrassColorModifier> {

    NONE("none"),
    SWAMP("swamp"),
    DARK_FOREST("dark_forest");

    public static final KeyedEnumTranslator<GrassColorModifier> TRANSLATOR = KeyedEnumTranslator.byKey(GrassColorModifier::getKey);

    private final String key;

    @AsOf("0.0.24")
    GrassColorModifier(String key) {
        this.key = key;
    }

    /**
     * Returns the vanilla key for this grass color modifier (e.g. "none", "swamp", "dark_forest").
     * The impl module uses this key to resolve the underlying Minecraft GrassColorModifier value.
     * @return The vanilla key for this enum value.
     */
    @AsOf("0.0.24")
    public String getKey() {
        return key;
    }


    @Override
    public KeyedEnumTranslator<GrassColorModifier> translator() {
        return TRANSLATOR;
    }
}