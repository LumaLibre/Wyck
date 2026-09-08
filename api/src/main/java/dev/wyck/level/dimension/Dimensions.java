package dev.wyck.level.dimension;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.Generated;
import dev.wyck.keys.KeyChains;
import dev.wyck.keys.ResourceKey;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import org.jspecify.annotations.NullMarked;

/**
 * Auto-generated. Do not modify!
 * Run ./gradlew generateSources to regenerate.
 * <p>
 * Typed references that point to vanilla's dimensions.
 * </p>
 *
 *
 * @since 3.0.0
 * @version 3.4.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("3.0.0")
@Generated("2026-09-08T15:00:53.380959400Z")
public final class Dimensions {
    /**
     * From: BuiltinDimensionTypes
     */
    @AsOf("3.0.0")
    public static final Dimension OVERWORLD = reference("overworld");

    @AsOf("3.0.0")
    public static final Dimension NETHER = reference("the_nether");

    @AsOf("3.0.0")
    public static final Dimension END = reference("the_end");

    @AsOf("3.0.0")
    public static final Dimension OVERWORLD_CAVES = reference("overworld_caves");

    Dimensions() {
        throw new UnsupportedOperationException("Not intended for instantiation");
    }

    private static Dimension reference(String path) {
        Dimension keyed = Dimension.reference(ResourceKey.minecraft(path));
        KeyChains.DIMENSIONS.append(keyed);
        return keyed;
    }
}
