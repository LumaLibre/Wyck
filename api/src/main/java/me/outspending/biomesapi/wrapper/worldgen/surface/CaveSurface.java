package me.outspending.biomesapi.wrapper.worldgen.surface;

import com.mojang.serialization.Codec;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.internal.KeyedEnumTranslator;
import me.outspending.biomesapi.wrapper.internal.NmsEnumTranslatable;
import org.jspecify.annotations.NullMarked;

/**
 * Which face of the stone column a depth check measures from.
 *
 * @since 2.4.0
 */
@NullMarked
@AsOf("2.4.0")
public enum CaveSurface implements NmsEnumTranslatable<CaveSurface> {
    FLOOR("floor"),
    CEILING("ceiling");

    public static final KeyedEnumTranslator<CaveSurface> TRANSLATOR = KeyedEnumTranslator.byKey(CaveSurface::getKey, CaveSurface.values());
    public static final Codec<CaveSurface> CODEC = TRANSLATOR.codec();

    private final String key;

    CaveSurface(String key) {
        this.key = key;
    }

    @Override
    @AsOf("2.4.0")
    public String getKey() {
        return key;
    }

    @Override
    @AsOf("2.4.0")
    public KeyedEnumTranslator<CaveSurface> translator() {
        return TRANSLATOR;
    }
}