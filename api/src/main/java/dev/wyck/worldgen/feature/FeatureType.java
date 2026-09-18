package dev.wyck.worldgen.feature;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.Generated;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.wrapper.RegisteredConstantTranslator;
import dev.wyck.wrapper.WrappedConstant;
import java.lang.Override;
import java.lang.String;
import org.jspecify.annotations.NullMarked;

/**
 * Auto-generated. Do not modify!
 * Run ./gradlew generateSources to regenerate.
 * <p>
 * Typed references to the built-in feature algorithms in the {@code FEATURE_TYPE} registry.
 * </p>
 *
 *
 * @since 2.3.0
 * @version 4.0.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("2.3.0")
@Generated("2026-09-18T20:37:08.734858Z")
public enum FeatureType implements WrappedConstant<FeatureType> {
    BAMBOO("bamboo"),
    BLOCK_BLOB("block_blob"),
    BLOCK_COLUMN("block_column"),
    BLOCK_PILE("block_pile"),
    BLUE_ICE("blue_ice"),
    BONUS_CHEST("bonus_chest"),
    CHORUS_PLANT("chorus_plant"),
    CORAL_CLAW("coral_claw"),
    CORAL_TREE("coral_tree"),
    DELTA_FEATURE("delta_feature"),
    DISK("disk"),
    END_GATEWAY("end_gateway"),
    END_ISLAND("end_island"),
    END_PLATFORM("end_platform"),
    END_PODIUM("end_podium"),
    END_SPIKE("end_spike"),
    FALLEN_TREE("fallen_tree"),
    FILL_LAYER("fill_layer"),
    FOSSIL("fossil"),
    FREEZE_TOP_LAYER("freeze_top_layer"),
    GEODE("geode"),
    HUGE_BROWN_MUSHROOM("huge_brown_mushroom"),
    HUGE_FUNGUS("huge_fungus"),
    HUGE_RED_MUSHROOM("huge_red_mushroom"),
    ICEBERG("iceberg"),
    LAKE("lake"),
    LARGE_DRIPSTONE("large_dripstone"),
    MONSTER_ROOM("monster_room"),
    MULTIFACE_GROWTH("multiface_growth"),
    REPLACE_BLOBS("netherrack_replace_blobs"),
    NO_OP("no_op"),
    ORE("ore"),
    OVERLAY("overlay"),
    PROJECTED_RANDOM_PATCHY_SQUARE("projected_random_patchy_square"),
    RANDOM_BOOLEAN_SELECTOR("random_boolean_selector"),
    RANDOM_NEIGHBOR_SPREAD("random_neighbor_spread"),
    RANDOM_SELECTOR("random_selector"),
    REPLACE_SINGLE_BLOCK("replace_single_block"),
    ROOT_SYSTEM("root_system"),
    SCATTERED_ORE("scattered_ore"),
    SCULK_PATCH("sculk_patch"),
    SEQUENCE("sequence"),
    SIMPLE_BLOCK("simple_block"),
    SIMPLE_RANDOM_SELECTOR("simple_random_selector"),
    SINGLE_BLOCK_PILLAR("single_block_pillar"),
    SPELEOTHEM("speleothem"),
    SPELEOTHEM_CLUSTER("speleothem_cluster"),
    SPIKE("spike"),
    SPRING("spring_feature"),
    STEPPED_COLUMN_CLUSTER("stepped_column_cluster"),
    TEMPLATE("template"),
    TREE("tree"),
    UNDERWATER_MAGMA("underwater_magma"),
    VEGETATION_PATCH("vegetation_patch"),
    VINES("vines"),
    VOID_START_PLATFORM("void_start_platform"),
    WATERLOGGED_VEGETATION_PATCH("waterlogged_vegetation_patch"),
    WEIGHTED_RANDOM_SELECTOR("weighted_random_selector");

    public static final RegisteredConstantTranslator<FeatureType> TRANSLATOR = RegisteredConstantTranslator.of(RegistryId.FEATURE_TYPE, FeatureType::resourceKey, FeatureType.values());

    private final String key;

    @AsOf("2.3.0")
    FeatureType(String key) {
        this.key = key;
    }

    @AsOf("2.3.0")
    @Override
    public RegisteredConstantTranslator<FeatureType> translator() {
        return TRANSLATOR;
    }

    /**
     * The vanilla registry path for this activity.
     * @return the registry path for this activity
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
}
