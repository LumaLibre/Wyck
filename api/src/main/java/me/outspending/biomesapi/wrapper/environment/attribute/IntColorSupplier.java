package me.outspending.biomesapi.wrapper.environment.attribute;

import me.outspending.biomesapi.annotations.AsOf;

import java.util.function.Supplier;

/**
 * A supplier for int color values.
 * @version 2.1.0
 * @since 2.1.0
 * @author Jsinco
 */
@AsOf("2.1.0")
public final class IntColorSupplier extends WrappedEnvironmentAttributeSupplier<Integer, Integer> {

    @AsOf("2.1.0")
    public IntColorSupplier(Supplier<WrappedEnvironmentAttribute<Integer, Integer>> supplier) {
        super(supplier);
    }

    @AsOf("2.1.0")
    public static int parseHex(String hex) {
        if (hex.startsWith("#")) hex = hex.substring(1);
        return (int) Long.parseLong(hex, 16);
    }
}