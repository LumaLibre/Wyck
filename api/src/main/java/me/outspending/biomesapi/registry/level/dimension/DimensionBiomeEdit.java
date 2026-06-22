package me.outspending.biomesapi.registry.level.dimension;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.wrapper.worldgen.climate.BiomeClimatePoint;

/**
 * Represents an edit to a dimension's biome distribution, either adding a biome at a climate point or replacing one biome with another.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@AsOf("2.3.0")
public sealed interface DimensionBiomeEdit permits DimensionBiomeEdit.Add, DimensionBiomeEdit.Replace {

    /**
     * The key of the dimension to edit.
     * @return the dimension key.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    ResourceKey dimension();


    /**
     * Adds a biome to a dimension.
     * @param dimension the dimension to add the biome to
     * @param biome the biome to add
     * @param point the climate point of the biome
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    record Add(ResourceKey dimension, ResourceKey biome, BiomeClimatePoint point) implements DimensionBiomeEdit {
    }

    /**
     * Replaces a biome in a dimension.
     * @param dimension the dimension to replace the biome in
     * @param target the biome to replace
     * @param replacement the biome to replace with
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    record Replace(ResourceKey dimension, ResourceKey target, ResourceKey replacement) implements DimensionBiomeEdit {
    }
}