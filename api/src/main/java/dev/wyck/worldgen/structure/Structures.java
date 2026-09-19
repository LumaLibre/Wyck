package dev.wyck.worldgen.structure;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.Generated;
import dev.wyck.keys.ResourceKey;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import org.jspecify.annotations.NullMarked;

/**
 * Auto-generated. Do not modify!
 * Run ./gradlew generateSources to regenerate.
 * <p>
 * Typed references that point to vanilla's built-in structures.
 * </p>
 *
 *
 * @since 3.4.0
 * @version 3.4.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("3.4.0")
@Generated("2026-09-13T05:38:38.767398Z")
public final class Structures {
    // From: BuiltinStructures
    @AsOf("3.4.0")
    public static final Structure PILLAGER_OUTPOST = reference("pillager_outpost");
    @AsOf("3.4.0")
    public static final Structure MINESHAFT = reference("mineshaft");
    @AsOf("3.4.0")
    public static final Structure MINESHAFT_MESA = reference("mineshaft_mesa");
    @AsOf("3.4.0")
    public static final Structure WOODLAND_MANSION = reference("mansion");
    @AsOf("3.4.0")
    public static final Structure JUNGLE_TEMPLE = reference("jungle_pyramid");
    @AsOf("3.4.0")
    public static final Structure DESERT_PYRAMID = reference("desert_pyramid");
    @AsOf("3.4.0")
    public static final Structure IGLOO = reference("igloo");
    @AsOf("3.4.0")
    public static final Structure SHIPWRECK = reference("shipwreck");
    @AsOf("3.4.0")
    public static final Structure SHIPWRECK_BEACHED = reference("shipwreck_beached");
    @AsOf("3.4.0")
    public static final Structure SWAMP_HUT = reference("swamp_hut");
    @AsOf("3.4.0")
    public static final Structure STRONGHOLD = reference("stronghold");
    @AsOf("3.4.0")
    public static final Structure OCEAN_MONUMENT = reference("monument");
    @AsOf("3.4.0")
    public static final Structure OCEAN_RUIN_COLD = reference("ocean_ruin_cold");
    @AsOf("3.4.0")
    public static final Structure OCEAN_RUIN_WARM = reference("ocean_ruin_warm");
    @AsOf("3.4.0")
    public static final Structure FORTRESS = reference("fortress");
    @AsOf("3.4.0")
    public static final Structure NETHER_FOSSIL = reference("nether_fossil");
    @AsOf("3.4.0")
    public static final Structure END_CITY = reference("end_city");
    @AsOf("3.4.0")
    public static final Structure BURIED_TREASURE = reference("buried_treasure");
    @AsOf("3.4.0")
    public static final Structure BASTION_REMNANT = reference("bastion_remnant");
    @AsOf("3.4.0")
    public static final Structure VILLAGE_PLAINS = reference("village_plains");
    @AsOf("3.4.0")
    public static final Structure VILLAGE_DESERT = reference("village_desert");
    @AsOf("3.4.0")
    public static final Structure VILLAGE_SAVANNA = reference("village_savanna");
    @AsOf("3.4.0")
    public static final Structure VILLAGE_SNOWY = reference("village_snowy");
    @AsOf("3.4.0")
    public static final Structure VILLAGE_TAIGA = reference("village_taiga");
    @AsOf("3.4.0")
    public static final Structure RUINED_PORTAL_STANDARD = reference("ruined_portal");
    @AsOf("3.4.0")
    public static final Structure RUINED_PORTAL_DESERT = reference("ruined_portal_desert");
    @AsOf("3.4.0")
    public static final Structure RUINED_PORTAL_JUNGLE = reference("ruined_portal_jungle");
    @AsOf("3.4.0")
    public static final Structure RUINED_PORTAL_SWAMP = reference("ruined_portal_swamp");
    @AsOf("3.4.0")
    public static final Structure RUINED_PORTAL_MOUNTAIN = reference("ruined_portal_mountain");
    @AsOf("3.4.0")
    public static final Structure RUINED_PORTAL_OCEAN = reference("ruined_portal_ocean");
    @AsOf("3.4.0")
    public static final Structure RUINED_PORTAL_NETHER = reference("ruined_portal_nether");
    @AsOf("3.4.0")
    public static final Structure ANCIENT_CITY = reference("ancient_city");
    @AsOf("3.4.0")
    public static final Structure TRAIL_RUINS = reference("trail_ruins");
    @AsOf("3.4.0")
    public static final Structure TRIAL_CHAMBERS = reference("trial_chambers");

    Structures() {
        throw new UnsupportedOperationException("Not intended for instantiation");
    }

    private static Structure reference(String path) {
        return Structure.reference(ResourceKey.minecraft(path));
    }
}
