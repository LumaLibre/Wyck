package dev.wyck.wrapper.level.dimension;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.internal.KeyedEnumTranslator;
import dev.wyck.wrapper.internal.WrappedEnumerator;
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
public enum CardinalLightType implements WrappedEnumerator<CardinalLightType> {
    DEFAULT("default"),
    NETHER("nether");

    public static final KeyedEnumTranslator<CardinalLightType> TRANSLATOR = KeyedEnumTranslator.byKey(CardinalLightType::getKey, CardinalLightType.values());

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
