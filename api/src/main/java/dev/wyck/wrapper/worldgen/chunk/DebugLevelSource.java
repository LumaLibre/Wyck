package dev.wyck.wrapper.worldgen.chunk;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.model.biome.Biome;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Minecraft's debug level source.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface DebugLevelSource extends ChunkGenerator {
    @ApiStatus.Internal
    ConstructWireProvider<DebugLevelSource> WIRE = ConstructWireProvider.construct("dev.wyck.wrapper.worldgen.chunk.DebugLevelSourceImpl");

    /**
     * Holder biome.
     * @return the holder biome.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Biome biome(); // vanilla-name: plains

    /**
     * Creates a new debug level source.
     * @param biome the biome
     * @return the debug level source
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static DebugLevelSource of(Biome biome) {
        return WIRE.construct(biome);
    }
}
