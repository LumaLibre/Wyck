//927a924fd3bf4fa9808cba148fea8ff3
package dev.wyck.level.dimension;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.Generated;
import dev.wyck.wrapper.KeyedEnumTranslator;
import dev.wyck.wrapper.WrappedEnumerator;
import org.jspecify.annotations.NullMarked;

/**
 * Auto-generated. Do not modify!
 * Run ./gradlew generateSources to regenerate.
 * <p>
 * Cardinal light type, as it appears in Minecraft.
 * </p>
 *
 * @since 2.4.0
 * @version 3.0.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("2.4.0")
@Generated("2026-07-15T18:48:22.499922Z")
public enum CardinalLightType implements WrappedEnumerator<CardinalLightType> {

    DEFAULT("default"),
    NETHER("nether");

    public static final KeyedEnumTranslator<CardinalLightType> TRANSLATOR = KeyedEnumTranslator.byKey(CardinalLightType::getKey, CardinalLightType.values());

    private final String key;

    @AsOf("2.4.0")
    CardinalLightType(String key) {
        this.key = key;
    }

    /**
     * The vanilla name for this Type value.
     * @return the vanilla key for this enum value
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    public String getKey() {
        return this.key;
    }

    @Override
    @AsOf("2.4.0")
    public KeyedEnumTranslator<CardinalLightType> translator() {
        return TRANSLATOR;
    }
}
