package me.outspending.biomesapi.renderer.packet;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.renderer.packet.data.SnapshotChunkData;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Bridges the API module and the NMS module across the chunk-rewrite boundary.
 *
 * <p>The packet listener decides (cheaply, with no decoding) whether any phony biome could
 * apply to a chunk and, if so, produces a resolver. The NMS handler then decodes the chunk
 * exactly once, builds a {@link SnapshotChunkData}, and calls {@link #resolve(SnapshotChunkData)}.
 * The resolver runs the biome-aware conditionals against the decoded data and returns the
 * {@link CustomBiome} to apply, or {@code null} to leave the packet untouched (in which case
 * the NMS side skips serialization entirely).
 *
 * @version 2.2.0
 * @since 2.2.0
 * @author Jsinco
 */
@FunctionalInterface
@AsOf("2.2.0")
@ApiStatus.Internal
public interface PhonyBiomeResolver {

    /**
     * @param chunkData the decoded chunk view, exposing the real biome/block data
     * @return the custom biome to apply, or {@code null} to leave the packet unmodified
     */
    @Nullable CustomBiome resolve(@NotNull SnapshotChunkData chunkData);
}