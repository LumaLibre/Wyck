package me.outspending.biomesapi.wrapper.worldgen.surface;

import com.mojang.serialization.Codec;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.serialization.ConstantRepresentable;

/**
 * Which face of the stone column a depth check measures from.
 *
 * @since 2.4.0
 */
@AsOf("2.4.0")
public enum CaveSurface implements ConstantRepresentable {
    FLOOR("floor"),
    CEILING("ceiling");

    public static final Codec<CaveSurface> CODEC = ConstantRepresentable.codec(CaveSurface.values());

    private final String key;

    CaveSurface(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}