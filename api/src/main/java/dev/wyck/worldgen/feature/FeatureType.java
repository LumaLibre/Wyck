//ff7e70fb38fb4275ddc0bde53a28296f
package dev.wyck.worldgen.feature;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.Generated;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.wrapper.RegisteredConstantTranslator;
import dev.wyck.wrapper.WrappedConstant;
import org.jspecify.annotations.NullMarked;

/**
 * Auto-generated. Do not modify!
 * Run ./gradlew generateSources to regenerate.
 * <p>
 * Typed references to the built-in feature types, the algorithms in the {@code FEATURE} registry.
 * </p>
 *
 * @since 2.3.0
 * @version 3.0.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("2.3.0")
@Generated("2026-07-15T18:48:22.503228Z")
public enum FeatureType implements WrappedConstant<FeatureType> {

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
    WEIGHTED_RANDOM_SELECTOR("weighted_random_selector"),
    SIMPLE_RANDOM_SELECTOR("simple_random_selector"),
    RANDOM_BOOLEAN_SELECTOR("random_boolean_selector"),
    SEQUENCE("sequence"),
    TEMPLATE("template"),
    GEODE("geode"),
    SPELEOTHEM_CLUSTER("speleothem_cluster"),
    LARGE_DRIPSTONE("large_dripstone"),
    SPELEOTHEM("speleothem"),
    SCULK_PATCH("sculk_patch"),
    @Deprecated(since = "3.0.0")
    POINTED_DRIPSTONE("pointed_dripstone");

    public static final RegisteredConstantTranslator<FeatureType> TRANSLATOR = RegisteredConstantTranslator.of(RegistryId.FEATURE, FeatureType::resourceKey, FeatureType.values());
    private final String key;

    @AsOf("2.3.0")
    FeatureType(String key) {
        this.key = key;
    }

    /**
     * The vanilla registry path for this feature.
     * @return the registry path for this feature
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public String key() {
        return this.key;
    }

    @AsOf("2.3.0")
    public ResourceKey resourceKey() {
        return ResourceKey.minecraft(this.key);
    }

    @Override
    @AsOf("2.3.0")
    public RegisteredConstantTranslator<FeatureType> translator() {
        return TRANSLATOR;
    }
}
