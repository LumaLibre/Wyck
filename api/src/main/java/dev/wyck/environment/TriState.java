//c2186f9327e771ea373f8588e652dcda
package dev.wyck.environment;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.Generated;
import dev.wyck.wrapper.KeyedEnumTranslator;
import dev.wyck.wrapper.WrappedEnumerator;
import org.jspecify.annotations.NullMarked;

/**
 * Auto-generated. Do not modify!
 * Run ./gradlew generateSources to regenerate.
 * <p>
 * Represents a tri-state value, which can be true, false, or default.
 * </p>
 *
 * @since 2.4.1
 * @version 3.0.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("2.4.1")
@Generated("2026-07-15T18:48:22.498039Z")
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

    /**
     * The vanilla name for this TriState value.
     * @return the vanilla key for this enum value
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    public String getKey() {
        return this.key;
    }

    @Override
    @AsOf("2.4.1")
    public KeyedEnumTranslator<TriState> translator() {
        return TRANSLATOR;
    }
}
