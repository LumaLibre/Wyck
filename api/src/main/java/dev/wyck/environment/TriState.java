package dev.wyck.environment;

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
 * Represents a tri-state value, which can be true, false, or default.
 * </p>
 *
 *
 * @since 2.4.1
 * @version 3.4.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("2.4.1")
@Generated("2026-09-08T15:00:53.395321500Z")
public enum TriState implements WrappedEnumerator<TriState> {
    TRUE("TRUE"),

    FALSE("FALSE"),

    DEFAULT("DEFAULT");

    public static final KeyedEnumTranslator<TriState> TRANSLATOR = KeyedEnumTranslator.byKey(TriState::getKey, TriState.values());

    private final String key;

    @AsOf("2.4.1")
    TriState(String key) {
        this.key = key;
    }

    @AsOf("2.4.1")
    @Override
    public KeyedEnumTranslator<TriState> translator() {
        return TRANSLATOR;
    }

    /**
     *  The vanilla name for this TriState
     *  @return the vanilla key for this enum value
     *  @since 2.4.1
     */
    @AsOf("2.4.1")
    public String getKey() {
        return this.key;
    }
}
