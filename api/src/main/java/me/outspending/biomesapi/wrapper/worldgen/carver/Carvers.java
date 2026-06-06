package me.outspending.biomesapi.wrapper.worldgen.carver;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.registry.BiomeResourceKey;

/**
 * Typed references to the built-in configured carvers registered by vanilla.
 * Each constant resolves, at conversion time, the corresponding entry in the
 * {@code CONFIGURED_CARVER} registry. Mirror of: {@code net.minecraft.data.worldgen.Carvers}.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@AsOf("2.3.0")
public final class Carvers {

    /**
     * The overworld cave carver.
     */
    @AsOf("2.3.0")
    public static final ConfiguredWorldCarver CAVE = reference("cave");

    /**
     * A sparser overworld cave carver.
     */
    @AsOf("2.3.0")
    public static final ConfiguredWorldCarver CAVE_EXTRA_UNDERGROUND = reference("cave_extra_underground");

    /**
     * The overworld canyon (ravine, etc.) carver.
     */
    @AsOf("2.3.0")
    public static final ConfiguredWorldCarver CANYON = reference("canyon");

    /**
     * The nether cave carver.
     */
    @AsOf("2.3.0")
    public static final ConfiguredWorldCarver NETHER_CAVE = reference("nether_cave");

    private static ConfiguredWorldCarver reference(String path) {
        return ConfiguredWorldCarver.reference(BiomeResourceKey.minecraft(path));
    }

    private Carvers() {
    }
}