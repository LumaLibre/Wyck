//25642a28d25c2dd5f626d549a78bb6b8
package dev.wyck.worldgen;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.Generated;
import dev.wyck.wrapper.KeyedEnumTranslator;
import dev.wyck.wrapper.WrappedEnumerator;
import org.jspecify.annotations.NullMarked;

/**
 * Auto-generated. Do not modify!
 * Run ./gradlew generateSources to regenerate.
 * <p>
 * A rotation applied to a structure template, in 90-degree steps about the Y axis.
 * </p>
 * @see <a href="https://minecraft.wiki/w/Template_pool">Template pool</a>
 *
 * @since 3.1.0
 * @version 3.1.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("3.1.0")
@Generated("2026-07-16T08:25:35.507016Z")
public enum Rotation implements WrappedEnumerator<Rotation> {

    NONE("NONE"),
    CLOCKWISE_90("CLOCKWISE_90"),
    CLOCKWISE_180("CLOCKWISE_180"),
    COUNTERCLOCKWISE_90("COUNTERCLOCKWISE_90");

    public static final KeyedEnumTranslator<Rotation> TRANSLATOR = KeyedEnumTranslator.byKey(Rotation::getKey, Rotation.values());

    private final String key;

    @AsOf("3.1.0")
    Rotation(String key) {
        this.key = key;
    }

    /**
     * The vanilla name for this Rotation value.
     * @return the vanilla key for this enum value
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    public String getKey() {
        return this.key;
    }

    @Override
    @AsOf("3.1.0")
    public KeyedEnumTranslator<Rotation> translator() {
        return TRANSLATOR;
    }
}
