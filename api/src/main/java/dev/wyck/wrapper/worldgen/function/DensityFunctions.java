package dev.wyck.wrapper.worldgen.function;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.Generated;
import dev.wyck.keys.ResourceKey;
import org.jspecify.annotations.NullMarked;

/**
 * Auto-generated. Do not modify!
 * Run ./gradlew generateSources to regenerate.
 * <p>
 * Typed references that point to vanilla's density functions.
 * </p>
 *
 * @since 2.4.0
 * @version 3.0.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("2.4.0")
@Generated("2026-07-12T06:32:42.059456Z")
public final class DensityFunctions {

    // From: NoiseRouterData 
    @AsOf("2.4.0")
    public static final DensityFunction ZERO = reference("zero");
    @AsOf("2.4.0")
    public static final DensityFunction Y = reference("y");
    @AsOf("2.4.0")
    public static final DensityFunction SHIFT_X = reference("shift_x");
    @AsOf("2.4.0")
    public static final DensityFunction SHIFT_Z = reference("shift_z");
    @AsOf("2.4.0")
    public static final DensityFunction BASE_3D_NOISE_OVERWORLD = reference("overworld/base_3d_noise");
    @AsOf("2.4.0")
    public static final DensityFunction BASE_3D_NOISE_NETHER = reference("nether/base_3d_noise");
    @AsOf("2.4.0")
    public static final DensityFunction BASE_3D_NOISE_END = reference("end/base_3d_noise");
    @AsOf("2.4.0")
    public static final DensityFunction CONTINENTS = reference("overworld/continents");
    @AsOf("2.4.0")
    public static final DensityFunction EROSION = reference("overworld/erosion");
    @AsOf("2.4.0")
    public static final DensityFunction RIDGES = reference("overworld/ridges");
    @AsOf("2.4.0")
    public static final DensityFunction RIDGES_FOLDED = reference("overworld/ridges_folded");
    @AsOf("2.4.0")
    public static final DensityFunction OFFSET = reference("overworld/offset");
    @AsOf("2.4.0")
    public static final DensityFunction FACTOR = reference("overworld/factor");
    @AsOf("2.4.0")
    public static final DensityFunction JAGGEDNESS = reference("overworld/jaggedness");
    @AsOf("2.4.0")
    public static final DensityFunction DEPTH = reference("overworld/depth");
    @AsOf("2.4.0")
    public static final DensityFunction SLOPED_CHEESE = reference("overworld/sloped_cheese");
    @AsOf("2.4.0")
    public static final DensityFunction CONTINENTS_LARGE = reference("overworld_large_biomes/continents");
    @AsOf("2.4.0")
    public static final DensityFunction EROSION_LARGE = reference("overworld_large_biomes/erosion");
    @AsOf("2.4.0")
    public static final DensityFunction OFFSET_LARGE = reference("overworld_large_biomes/offset");
    @AsOf("2.4.0")
    public static final DensityFunction FACTOR_LARGE = reference("overworld_large_biomes/factor");
    @AsOf("2.4.0")
    public static final DensityFunction JAGGEDNESS_LARGE = reference("overworld_large_biomes/jaggedness");
    @AsOf("2.4.0")
    public static final DensityFunction DEPTH_LARGE = reference("overworld_large_biomes/depth");
    @AsOf("2.4.0")
    public static final DensityFunction SLOPED_CHEESE_LARGE = reference("overworld_large_biomes/sloped_cheese");
    @AsOf("2.4.0")
    public static final DensityFunction OFFSET_AMPLIFIED = reference("overworld_amplified/offset");
    @AsOf("2.4.0")
    public static final DensityFunction FACTOR_AMPLIFIED = reference("overworld_amplified/factor");
    @AsOf("2.4.0")
    public static final DensityFunction JAGGEDNESS_AMPLIFIED = reference("overworld_amplified/jaggedness");
    @AsOf("2.4.0")
    public static final DensityFunction DEPTH_AMPLIFIED = reference("overworld_amplified/depth");
    @AsOf("2.4.0")
    public static final DensityFunction SLOPED_CHEESE_AMPLIFIED = reference("overworld_amplified/sloped_cheese");
    @AsOf("2.4.0")
    public static final DensityFunction SLOPED_CHEESE_END = reference("end/sloped_cheese");
    @AsOf("2.4.0")
    public static final DensityFunction SPAGHETTI_ROUGHNESS_FUNCTION = reference("overworld/caves/spaghetti_roughness_function");
    @AsOf("2.4.0")
    public static final DensityFunction ENTRANCES = reference("overworld/caves/entrances");
    @AsOf("2.4.0")
    public static final DensityFunction NOODLE = reference("overworld/caves/noodle");
    @AsOf("2.4.0")
    public static final DensityFunction PILLARS = reference("overworld/caves/pillars");
    @AsOf("2.4.0")
    public static final DensityFunction SPAGHETTI_2D_THICKNESS_MODULATOR = reference("overworld/caves/spaghetti_2d_thickness_modulator");
    @AsOf("2.4.0")
    public static final DensityFunction SPAGHETTI_2D = reference("overworld/caves/spaghetti_2d");

    private static DensityFunction reference(String path) {
        DensityFunction keyed = DensityFunction.reference(ResourceKey.minecraft(path));
        dev.wyck.keys.KeyChains.DENSITY_FUNCTIONS.append(keyed);
        return keyed;
    }

    private DensityFunctions() {
        throw new UnsupportedOperationException("Not intended for instantiation");
    }
}
