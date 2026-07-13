package dev.wyck.model.level.dimension;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.Generated;
import dev.wyck.keys.ResourceKey;
import org.jspecify.annotations.NullMarked;

/**
 * Auto-generated. Do not modify!
 * Run ./gradlew generateSources to regenerate.
 * <p>
 * Typed references that point to vanilla's dimensions.
 * </p>
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("3.0.0")
@Generated("2026-07-13T07:21:53.853542Z")
public final class Dimensions {

    // From: BuiltinDimensionTypes 
    @AsOf("3.0.0")
    public static final Dimension OVERWORLD = reference("overworld");
    @AsOf("3.0.0")
    public static final Dimension NETHER = reference("the_nether");
    @AsOf("3.0.0")
    public static final Dimension END = reference("the_end");
    @AsOf("3.0.0")
    public static final Dimension OVERWORLD_CAVES = reference("overworld_caves");

    private static Dimension reference(String path) {
        Dimension keyed = Dimension.reference(ResourceKey.minecraft(path));
        dev.wyck.keys.KeyChains.DIMENSIONS.append(keyed);
        return keyed;
    }

    private Dimensions() {
        throw new UnsupportedOperationException("Not intended for instantiation");
    }
}
