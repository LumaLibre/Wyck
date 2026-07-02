package dev.wyck.wrapper.environment;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.internal.KeyedEnumTranslator;
import dev.wyck.wrapper.internal.WrappedEnumerator;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Represents a tri-state value, which can be true, false, or default.
 *
 * @since 2.4.1
 * @version 2.4.1
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.1")
public enum TriState implements WrappedEnumerator<TriState> {
    TRUE("true"),
    FALSE("false"),
    DEFAULT("default");

    public static final KeyedEnumTranslator<TriState> TRANSLATOR = KeyedEnumTranslator.byKey(TriState::getKey, TriState.values());

    private final String key;

    TriState(String key) {
        this.key = key;
    }

    /**
     * Gets the key for this enum.
     * @return The key for this enum.
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    public String getKey() {
        return key;
    }

    /**
     * Gets the translator for this enum.
     * @return The translator for this enum.
     * @since 2.4.1
     */
    @Override
    @AsOf("2.4.1")
    public KeyedEnumTranslator<TriState> translator() {
        return TRANSLATOR;
    }

    /**
     * Converts a boolean to a TriState.
     * @param value The boolean to convert.
     * @return The TriState equivalent of the boolean.
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    public static TriState fromBoolean(@Nullable Boolean value) {
        return value == null ? DEFAULT : value ? TRUE : FALSE;
    }
}
