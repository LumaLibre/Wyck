package me.outspending.biomesapi.wrapper;

import me.outspending.biomesapi.annotations.AsOf;
import net.minecraft.world.level.biome.BiomeSpecialEffects;

/**
 * This enum represents the grass color modifier for a biome in Minecraft.
 * It includes three values: NONE, SWAMP, and DARK_FOREST, which correspond to the GrassColorModifier values in the BiomeSpecialEffects class in the Minecraft code.
 * Each enum value includes a GrassColorModifier object, which can be retrieved using the getDelegateModifier method.
 *
 * @version 0.0.24
 * @since 0.0.24
 * @author Outspending
 */
@AsOf("0.0.24")
public enum GrassColorModifier {

    NONE(BiomeSpecialEffects.GrassColorModifier.NONE),
    SWAMP(BiomeSpecialEffects.GrassColorModifier.SWAMP),
    DARK_FOREST(BiomeSpecialEffects.GrassColorModifier.DARK_FOREST);


    private final BiomeSpecialEffects.GrassColorModifier delegateModifier;

    GrassColorModifier(BiomeSpecialEffects.GrassColorModifier delegateModifier) {
        this.delegateModifier = delegateModifier;
    }


    public BiomeSpecialEffects.GrassColorModifier getDelegateModifier() {
        return delegateModifier;
    }

}
