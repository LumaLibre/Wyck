package dev.wyck.worldgen.surface.condition;

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
 * Which face of the surface a {@link StoneDepthConditionSource stone-depth} check measures from.
 * {@link #FLOOR}, blocks are placed based on the distance to the surface above; if {@link #CEILING},
 * the distance to the surface below is used instead.
 * </p>
 *
 * @see <a href="https://minecraft.wiki/w/Surface_rule#Surface_conditions">Surface rule (surface conditions)</a>
 * @since 3.0.0
 * @version 3.4.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("3.0.0")
@Generated("2026-09-08T15:00:53.390394300Z")
public enum CaveSurface implements WrappedEnumerator<CaveSurface> {
    CEILING("CEILING"),

    FLOOR("FLOOR");

    public static final KeyedEnumTranslator<CaveSurface> TRANSLATOR = KeyedEnumTranslator.byKey(CaveSurface::getKey, CaveSurface.values());

    private final String key;

    @AsOf("3.0.0")
    CaveSurface(String key) {
        this.key = key;
    }

    @AsOf("3.0.0")
    @Override
    public KeyedEnumTranslator<CaveSurface> translator() {
        return TRANSLATOR;
    }

    /**
     *  The vanilla name for this CaveSurface
     *  @return the vanilla key for this enum value
     *  @since 3.0.0
     */
    @AsOf("3.0.0")
    public String getKey() {
        return this.key;
    }
}
