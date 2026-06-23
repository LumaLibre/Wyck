package me.outspending.biomesapi.registry.level;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.keys.ResourceKey;
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
 * @see BootstrapBiomeRegistry#addToDimension(ResourceKey, ResourceKey, BiomeClimatePoint)
 * @see BootstrapBiomeRegistry#replaceInDimension(ResourceKey, ResourceKey, ResourceKey)
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
@ApiStatus.Experimental
public interface LevelStemEditor {

    @ApiStatus.Internal
    WireProvider<LevelStemEditor> RUNTIME = WireProvider.create("me.outspending.biomesapi.registry.level.RuntimeLevelStemEditor");

    // TODO: DATAPACK editor for bootstrap should eventually be merged into this?

    /**
     * Creates a new DimensionEditor backed by the runtime biome source swap implementation.
     *
     * @return a new DimensionEditor instance
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static LevelStemEditor create() {
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
    LevelStemEditor addToDimension(ResourceKey dimension, ResourceKey biome, BiomeClimatePoint point);

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
    LevelStemEditor replaceInDimension(ResourceKey dimension, ResourceKey target, ResourceKey replacement);

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