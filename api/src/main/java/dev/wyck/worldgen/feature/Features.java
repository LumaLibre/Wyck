package dev.wyck.worldgen.feature;

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
 * Typed references that point to vanilla's configured features.
 * </p>
 *
 *
 * @since 2.3.0
 * @version 4.0.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("2.3.0")
@Generated("2026-09-18T20:37:08.674559Z")
public final class Features {
    // From: AquaticFeatures
    @AsOf("2.3.0")
    public static final Feature SEAGRASS_SHORT = reference("seagrass_short");
    @AsOf("2.3.0")
    public static final Feature SEAGRASS_SLIGHTLY_LESS_SHORT = reference("seagrass_slightly_less_short");
    @AsOf("2.3.0")
    public static final Feature SEAGRASS_MID = reference("seagrass_mid");
    @AsOf("2.3.0")
    public static final Feature SEAGRASS_TALL = reference("seagrass_tall");
    @AsOf("2.3.0")
    public static final Feature SEA_PICKLE = reference("sea_pickle");
    @AsOf("2.3.0")
    public static final Feature KELP = reference("kelp");
    @AsOf("2.3.0")
    public static final Feature CORAL_BLOCK_DECORATION = reference("coral/block_decoration");
    @AsOf("2.3.0")
    public static final Feature TUBE_CORAL_BLOCK = reference("coral/tube_block");
    @AsOf("2.3.0")
    public static final Feature BRAIN_CORAL_BLOCK = reference("coral/brain_block");
    @AsOf("2.3.0")
    public static final Feature BUBBLE_CORAL_BLOCK = reference("coral/bubble_block");
    @AsOf("2.3.0")
    public static final Feature FIRE_CORAL_BLOCK = reference("coral/fire_block");
    @AsOf("2.3.0")
    public static final Feature HORN_CORAL_BLOCK = reference("coral/horn_block");
    @AsOf("2.3.0")
    public static final Feature WARM_OCEAN_VEGETATION = reference("warm_ocean_vegetation");

    // From: CaveFeatures
    @AsOf("2.3.0")
    public static final Feature MONSTER_ROOM = reference("monster_room");
    @AsOf("2.3.0")
    public static final Feature FOSSIL_COAL = reference("fossil_coal");
    @AsOf("2.3.0")
    public static final Feature FOSSIL_DIAMONDS = reference("fossil_diamonds");
    @AsOf("2.3.0")
    public static final Feature DRIPSTONE_CLUSTER = reference("dripstone_cluster");
    @AsOf("2.4.0")
    public static final Feature SULFUR_SPIKE_CLUSTER = reference("sulfur_spike_cluster");
    @AsOf("2.3.0")
    public static final Feature LARGE_DRIPSTONE = reference("large_dripstone");
    @AsOf("2.3.0")
    public static final Feature POINTED_DRIPSTONE = reference("pointed_dripstone");
    @AsOf("2.4.0")
    public static final Feature SULFUR_SPIKE = reference("sulfur_spike");
    @AsOf("2.3.0")
    public static final Feature UNDERWATER_MAGMA = reference("underwater_magma");
    @AsOf("2.3.0")
    public static final Feature GLOW_LICHEN = reference("glow_lichen");
    @AsOf("2.3.0")
    public static final Feature ROOTED_AZALEA_TREE = reference("rooted_azalea_tree");
    @AsOf("2.4.0")
    public static final Feature ROOTED_SULFUR_SPRING = reference("rooted_sulfur_spring");
    @AsOf("2.3.0")
    public static final Feature CAVE_VINE = reference("cave_vine");
    @AsOf("2.3.0")
    public static final Feature CAVE_VINE_IN_MOSS = reference("cave_vine_in_moss");
    @AsOf("2.3.0")
    public static final Feature MOSS_VEGETATION = reference("moss_vegetation");
    @AsOf("2.3.0")
    public static final Feature MOSS_PATCH = reference("moss_patch");
    @AsOf("2.3.0")
    public static final Feature MOSS_PATCH_BONEMEAL = reference("moss_patch_bonemeal");
    @AsOf("2.3.0")
    public static final Feature DRIPLEAF = reference("dripleaf");
    @AsOf("2.3.0")
    public static final Feature CLAY_WITH_DRIPLEAVES = reference("clay_with_dripleaves");
    @AsOf("2.3.0")
    public static final Feature CLAY_POOL_WITH_DRIPLEAVES = reference("clay_pool_with_dripleaves");
    @AsOf("2.3.0")
    public static final Feature LUSH_CAVES_CLAY = reference("lush_caves_clay");
    @AsOf("2.3.0")
    public static final Feature MOSS_PATCH_CEILING = reference("moss_patch_ceiling");
    @AsOf("2.3.0")
    public static final Feature SPORE_BLOSSOM = reference("spore_blossom");
    @AsOf("2.3.0")
    public static final Feature AMETHYST_GEODE = reference("amethyst_geode");
    @AsOf("2.3.0")
    public static final Feature SCULK_PATCH_DEEP_DARK = reference("sculk_patch_deep_dark");
    @AsOf("2.3.0")
    public static final Feature SCULK_PATCH_ANCIENT_CITY = reference("sculk_patch_ancient_city");
    @AsOf("2.3.0")
    public static final Feature SCULK_VEIN = reference("sculk_vein");

    // From: EndFeatures
    @AsOf("2.3.0")
    public static final Feature END_PLATFORM = reference("end_platform");
    @AsOf("2.3.0")
    public static final Feature END_PODIUM_ACTIVE = reference("end_podium_active");
    @AsOf("2.3.0")
    public static final Feature END_PODIUM_INACTIVE = reference("end_podium_inactive");
    @AsOf("2.3.0")
    public static final Feature END_SPIKE = reference("end_spike");
    @AsOf("2.3.0")
    public static final Feature END_GATEWAY_RETURN = reference("end_gateway_return");
    @AsOf("2.3.0")
    public static final Feature END_GATEWAY_DELAYED = reference("end_gateway_delayed");
    @AsOf("2.3.0")
    public static final Feature CHORUS_PLANT = reference("chorus_plant");
    @AsOf("2.3.0")
    public static final Feature END_ISLAND = reference("end_island");

    // From: MiscOverworldFeatures
    @AsOf("2.3.0")
    public static final Feature ICE_SPIKE = reference("ice_spike");
    @AsOf("2.3.0")
    public static final Feature ICE_PATCH = reference("ice_patch");
    @AsOf("2.3.0")
    public static final Feature FOREST_ROCK = reference("forest_rock");
    @AsOf("2.3.0")
    public static final Feature ICEBERG_PACKED = reference("iceberg_packed");
    @AsOf("2.3.0")
    public static final Feature ICEBERG_BLUE = reference("iceberg_blue");
    @AsOf("2.3.0")
    public static final Feature BLUE_ICE = reference("blue_ice");
    @AsOf("2.3.0")
    public static final Feature LAKE_LAVA = reference("lake_lava");
    @AsOf("2.4.0")
    public static final Feature SULFUR_POOL = reference("sulfur_pool");
    @AsOf("2.4.0")
    public static final Feature SULFUR_SPRING = reference("sulfur_spring");
    @AsOf("2.3.0")
    public static final Feature DISK_CLAY = reference("disk_clay");
    @AsOf("2.3.0")
    public static final Feature DISK_GRAVEL = reference("disk_gravel");
    @AsOf("2.3.0")
    public static final Feature DISK_SAND = reference("disk_sand");
    @AsOf("2.3.0")
    public static final Feature FREEZE_TOP_LAYER = reference("freeze_top_layer");
    @AsOf("2.3.0")
    public static final Feature DISK_GRASS = reference("disk_grass");
    @AsOf("2.3.0")
    public static final Feature BONUS_CHEST = reference("bonus_chest");
    @AsOf("2.3.0")
    public static final Feature VOID_START_PLATFORM = reference("void_start_platform");
    @AsOf("2.3.0")
    public static final Feature DESERT_WELL = reference("desert_well");
    @AsOf("2.3.0")
    public static final Feature SPRING_LAVA_OVERWORLD = reference("spring_lava_overworld");
    @AsOf("2.3.0")
    public static final Feature SPRING_LAVA_FROZEN = reference("spring_lava_frozen");
    @AsOf("2.3.0")
    public static final Feature SPRING_WATER = reference("spring_water");

    // From: NetherFeatures
    @AsOf("2.3.0")
    public static final Feature DELTA = reference("delta");
    @AsOf("2.3.0")
    public static final Feature SMALL_BASALT_COLUMNS = reference("small_basalt_columns");
    @AsOf("2.3.0")
    public static final Feature LARGE_BASALT_COLUMNS = reference("large_basalt_columns");
    @AsOf("2.3.0")
    public static final Feature BASALT_BLOBS = reference("basalt_blobs");
    @AsOf("2.3.0")
    public static final Feature BLACKSTONE_BLOBS = reference("blackstone_blobs");
    @AsOf("2.3.0")
    public static final Feature GLOWSTONE_EXTRA = reference("glowstone_extra");
    @AsOf("2.3.0")
    public static final Feature NYLIUM_BONEMEAL = reference("nylium_bonemeal");
    @AsOf("2.3.0")
    public static final Feature CRIMSON_FOREST_VEGETATION = reference("crimson_forest_vegetation");
    @AsOf("2.3.0")
    public static final Feature WARPED_FOREST_VEGETION = reference("warped_forest_vegetation");
    @AsOf("2.3.0")
    public static final Feature NETHER_SPROUTS = reference("nether_sprouts");
    @AsOf("2.3.0")
    public static final Feature TWISTING_VINES = reference("twisting_vines");
    @AsOf("2.3.0")
    public static final Feature WEEPING_VINES = reference("weeping_vines");
    @AsOf("2.3.0")
    public static final Feature CRIMSON_ROOTS = reference("crimson_roots");
    @AsOf("2.3.0")
    public static final Feature BASALT_PILLAR = reference("basalt_pillar");
    @AsOf("2.3.0")
    public static final Feature SPRING_LAVA_NETHER = reference("spring_lava_nether");
    @AsOf("2.3.0")
    public static final Feature SPRING_NETHER_CLOSED = reference("spring_nether_closed");
    @AsOf("2.3.0")
    public static final Feature SPRING_NETHER_OPEN = reference("spring_nether_open");
    @AsOf("2.3.0")
    public static final Feature FIRE = reference("patch_fire");
    @AsOf("2.3.0")
    public static final Feature SOUL_FIRE = reference("patch_soul_fire");

    // From: OreFeatures
    @AsOf("2.3.0")
    public static final Feature ORE_MAGMA = reference("ore_magma");
    @AsOf("2.3.0")
    public static final Feature ORE_SOUL_SAND = reference("ore_soul_sand");
    @AsOf("2.3.0")
    public static final Feature ORE_NETHER_GOLD = reference("ore_nether_gold");
    @AsOf("2.3.0")
    public static final Feature ORE_QUARTZ = reference("ore_quartz");
    @AsOf("2.3.0")
    public static final Feature ORE_GRAVEL_NETHER = reference("ore_gravel_nether");
    @AsOf("2.3.0")
    public static final Feature ORE_BLACKSTONE = reference("ore_blackstone");
    @AsOf("2.3.0")
    public static final Feature ORE_DIRT = reference("ore_dirt");
    @AsOf("2.3.0")
    public static final Feature ORE_GRAVEL = reference("ore_gravel");
    @AsOf("2.3.0")
    public static final Feature ORE_GRANITE = reference("ore_granite");
    @AsOf("2.3.0")
    public static final Feature ORE_DIORITE = reference("ore_diorite");
    @AsOf("2.3.0")
    public static final Feature ORE_ANDESITE = reference("ore_andesite");
    @AsOf("2.3.0")
    public static final Feature ORE_TUFF = reference("ore_tuff");
    @AsOf("2.3.0")
    public static final Feature ORE_COAL = reference("ore_coal");
    @AsOf("2.3.0")
    public static final Feature ORE_COAL_BURIED = reference("ore_coal_buried");
    @AsOf("2.3.0")
    public static final Feature ORE_IRON = reference("ore_iron");
    @AsOf("2.3.0")
    public static final Feature ORE_IRON_SMALL = reference("ore_iron_small");
    @AsOf("2.3.0")
    public static final Feature ORE_GOLD = reference("ore_gold");
    @AsOf("2.3.0")
    public static final Feature ORE_GOLD_BURIED = reference("ore_gold_buried");
    @AsOf("2.3.0")
    public static final Feature ORE_REDSTONE = reference("ore_redstone");
    @AsOf("2.3.0")
    public static final Feature ORE_DIAMOND_SMALL = reference("ore_diamond_small");
    @AsOf("2.3.0")
    public static final Feature ORE_DIAMOND_MEDIUM = reference("ore_diamond_medium");
    @AsOf("2.3.0")
    public static final Feature ORE_DIAMOND_LARGE = reference("ore_diamond_large");
    @AsOf("2.3.0")
    public static final Feature ORE_DIAMOND_BURIED = reference("ore_diamond_buried");
    @AsOf("2.3.0")
    public static final Feature ORE_LAPIS = reference("ore_lapis");
    @AsOf("2.3.0")
    public static final Feature ORE_LAPIS_BURIED = reference("ore_lapis_buried");
    @AsOf("2.3.0")
    public static final Feature ORE_INFESTED = reference("ore_infested");
    @AsOf("2.3.0")
    public static final Feature ORE_EMERALD = reference("ore_emerald");
    @AsOf("2.3.0")
    public static final Feature ORE_ANCIENT_DEBRIS_LARGE = reference("ore_ancient_debris_large");
    @AsOf("2.3.0")
    public static final Feature ORE_ANCIENT_DEBRIS_SMALL = reference("ore_ancient_debris_small");
    @AsOf("2.3.0")
    public static final Feature ORE_COPPPER_SMALL = reference("ore_copper_small");
    @AsOf("2.3.0")
    public static final Feature ORE_COPPER_LARGE = reference("ore_copper_large");
    @AsOf("2.3.0")
    public static final Feature ORE_CLAY = reference("ore_clay");

    // From: PileFeatures
    @AsOf("2.3.0")
    public static final Feature PILE_HAY = reference("pile_hay");
    @AsOf("2.3.0")
    public static final Feature PILE_MELON = reference("pile_melon");
    @AsOf("2.3.0")
    public static final Feature PILE_SNOW = reference("pile_snow");
    @AsOf("2.3.0")
    public static final Feature PILE_ICE = reference("pile_ice");
    @AsOf("2.3.0")
    public static final Feature PILE_PUMPKIN = reference("pile_pumpkin");

    // From: TreeFeatures
    @AsOf("2.3.0")
    public static final Feature CRIMSON_FUNGUS = reference("crimson_fungus");
    @AsOf("2.3.0")
    public static final Feature CRIMSON_FUNGUS_PLANTED = reference("crimson_fungus_planted");
    @AsOf("2.3.0")
    public static final Feature WARPED_FUNGUS = reference("warped_fungus");
    @AsOf("2.3.0")
    public static final Feature WARPED_FUNGUS_PLANTED = reference("warped_fungus_planted");
    @AsOf("2.3.0")
    public static final Feature HUGE_BROWN_MUSHROOM = reference("huge_brown_mushroom");
    @AsOf("2.3.0")
    public static final Feature HUGE_RED_MUSHROOM = reference("huge_red_mushroom");
    @AsOf("2.3.0")
    public static final Feature OAK = reference("oak");
    @AsOf("2.3.0")
    public static final Feature DARK_OAK = reference("dark_oak");
    @AsOf("2.3.0")
    public static final Feature PALE_OAK = reference("pale_oak");
    @AsOf("2.3.0")
    public static final Feature PALE_OAK_BONEMEAL = reference("pale_oak_bonemeal");
    @AsOf("2.3.0")
    public static final Feature PALE_OAK_CREAKING = reference("pale_oak_creaking");
    @AsOf("2.3.0")
    public static final Feature BIRCH = reference("birch");
    @AsOf("2.3.0")
    public static final Feature ACACIA = reference("acacia");
    @AsOf("2.3.0")
    public static final Feature SPRUCE = reference("spruce");
    @AsOf("2.3.0")
    public static final Feature PINE = reference("pine");
    @AsOf("2.3.0")
    public static final Feature JUNGLE_TREE = reference("jungle_tree");
    @AsOf("2.3.0")
    public static final Feature FANCY_OAK = reference("fancy_oak");
    @AsOf("2.3.0")
    public static final Feature JUNGLE_TREE_NO_VINE = reference("jungle_tree_no_vine");
    @AsOf("2.3.0")
    public static final Feature MEGA_JUNGLE_TREE = reference("mega_jungle_tree");
    @AsOf("2.3.0")
    public static final Feature MEGA_SPRUCE = reference("mega_spruce");
    @AsOf("2.3.0")
    public static final Feature MEGA_PINE = reference("mega_pine");
    @AsOf("2.3.0")
    public static final Feature SUPER_BIRCH_BEES_0002 = reference("super_birch_bees_0002");
    @AsOf("2.3.0")
    public static final Feature SUPER_BIRCH_BEES = reference("super_birch_bees");
    @AsOf("2.3.0")
    public static final Feature SWAMP_OAK = reference("swamp_oak");
    @AsOf("2.3.0")
    public static final Feature JUNGLE_BUSH = reference("jungle_bush");
    @AsOf("2.3.0")
    public static final Feature AZALEA_TREE = reference("azalea_tree");
    @AsOf("2.3.0")
    public static final Feature MANGROVE = reference("mangrove");
    @AsOf("2.3.0")
    public static final Feature TALL_MANGROVE = reference("tall_mangrove");
    @AsOf("2.3.0")
    public static final Feature CHERRY = reference("cherry");
    @AsOf("2.3.0")
    public static final Feature OAK_BEES_0002_LEAF_LITTER = reference("oak_bees_0002_leaf_litter");
    @AsOf("2.3.0")
    public static final Feature OAK_BEES_002 = reference("oak_bees_002");
    @AsOf("2.3.0")
    public static final Feature OAK_BEES_005 = reference("oak_bees_005");
    @AsOf("2.3.0")
    public static final Feature BIRCH_BEES_0002 = reference("birch_bees_0002");
    @AsOf("2.3.0")
    public static final Feature BIRCH_BEES_0002_LEAF_LITTER = reference("birch_bees_0002_leaf_litter");
    @AsOf("2.3.0")
    public static final Feature BIRCH_BEES_002 = reference("birch_bees_002");
    @AsOf("2.3.0")
    public static final Feature BIRCH_BEES_005 = reference("birch_bees_005");
    @AsOf("2.3.0")
    public static final Feature FANCY_OAK_BEES_0002_LEAF_LITTER = reference("fancy_oak_bees_0002_leaf_litter");
    @AsOf("2.3.0")
    public static final Feature FANCY_OAK_BEES_002 = reference("fancy_oak_bees_002");
    @AsOf("2.3.0")
    public static final Feature FANCY_OAK_BEES_005 = reference("fancy_oak_bees_005");
    @AsOf("2.3.0")
    public static final Feature FANCY_OAK_BEES = reference("fancy_oak_bees");
    @AsOf("2.3.0")
    public static final Feature CHERRY_BEES_005 = reference("cherry_bees_005");
    @AsOf("2.3.0")
    public static final Feature OAK_LEAF_LITTER = reference("oak_leaf_litter");
    @AsOf("2.3.0")
    public static final Feature DARK_OAK_LEAF_LITTER = reference("dark_oak_leaf_litter");
    @AsOf("2.3.0")
    public static final Feature BIRCH_LEAF_LITTER = reference("birch_leaf_litter");
    @AsOf("2.3.0")
    public static final Feature FANCY_OAK_LEAF_LITTER = reference("fancy_oak_leaf_litter");
    @AsOf("2.3.0")
    public static final Feature RED_POPLAR = reference("red_poplar");
    @AsOf("2.3.0")
    public static final Feature ORANGE_POPLAR = reference("orange_poplar");
    @AsOf("2.3.0")
    public static final Feature YELLOW_POPLAR = reference("yellow_poplar");
    @AsOf("2.3.0")
    public static final Feature RED_POPLAR_LEAF_LITTER = reference("red_poplar_leaf_litter");
    @AsOf("2.3.0")
    public static final Feature ORANGE_POPLAR_LEAF_LITTER = reference("orange_poplar_leaf_litter");
    @AsOf("2.3.0")
    public static final Feature YELLOW_POPLAR_LEAF_LITTER = reference("yellow_poplar_leaf_litter");
    @AsOf("2.3.0")
    public static final Feature FALLEN_OAK_TREE = reference("fallen_oak_tree");
    @AsOf("2.3.0")
    public static final Feature FALLEN_JUNGLE_TREE = reference("fallen_jungle_tree");
    @AsOf("2.3.0")
    public static final Feature FALLEN_SPRUCE_TREE = reference("fallen_spruce_tree");
    @AsOf("2.3.0")
    public static final Feature FALLEN_BIRCH_TREE = reference("fallen_birch_tree");
    @AsOf("2.3.0")
    public static final Feature FALLEN_SUPER_BIRCH_TREE = reference("fallen_super_birch_tree");
    @AsOf("2.3.0")
    public static final Feature FALLEN_POPLAR_TREE = reference("fallen_poplar_tree");

    // From: VegetationFeatures
    @AsOf("2.3.0")
    public static final Feature BAMBOO_NO_PODZOL = reference("bamboo_no_podzol");
    @AsOf("2.3.0")
    public static final Feature BAMBOO_SOME_PODZOL = reference("bamboo_some_podzol");
    @AsOf("2.3.0")
    public static final Feature VINES = reference("vines");
    @AsOf("2.3.0")
    public static final Feature BROWN_MUSHROOM = reference("brown_mushroom");
    @AsOf("2.3.0")
    public static final Feature RED_MUSHROOM = reference("red_mushroom");
    @AsOf("2.3.0")
    public static final Feature SUNFLOWER = reference("sunflower");
    @AsOf("2.3.0")
    public static final Feature PUMPKIN = reference("pumpkin");
    @AsOf("2.3.0")
    public static final Feature BERRY_BUSH = reference("berry_bush");
    @AsOf("2.3.0")
    public static final Feature TAIGA_GRASS = reference("taiga_grass");
    @AsOf("2.3.0")
    public static final Feature GRASS = reference("grass");
    @AsOf("2.3.0")
    public static final Feature GRASS_JUNGLE = reference("grass_jungle");
    @AsOf("2.3.0")
    public static final Feature DEAD_BUSH = reference("dead_bush");
    @AsOf("2.3.0")
    public static final Feature DRY_GRASS = reference("dry_grass");
    @AsOf("2.3.0")
    public static final Feature MELON = reference("melon");
    @AsOf("2.3.0")
    public static final Feature WATERLILY = reference("waterlily");
    @AsOf("2.3.0")
    public static final Feature TALL_GRASS = reference("tall_grass");
    @AsOf("2.3.0")
    public static final Feature LARGE_FERN = reference("large_fern");
    @AsOf("2.3.0")
    public static final Feature BUSH = reference("bush");
    @AsOf("2.3.0")
    public static final Feature RED_SHRUB = reference("red_shrub");
    @AsOf("2.3.0")
    public static final Feature LEAF_LITTER = reference("leaf_litter");
    @AsOf("2.3.0")
    public static final Feature FIREFLY_BUSH = reference("firefly_bush");
    @AsOf("2.3.0")
    public static final Feature CACTUS = reference("cactus");
    @AsOf("2.3.0")
    public static final Feature SUGAR_CANE = reference("sugar_cane");
    @AsOf("2.3.0")
    public static final Feature FLOWER_DEFAULT = reference("flower_default");
    @AsOf("2.3.0")
    public static final Feature FLOWER_FLOWER_FOREST = reference("flower_flower_forest");
    @AsOf("2.3.0")
    public static final Feature FLOWER_SWAMP = reference("flower_swamp");
    @AsOf("2.3.0")
    public static final Feature FLOWER_PLAIN = reference("flower_plain");
    @AsOf("2.3.0")
    public static final Feature FLOWER_MEADOW = reference("flower_meadow");
    @AsOf("2.3.0")
    public static final Feature FLOWER_CHERRY = reference("flower_cherry");
    @AsOf("2.3.0")
    public static final Feature FLOWER_PALE_GARDEN = reference("flower_pale_garden");
    @AsOf("2.3.0")
    public static final Feature WILDFLOWER = reference("wildflower");
    @AsOf("2.3.0")
    public static final Feature FOREST_FLOWERS = reference("forest_flowers");
    @AsOf("2.3.0")
    public static final Feature PALE_FOREST_FLOWER = reference("pale_forest_flower");
    @AsOf("2.3.0")
    public static final Feature DARK_FOREST_VEGETATION = reference("dark_forest_vegetation");
    @AsOf("2.3.0")
    public static final Feature PALE_GARDEN_VEGETATION = reference("pale_garden_vegetation");
    @AsOf("2.3.0")
    public static final Feature PALE_MOSS_VEGETATION = reference("pale_moss_vegetation");
    @AsOf("2.3.0")
    public static final Feature PALE_MOSS_PATCH = reference("pale_moss_patch");
    @AsOf("2.3.0")
    public static final Feature PALE_MOSS_PATCH_BONEMEAL = reference("pale_moss_patch_bonemeal");
    @AsOf("2.3.0")
    public static final Feature TREES_FLOWER_FOREST = reference("trees_flower_forest");
    @AsOf("2.3.0")
    public static final Feature MEADOW_TREES = reference("meadow_trees");
    @AsOf("2.3.0")
    public static final Feature TREES_TAIGA = reference("trees_taiga");
    @AsOf("2.3.0")
    public static final Feature TREES_BADLANDS = reference("trees_badlands");
    @AsOf("2.3.0")
    public static final Feature TREES_GROVE = reference("trees_grove");
    @AsOf("2.3.0")
    public static final Feature TREES_SAVANNA = reference("trees_savanna");
    @AsOf("2.3.0")
    public static final Feature TREES_SNOWY = reference("trees_snowy");
    @AsOf("2.3.0")
    public static final Feature TREES_BIRCH = reference("trees_birch");
    @AsOf("2.3.0")
    public static final Feature BIRCH_TALL = reference("birch_tall");
    @AsOf("2.3.0")
    public static final Feature TREES_WINDSWEPT_HILLS = reference("trees_windswept_hills");
    @AsOf("2.3.0")
    public static final Feature TREES_WATER = reference("trees_water");
    @AsOf("2.3.0")
    public static final Feature TREES_BIRCH_AND_OAK_LEAF_LITTER = reference("trees_birch_and_oak_leaf_litter");
    @AsOf("2.3.0")
    public static final Feature TREES_PLAINS = reference("trees_plains");
    @AsOf("2.3.0")
    public static final Feature TREES_SPARSE_JUNGLE = reference("trees_sparse_jungle");
    @AsOf("2.3.0")
    public static final Feature TREES_OLD_GROWTH_SPRUCE_TAIGA = reference("trees_old_growth_spruce_taiga");
    @AsOf("2.3.0")
    public static final Feature TREES_OLD_GROWTH_PINE_TAIGA = reference("trees_old_growth_pine_taiga");
    @AsOf("2.3.0")
    public static final Feature TREES_JUNGLE = reference("trees_jungle");
    @AsOf("2.3.0")
    public static final Feature TREES_DAPPLED_FOREST = reference("trees_dappled_forest");
    @AsOf("2.3.0")
    public static final Feature BAMBOO_VEGETATION = reference("bamboo_vegetation");
    @AsOf("2.3.0")
    public static final Feature MUSHROOM_ISLAND_VEGETATION = reference("mushroom_island_vegetation");
    @AsOf("2.3.0")
    public static final Feature MANGROVE_VEGETATION = reference("mangrove_vegetation");

    Features() {
        throw new UnsupportedOperationException("Not intended for instantiation");
    }

    private static Feature reference(String path) {
        Feature keyed = Feature.reference(ResourceKey.minecraft(path));
        KeyChains.CONFIGURED_FEATURES.append(keyed);
        return keyed;
    }
}
