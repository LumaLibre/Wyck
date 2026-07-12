package dev.wyck.wrapper.worldgen.surface.condition;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.internal.KeyedEnumTranslator;
import dev.wyck.wrapper.internal.WrappedEnumerator;
import org.jspecify.annotations.NullMarked;

/**
 * Which face of the surface a {@link StoneDepthConditionSource stone-depth} check measures from. If
 * {@link #FLOOR}, blocks are placed based on the distance to the surface above; if {@link #CEILING},
 * the distance to the surface below is used instead.
 *
 * @see <a href="https://minecraft.wiki/w/Surface_rule#Surface_conditions">Surface rule (surface conditions)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public enum CaveSurface implements WrappedEnumerator<CaveSurface> {

    FLOOR("FLOOR"),
    CEILING("CEILING");

    public static final KeyedEnumTranslator<CaveSurface> TRANSLATOR = KeyedEnumTranslator.byKey(CaveSurface::getKey, CaveSurface.values());

    private final String key;

    @AsOf("3.0.0")
    CaveSurface(String key) {
        this.key = key;
    }

    /**
     * The vanilla name for this cave surface.
     * @return the vanilla key for this enum value
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    public String getKey() {
        return this.key;
    }

    @Override
    @AsOf("3.0.0")
    public KeyedEnumTranslator<CaveSurface> translator() {
        return TRANSLATOR;
    }
}
