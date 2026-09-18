package dev.wyck.worldgen.function;

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
 * Typed references that point to vanilla's density functions.
 * </p>
 *
 *
 * @since 2.4.0
 * @version 4.0.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("2.4.0")
@Generated("2026-09-18T20:37:08.715304Z")
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
    public static final DensityFunction RIDGES = reference("overworld/ridges");
    @AsOf("2.4.0")
    public static final DensityFunction RIDGES_FOLDED = reference("overworld/ridges_folded");
    @AsOf("2.4.0")
    public static final DensityFunction END_ISLANDS = reference("end/islands");
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
    @AsOf("2.4.0")
    public static final DensityFunction ORE_VEIN_MASK = reference("overworld/ore_vein/mask");
    @AsOf("2.4.0")
    public static final DensityFunction ORE_VEIN_TOGGLE = reference("overworld/ore_vein/toggle");
    @AsOf("2.4.0")
    public static final DensityFunction ORE_VEIN_RICHNESS = reference("overworld/ore_vein/richness");
    @AsOf("2.4.0")
    public static final DensityFunction ORE_VEIN_COPPER_DENSITY = reference("overworld/ore_vein/copper_density");
    @AsOf("2.4.0")
    public static final DensityFunction ORE_VEIN_IRON_DENSITY = reference("overworld/ore_vein/iron_density");
    @AsOf("2.4.0")
    public static final DensityFunction ORE_VEIN_GAP = reference("overworld/ore_vein/gap");

    DensityFunctions() {
        throw new UnsupportedOperationException("Not intended for instantiation");
    }

    private static DensityFunction reference(String path) {
        DensityFunction keyed = DensityFunction.reference(ResourceKey.minecraft(path));
        KeyChains.DENSITY_FUNCTIONS.append(keyed);
        return keyed;
    }
}
