package dev.wyck.wrapper.level.dimension;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.internal.KeyedEnumTranslator;
import dev.wyck.wrapper.internal.WrappedEnumerator;
import org.jspecify.annotations.NullMarked;

/**
 * Skybox type, as it appears in Minecraft.
 *
 * @version 2.4.0
 * @since 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
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

    @AsOf("2.4.0")
    public String getKey() {
        return key;
    }

    @Override
    @AsOf("2.4.0")
    public KeyedEnumTranslator<Skybox> translator() {
        return TRANSLATOR;
    }
}
