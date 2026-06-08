package me.outspending.biomesapi.wrapper.worldgen.feature;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

// TODO: Custom ConfiguredFeature implementations
/**
 * Typed references to the built-in feature types, the algorithms in the {@code FEATURE}
 * registry.
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
@ApiStatus.Experimental
public enum FeatureType {

    NO_OP("no_op"),
    TREE("tree"),
    FALLEN_TREE("fallen_tree"),
    BLOCK_PILE("block_pile"),
    SPRING("spring_feature"),
    CHORUS_PLANT("chorus_plant"),
    REPLACE_SINGLE_BLOCK("replace_single_block"),
    VOID_START_PLATFORM("void_start_platform"),
    DESERT_WELL("desert_well"),
    FOSSIL("fossil"),
    HUGE_RED_MUSHROOM("huge_red_mushroom"),
    HUGE_BROWN_MUSHROOM("huge_brown_mushroom"),
    SPIKE("spike"),
    GLOWSTONE_BLOB("glowstone_blob"),
    FREEZE_TOP_LAYER("freeze_top_layer"),
    VINES("vines"),
    BLOCK_COLUMN("block_column"),
    VEGETATION_PATCH("vegetation_patch"),
    WATERLOGGED_VEGETATION_PATCH("waterlogged_vegetation_patch"),
    ROOT_SYSTEM("root_system"),
    MULTIFACE_GROWTH("multiface_growth"),
    UNDERWATER_MAGMA("underwater_magma"),
    MONSTER_ROOM("monster_room"),
    BLUE_ICE("blue_ice"),
    ICEBERG("iceberg"),
    BLOCK_BLOB("block_blob"),
    DISK("disk"),
    LAKE("lake"),
    ORE("ore"),
    END_PLATFORM("end_platform"),
    END_SPIKE("end_spike"),
    END_ISLAND("end_island"),
    END_GATEWAY("end_gateway"),
    SEAGRASS("seagrass"),
    KELP("kelp"),
    CORAL_TREE("coral_tree"),
    CORAL_MUSHROOM("coral_mushroom"),
    CORAL_CLAW("coral_claw"),
    SEA_PICKLE("sea_pickle"),
    SIMPLE_BLOCK("simple_block"),
    BAMBOO("bamboo"),
    HUGE_FUNGUS("huge_fungus"),
    NETHER_FOREST_VEGETATION("nether_forest_vegetation"),
    WEEPING_VINES("weeping_vines"),
    TWISTING_VINES("twisting_vines"),
    BASALT_COLUMNS("basalt_columns"),
    DELTA_FEATURE("delta_feature"),
    REPLACE_BLOBS("netherrack_replace_blobs"),
    FILL_LAYER("fill_layer"),
    BONUS_CHEST("bonus_chest"),
    BASALT_PILLAR("basalt_pillar"),
    SCATTERED_ORE("scattered_ore"),
    RANDOM_SELECTOR("random_selector"),
    SIMPLE_RANDOM_SELECTOR("simple_random_selector"),
    RANDOM_BOOLEAN_SELECTOR("random_boolean_selector"),
    GEODE("geode"),
    DRIPSTONE_CLUSTER("dripstone_cluster"),
    LARGE_DRIPSTONE("large_dripstone"),
    POINTED_DRIPSTONE("pointed_dripstone"),
    SCULK_PATCH("sculk_patch");

    private final String key;

    @AsOf("2.3.0")
    FeatureType(String key) {
        this.key = key;
    }

    /**
     * The vanilla registry path for this feature type.
     * @return the FEATURE registry key path
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public String getKey() {
        return this.key;
    }

    /**
     * The registry key of this feature in the FEATURE registry.
     * @return the resource key
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public BiomeResourceKey resourceKey() {
        return BiomeResourceKey.minecraft(this.key);
    }
}