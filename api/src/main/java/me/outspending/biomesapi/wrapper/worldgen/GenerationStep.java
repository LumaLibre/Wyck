package me.outspending.biomesapi.wrapper.worldgen;

import com.mojang.serialization.Codec;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.internal.KeyedEnumTranslator;
import me.outspending.biomesapi.wrapper.internal.NmsEnumTranslatable;
import org.jspecify.annotations.NullMarked;

/**
 * Wraps Minecraft's GenerationStep.Decoration.
 *
 * @version 2.3.0
 * @since 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public enum GenerationStep implements NmsEnumTranslatable<GenerationStep> {

    RAW_GENERATION("RAW_GENERATION"),
    LAKES("LAKES"),
    LOCAL_MODIFICATIONS("LOCAL_MODIFICATIONS"),
    UNDERGROUND_STRUCTURES("UNDERGROUND_STRUCTURES"),
    SURFACE_STRUCTURES("SURFACE_STRUCTURES"),
    STRONGHOLDS("STRONGHOLDS"),
    UNDERGROUND_ORES("UNDERGROUND_ORES"),
    UNDERGROUND_DECORATION("UNDERGROUND_DECORATION"),
    FLUID_SPRINGS("FLUID_SPRINGS"),
    VEGETAL_DECORATION("VEGETAL_DECORATION"),
    TOP_LAYER_MODIFICATION("TOP_LAYER_MODIFICATION");

    public static final KeyedEnumTranslator<GenerationStep> TRANSLATOR = KeyedEnumTranslator.byKey(GenerationStep::getKey, GenerationStep.values());
    public static final Codec<GenerationStep> CODEC = TRANSLATOR.codec();

    private final String key;

    @AsOf("2.3.0")
    GenerationStep(String key) {
        this.key = key;
    }

    /**
     * The vanilla name for this decoration step.
     * @return the vanilla key for this enum value
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public String getKey() {
        return this.key;
    }

    @Override
    @AsOf("2.3.0")
    public KeyedEnumTranslator<GenerationStep> translator() {
        return TRANSLATOR;
    }
}