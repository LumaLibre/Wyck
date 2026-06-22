package me.outspending.biomesapi.wrapper.level.dimension;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.internal.KeyedEnumTranslator;
import me.outspending.biomesapi.wrapper.internal.NmsEnumTranslatable;
import org.jspecify.annotations.NullMarked;

/**
 * Cardinal light type, as it appears in Minecraft.
 *
 * @version 2.4.0
 * @since 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
public enum CardinalLightType implements NmsEnumTranslatable<CardinalLightType> {
    DEFAULT("default"),
    NETHER("nether");

    private static final KeyedEnumTranslator<CardinalLightType> TRANSLATOR = KeyedEnumTranslator.byKey(CardinalLightType::getKey, CardinalLightType.values());

    private final String key;

    @AsOf("2.4.0")
    CardinalLightType(String key) {
        this.key = key;
    }

    @AsOf("2.4.0")
    public String getKey() {
        return key;
    }

    @Override
    @AsOf("2.4.0")
    public KeyedEnumTranslator<CardinalLightType> translator() {
        return TRANSLATOR;
    }
}
