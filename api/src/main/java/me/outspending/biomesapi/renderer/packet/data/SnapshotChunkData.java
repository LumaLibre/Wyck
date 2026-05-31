package me.outspending.biomesapi.renderer.packet.data;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.misc.ChunkLocation;
import org.bukkit.ChunkSnapshot;
import org.bukkit.block.Biome;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * A read-only, lazily evaluated view of the chunk currently being sent to a player,
 * handed to biome conditionals so they can inspect the <em>real</em> biome/block data
 * before a phony biome is applied.
 *
 * <p>Every accessor is a method (not a field) on purpose: a backing may compute its
 * value lazily and memoize it, so a conditional that never reads the biome pays nothing,
 * and several conditionals that read the same value share a single computation.
 *
 * <p>Two backings exist:
 * <ul>
 *   <li>{@code NmsSnapshotChunkData} (NMS module) wraps the already-decoded chunk
 *       sections from the outgoing packet. Cheap, lazy, safe on the Netty thread. This is
 *       what the packet listener uses.</li>
 *   <li>{@link BukkitSnapshotChunkData}  wraps a real {@link ChunkSnapshot} the caller
 *       obtained elsewhere (on the main thread). Full block data, but only available if a
 *       snapshot was supplied.</li>
 * </ul>
 *
 * @version 2.2.0
 * @since 2.2.0
 * @author Jsinco
 */
@AsOf("2.2.0")
public interface SnapshotChunkData {

    /**
     * @return the chunk this data belongs to
     */
    @NotNull ChunkLocation location();

    /**
     * The biome currently in the packet at the legacy "center" sample (block 7, 0, 7).
     * Lazily computed and memoized.
     *
     * @return the source biome at the chunk center
     */
    @NotNull Biome centerBiome();

    /**
     * The biome at the given <em>chunk-relative</em> block coordinates.
     * {@code y} is relative to the bottom of the chunk column (section 0), not world Y.
     *
     * @param x chunk-relative block X (0-15)
     * @param y chunk-relative block Y (0 .. sectionCount*16 - 1)
     * @param z chunk-relative block Z (0-15)
     * @return the source biome at those coordinates
     */
    @NotNull Biome biomeAt(int x, int y, int z);

    /**
     * The full Bukkit {@link ChunkSnapshot}, if one is available.
     *
     * <p><strong>Expensive / restricted.</strong> The packet-backed implementation returns
     * {@link Optional#empty()} on purpose  building a real snapshot requires touching
     * the live world chunk, which is not safe on the Netty thread. A snapshot is only present
     * when the caller explicitly constructed a {@link BukkitSnapshotChunkData} from a snapshot
     * they obtained on the main thread. Most conditionals should prefer {@link #centerBiome()}
     * or {@link #biomeAt(int, int, int)} instead.
     *
     * @return the snapshot, or empty if none is available
     */
    @NotNull Optional<ChunkSnapshot> bukkitSnapshot();
}