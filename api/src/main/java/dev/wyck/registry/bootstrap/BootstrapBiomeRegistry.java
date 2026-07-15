package dev.wyck.registry.bootstrap;

import dev.wyck.biome.Biome;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.WireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.level.LevelStemEditor;
import dev.wyck.util.ThrowingRunnable;
import dev.wyck.worldgen.climate.ClimatePoint;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Registers custom biomes during the Paper bootstrap/registry-load phase, so they enter the
 * biome registry before it freezes (no post-freezing). A biome that fails
 * to build should fail server startup.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
@ApiStatus.Experimental
@SuppressWarnings("UnstableApiUsage")
public interface BootstrapBiomeRegistry {

    @ApiStatus.Internal
    WireProvider<BootstrapBiomeRegistry> UNSAFE = WireProvider.create("dev.wyck.registry.bootstrap.UnsafePaperBootstrapBiomeRegistry");
    @ApiStatus.Internal
    WireProvider<BootstrapBiomeRegistry> DATAPACK = WireProvider.create("dev.wyck.registry.bootstrap.DatapackBootstrapBiomeRegistry");

    /**
     * Composes a new BootstrapBiomeRegistry instance.
     * @param context the bootstrap context
     * @param type the type of registry to compose
     * @return a new BootstrapBiomeRegistry instance
     * @since 2.3.0
     * @see Composer
     */
    @AsOf("2.3.0")
    static BootstrapBiomeRegistry compose(BootstrapContext context, Composer type) {
        BootstrapBiomeRegistry registry = type == Composer.INJECTOR ? injector() : datapack();
        registry.install(context);
        return registry;
    }

    /**
     * Creates a new UnsafePaperBootstrapBiomeRegistry instance.
     * @return a new instance of UnsafePaperBootstrapBiomeRegistry
     * @since 2.3.0
     * @apiNote {@link #install(BootstrapContext)} must be called after this method.
     */
    @AsOf("2.3.0")
    static BootstrapBiomeRegistry injector() {
        return UNSAFE.getNew();
    }

    /**
     * Creates a new DatapackBootstrapBiomeRegistry instance.
     * @return a new instance of DatapackBootstrapBiomeRegistry
     * @since 2.3.0
     * @apiNote {@link #install(BootstrapContext)} must be called after this method.
     */
    @AsOf("2.3.0")
    static BootstrapBiomeRegistry datapack() {
        return DATAPACK.getNew();
    }

    /**
     * Installs the registry into the given context.
     * @param context the context to install the registry into
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    BootstrapBiomeRegistry install(BootstrapContext context);

    /**
     * Queues a biome to be registered.
     * @param biome the biome to register
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    BootstrapBiomeRegistry queue(Biome biome);

    /**
     * Defers the execution of a runnable until the registry is installed.
     * @param runnable the runnable to defer
     * @return this registry instance
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    BootstrapBiomeRegistry deferring(ThrowingRunnable runnable);


    /**
     * Adds a biome to a dimension's biome distribution.
     * The biome will be added at the specified climate point and may replace an existing biome if the point overlaps with an existing one.
     * @param dimension the dimension to add the biome to
     * @param target the biome to add
     * @param placement the climate point to place the biome at
     * @return this registry instance
     * @throws UnsupportedOperationException if the implementation does not support this operation
     * @see LevelStemEditor#addToDimension(ResourceKey, ResourceKey, ClimatePoint)
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    BootstrapBiomeRegistry addToDimension(ResourceKey dimension, ResourceKey target, ClimatePoint placement) throws UnsupportedOperationException;

    /**
     * Replaces a biome in a dimension.
     * @param dimension the dimension to replace the biome in
     * @param target the biome to replace
     * @param replacement the biome to replace with
     * @return this registry instance
     * @throws UnsupportedOperationException if the implementation does not support this operation
     * @see LevelStemEditor#replaceInDimension(ResourceKey, ResourceKey, ResourceKey)
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    BootstrapBiomeRegistry replaceInDimension(ResourceKey dimension, ResourceKey target, ResourceKey replacement) throws UnsupportedOperationException;
}