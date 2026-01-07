package me.outspending.biomesapi.wrapper.environment;

import me.outspending.biomesapi.annotations.AsOf;
import net.minecraft.world.level.biome.BiomeSpecialEffects;

/**
 * This enum represents the grass color modifier for a biome in Minecraft.
 * It includes three values: NONE, SWAMP, and DARK_FOREST, which correspond to the GrassColorModifier values in the BiomeSpecialEffects class in the Minecraft code.
 * Each enum value includes a GrassColorModifier object, which can be retrieved using the getDelegateModifier method.
 *
 * @version 0.0.24
 * @since 0.0.24
 * @author Jsinco
 */
@AsOf("0.0.24")
public enum GrassColorModifier {

    NONE(BiomeSpecialEffects.GrassColorModifier.NONE),
    SWAMP(BiomeSpecialEffects.GrassColorModifier.SWAMP),
    DARK_FOREST(BiomeSpecialEffects.GrassColorModifier.DARK_FOREST);


    private final BiomeSpecialEffects.GrassColorModifier delegateModifier;

    @AsOf("0.0.24")
    GrassColorModifier(BiomeSpecialEffects.GrassColorModifier delegateModifier) {
        this.delegateModifier = delegateModifier;
    }

    /**
     * This method returns the GrassColorModifier that corresponds to the enum value.
     * @return The GrassColorModifier that corresponds to the enum value.
     */
    @AsOf("0.0.24")
    public BiomeSpecialEffects.GrassColorModifier getDelegateModifier() {
        return delegateModifier;
    }

}
