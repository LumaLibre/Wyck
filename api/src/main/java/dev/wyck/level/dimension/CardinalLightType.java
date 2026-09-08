package dev.wyck.level.dimension;

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
 * Cardinal light type, as it appears in Minecraft.
 * </p>
 *
 *
 * @since 2.4.0
 * @version 3.4.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("2.4.0")
@Generated("2026-09-08T15:00:53.400317300Z")
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
    @Override
    public KeyedEnumTranslator<CardinalLightType> translator() {
        return TRANSLATOR;
    }

    /**
     *  The vanilla name for this Type
     *  @return the vanilla key for this enum value
     *  @since 2.4.0
     */
    @AsOf("2.4.0")
    public String getKey() {
        return this.key;
    }
}
