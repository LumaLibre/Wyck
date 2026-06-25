package me.outspending.biomesapi.wrapper.worldgen;

import com.mojang.serialization.Codec;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.serialization.ConstantRepresentable;
import org.jspecify.annotations.NullMarked;

/**
 * The vanilla fluids a block predicate can match against.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public enum FluidType implements ConstantRepresentable {

    EMPTY("empty"),
    WATER("water"),
    FLOWING_WATER("flowing_water"),
    LAVA("lava"),
    FLOWING_LAVA("flowing_lava");

    // No translator, not an enum
    public static final Codec<FluidType> CODEC = ConstantRepresentable.codec(FluidType.values());

    private final String key;

    @AsOf("2.3.0")
    FluidType(String key) {
        this.key = key;
    }

    /**
     * The vanilla registry path for this fluid (e.g. "water", "lava").
     * @return the registry path for this fluid
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public String getKey() {
        return this.key;
    }
}