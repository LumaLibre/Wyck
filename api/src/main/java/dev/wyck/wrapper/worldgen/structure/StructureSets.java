package dev.wyck.wrapper.worldgen.structure;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.Generated;
import dev.wyck.keys.ResourceKey;
import org.jspecify.annotations.NullMarked;

/**
 * Auto-generated. Do not modify!
 * Run ./gradlew generateSources to regenerate.
 * <p>
 * Typed references that point to vanilla's built-in structure sets.
 * </p>
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("3.0.0")
@Generated("2026-07-14T03:30:26.723377Z")
public final class StructureSets {

    // From: BuiltinStructureSets 
    @AsOf("3.0.0")
    public static final ResourceKey VILLAGES = reference("villages");
    @AsOf("3.0.0")
    public static final ResourceKey DESERT_PYRAMIDS = reference("desert_pyramids");
    @AsOf("3.0.0")
    public static final ResourceKey IGLOOS = reference("igloos");
    @AsOf("3.0.0")
    public static final ResourceKey JUNGLE_TEMPLES = reference("jungle_temples");
    @AsOf("3.0.0")
    public static final ResourceKey SWAMP_HUTS = reference("swamp_huts");
    @AsOf("3.0.0")
    public static final ResourceKey PILLAGER_OUTPOSTS = reference("pillager_outposts");
    @AsOf("3.0.0")
    public static final ResourceKey OCEAN_MONUMENTS = reference("ocean_monuments");
    @AsOf("3.0.0")
    public static final ResourceKey WOODLAND_MANSIONS = reference("woodland_mansions");
    @AsOf("3.0.0")
    public static final ResourceKey BURIED_TREASURES = reference("buried_treasures");
    @AsOf("3.0.0")
    public static final ResourceKey MINESHAFTS = reference("mineshafts");
    @AsOf("3.0.0")
    public static final ResourceKey RUINED_PORTALS = reference("ruined_portals");
    @AsOf("3.0.0")
    public static final ResourceKey SHIPWRECKS = reference("shipwrecks");
    @AsOf("3.0.0")
    public static final ResourceKey OCEAN_RUINS = reference("ocean_ruins");
    @AsOf("3.0.0")
    public static final ResourceKey NETHER_COMPLEXES = reference("nether_complexes");
    @AsOf("3.0.0")
    public static final ResourceKey NETHER_FOSSILS = reference("nether_fossils");
    @AsOf("3.0.0")
    public static final ResourceKey END_CITIES = reference("end_cities");
    @AsOf("3.0.0")
    public static final ResourceKey ANCIENT_CITIES = reference("ancient_cities");
    @AsOf("3.0.0")
    public static final ResourceKey STRONGHOLDS = reference("strongholds");
    @AsOf("3.0.0")
    public static final ResourceKey TRAIL_RUINS = reference("trail_ruins");
    @AsOf("3.0.0")
    public static final ResourceKey TRIAL_CHAMBERS = reference("trial_chambers");

    private static ResourceKey reference(String path) {
        return (ResourceKey.minecraft(path));
    }

    private StructureSets() {
        throw new UnsupportedOperationException("Not intended for instantiation");
    }
}
