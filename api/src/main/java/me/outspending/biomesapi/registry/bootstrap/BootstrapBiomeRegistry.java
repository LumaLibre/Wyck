package me.outspending.biomesapi.registry.bootstrap;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.util.ThrowingRunnable;
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
    WireProvider<BootstrapBiomeRegistry> UNSAFE = WireProvider.create("me.outspending.biomesapi.registry.bootstrap.UnsafePaperBootstrapBiomeRegistry");
    @ApiStatus.Internal
    WireProvider<BootstrapBiomeRegistry> DATAPACK = WireProvider.create("me.outspending.biomesapi.registry.bootstrap.DatapackBootstrapBiomeRegistry");

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
    BootstrapBiomeRegistry queue(CustomBiome biome);

    /**
     * Defers the execution of a runnable until the registry is installed.
     * @param runnable the runnable to defer
     * @return this registry instance
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    BootstrapBiomeRegistry deferring(ThrowingRunnable runnable);
}