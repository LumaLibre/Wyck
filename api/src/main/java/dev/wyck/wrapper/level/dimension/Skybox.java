//5fae2767114132e4c60f12ef13fc1072
package dev.wyck.wrapper.level.dimension;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.Generated;
import dev.wyck.wrapper.internal.KeyedEnumTranslator;
import dev.wyck.wrapper.internal.WrappedEnumerator;
import org.jspecify.annotations.NullMarked;

/**
 * Auto-generated. Do not modify!
 * Run ./gradlew generateSources to regenerate.
 * <p>
 * Skybox type, as it appears in Minecraft.
 * </p>
 *
 * @since 2.4.0
 * @version 3.0.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("2.4.0")
@Generated("2026-07-15T07:01:58.783695Z")
public enum Skybox implements WrappedEnumerator<Skybox> {

    NONE("none"),
    OVERWORLD("overworld"),
    END("end");

    public static final KeyedEnumTranslator<Skybox> TRANSLATOR = KeyedEnumTranslator.byKey(Skybox::getKey, Skybox.values());

    private final String key;

    @AsOf("2.4.0")
    Skybox(String key) {
        this.key = key;
    }

    /**
     * The vanilla name for this Skybox value.
     * @return the vanilla key for this enum value
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    public String getKey() {
        return this.key;
    }

    @Override
    @AsOf("2.4.0")
    public KeyedEnumTranslator<Skybox> translator() {
        return TRANSLATOR;
    }
}
