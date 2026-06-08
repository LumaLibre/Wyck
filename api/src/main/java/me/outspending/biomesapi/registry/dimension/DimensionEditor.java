package me.outspending.biomesapi.registry.dimension;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.registry.bootstrap.BootstrapBiomeRegistry;
import me.outspending.biomesapi.wrapper.worldgen.climate.BiomeClimatePoint;
import org.bukkit.World;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Edits a loaded world's biome distribution at runtime. Unlike the bootstrap datapack, this
 * reads the live biome source off each world's generator and swaps in an edited one, so it works
 * for any dimension including the end and custom dimensions.
 *
 * @see BootstrapBiomeRegistry#addToDimension(BiomeResourceKey, BiomeResourceKey, BiomeClimatePoint)
 * @see BootstrapBiomeRegistry#replaceInDimension(BiomeResourceKey, BiomeResourceKey, BiomeResourceKey)
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
@ApiStatus.Experimental
public interface DimensionEditor {

    @ApiStatus.Internal
    WireProvider<DimensionEditor> RUNTIME = WireProvider.create("me.outspending.biomesapi.registry.dimension.RuntimeDimensionEditor");

    // TODO: DATAPACK editor for bootstrap should eventually be merged into this?

    /**
     * Creates a new DimensionEditor backed by the runtime biome source swap implementation.
     *
     * @return a new DimensionEditor instance
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static DimensionEditor create() {
        return RUNTIME.getNew();
    }

    /**
     * Queues a biome addition to a dimension.
     *
     * @param dimension the dimension to edit
     * @param biome the biome to add
     * @param point where in climate space the biome wins
     * @return this editor instance
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    DimensionEditor addToDimension(BiomeResourceKey dimension, BiomeResourceKey biome, BiomeClimatePoint point);

    /**
     * Queues a biome replacement in a dimension.
     *
     * @param dimension the dimension to edit
     * @param target the biome key to replace
     * @param replacement the biome key to generate in its place
     * @return this editor instance
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    DimensionEditor replaceInDimension(BiomeResourceKey dimension, BiomeResourceKey target, BiomeResourceKey replacement);

    /**
     * Applies all queued edits to every loaded world whose dimension matches. Worlds loaded after
     * this call are not touched, use {@link #apply(World)}.
     *
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    void apply();

    /**
     * Applies the queued edits matching the given world's dimension to its live generator.
     *
     * @param world the world to edit
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    void apply(World world);
}