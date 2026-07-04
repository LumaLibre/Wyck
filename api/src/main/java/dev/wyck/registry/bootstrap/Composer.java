package dev.wyck.registry.bootstrap;

import dev.wyck.annotations.AsOf;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Selects how custom biomes are registered into the biome registry during the bootstrap phase.
 * Both run before the registry freezes; they differ in mechanism and stability.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
@ApiStatus.Experimental
public enum Composer {
    /**
     * Injects biomes directly into the live Minecraft biome registry by patching Paper's registry
     * internals (compose event via {@code PaperRegistries}, using {@code sun.misc.Unsafe}).
     * Unsafe, not guaranteed to work on the next Java LTS.
     */
    INJECTOR,
    /**
     * Registers biomes through Paper's supported datapack pipeline ({@code DATAPACK_DISCOVERY}):
     * each biome is encoded to JSON and loaded as a generated pack.
     */
    DATAPACK
}