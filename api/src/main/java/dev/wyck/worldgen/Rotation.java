package dev.wyck.worldgen;

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
 * A rotation applied to a structure template, in 90-degree steps about the Y axis.
 * </p>
 *
 * @see <a href="https://minecraft.wiki/w/Template_pool">Template pool</a>
 * @since 3.0.1
 * @version 3.4.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("3.0.1")
@Generated("2026-09-08T15:00:53.392389900Z")
public enum Rotation implements WrappedEnumerator<Rotation> {
    NONE("NONE"),

    CLOCKWISE_90("CLOCKWISE_90"),

    CLOCKWISE_180("CLOCKWISE_180"),

    COUNTERCLOCKWISE_90("COUNTERCLOCKWISE_90");

    public static final KeyedEnumTranslator<Rotation> TRANSLATOR = KeyedEnumTranslator.byKey(Rotation::getKey, Rotation.values());

    private final String key;

    @AsOf("3.0.1")
    Rotation(String key) {
        this.key = key;
    }

    @AsOf("3.0.1")
    @Override
    public KeyedEnumTranslator<Rotation> translator() {
        return TRANSLATOR;
    }

    /**
     *  The vanilla name for this Rotation
     *  @return the vanilla key for this enum value
     *  @since 3.0.1
     */
    @AsOf("3.0.1")
    public String getKey() {
        return this.key;
    }
}
