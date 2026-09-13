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
 * Typed references that point to vanilla's built-in structure sets.
 * </p>
 *
 *
 * @since 3.0.0
 * @version 3.4.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("3.0.0")
@Generated("2026-09-13T05:38:38.768808Z")
public final class StructureSets {
    // From: BuiltinStructureSets
    @AsOf("3.0.0")
    public static final StructureSet VILLAGES = reference("villages");
    @AsOf("3.0.0")
    public static final StructureSet DESERT_PYRAMIDS = reference("desert_pyramids");
    @AsOf("3.0.0")
    public static final StructureSet IGLOOS = reference("igloos");
    @AsOf("3.0.0")
    public static final StructureSet JUNGLE_TEMPLES = reference("jungle_temples");
    @AsOf("3.0.0")
    public static final StructureSet SWAMP_HUTS = reference("swamp_huts");
    @AsOf("3.0.0")
    public static final StructureSet PILLAGER_OUTPOSTS = reference("pillager_outposts");
    @AsOf("3.0.0")
    public static final StructureSet OCEAN_MONUMENTS = reference("ocean_monuments");
    @AsOf("3.0.0")
    public static final StructureSet WOODLAND_MANSIONS = reference("woodland_mansions");
    @AsOf("3.0.0")
    public static final StructureSet BURIED_TREASURES = reference("buried_treasures");
    @AsOf("3.0.0")
    public static final StructureSet MINESHAFTS = reference("mineshafts");
    @AsOf("3.0.0")
    public static final StructureSet RUINED_PORTALS = reference("ruined_portals");
    @AsOf("3.0.0")
    public static final StructureSet SHIPWRECKS = reference("shipwrecks");
    @AsOf("3.0.0")
    public static final StructureSet OCEAN_RUINS = reference("ocean_ruins");
    @AsOf("3.0.0")
    public static final StructureSet NETHER_COMPLEXES = reference("nether_complexes");
    @AsOf("3.0.0")
    public static final StructureSet NETHER_FOSSILS = reference("nether_fossils");
    @AsOf("3.0.0")
    public static final StructureSet END_CITIES = reference("end_cities");
    @AsOf("3.0.0")
    public static final StructureSet ANCIENT_CITIES = reference("ancient_cities");
    @AsOf("3.0.0")
    public static final StructureSet STRONGHOLDS = reference("strongholds");
    @AsOf("3.0.0")
    public static final StructureSet TRAIL_RUINS = reference("trail_ruins");
    @AsOf("3.0.0")
    public static final StructureSet TRIAL_CHAMBERS = reference("trial_chambers");

    StructureSets() {
        throw new UnsupportedOperationException("Not intended for instantiation");
    }

    private static StructureSet reference(String path) {
        return StructureSet.reference(ResourceKey.minecraft(path));
    }
}
