package dev.wyck.renderer.packet.handlers;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.WireProvider;
import dev.wyck.misc.ChunkLocation;
import dev.wyck.renderer.packet.PacketHandler;
import dev.wyck.renderer.packet.VirtualBiomeResolver;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Internal handler for NMS-level chunk packet manipulation. Implemented by the NMS module.
 *
 * @version 3.4.0
 * @since 2.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.0.0")
@ApiStatus.Internal
public interface NativeChunkPacketHandler {

    static NativeChunkPacketHandler instance() {
        record Holder() {
            static final WireProvider<NativeChunkPacketHandler> WIRE = WireProvider.create("dev.wyck.*?.renderer.packet.handlers.NativeChunkPacketHandlerImpl");
        }
        return Holder.WIRE.get();
    }

    int CHUNK_SECTION_SIZE = 16;
    int CHUNK_SECTIONS = 4;

    /**
     * Decodes a clientbound chunk packet exactly once, lets the supplied resolver inspect the
     * real biome/block data and choose a custom biome, and  only if one is chosen 
     * rewrites the packet to use that biome's ID and apply its block replacements.
     *
     * <p>If the resolver returns {@code null}, the packet is left untouched and no serialization
     * or reflection write occurs.
     *
     * @param chunkData the NMS ClientboundLevelChunkPacketData (passed as Object since the
     *                  API module cannot reference NMS types directly)
     * @param chunkLocation the chunk being sent, used to build the snapshot view
     * @param resolver chooses the custom biome to apply based on the decoded chunk data,
     *                 or returns {@code null} to leave the packet unmodified
     * @param sectionCount how many sections the dimension has
     * @since 3.3.0
     * @apiNote This rewrites {@code chunkData} in place. The server can send one packet instance to
     *          several players, and each of them then receives this player's rewrite; use
     *          {@link #rewriteChunkPacket(Object, ChunkLocation, VirtualBiomeResolver, int)} for
     *          anything that varies per player.
     */
    @AsOf("3.3.0")
    void modifyChunkBiomes(Object chunkData, ChunkLocation chunkLocation, VirtualBiomeResolver resolver, int sectionCount);

    /**
     * Rewrites a clientbound chunk packet for one player without touching the packet itself.
     *
     * <p>The server may send a single packet instance to several players -- Paper's chunk refresh
     * and {@code BiomeUpdater} both do -- so a rewrite has to land in a copy, or every later
     * recipient receives the first player's biomes. The copy shares everything except the
     * rewritten section data with the original.
     *
     * @param packet the NMS ClientboundLevelChunkWithLightPacket (passed as Object since the API
     *               module cannot reference NMS types directly)
     * @param chunkLocation the chunk being sent, used to build the snapshot view
     * @param resolver chooses the custom biome to apply based on the decoded chunk data
     * @param sectionCount how many sections the dimension has
     * @return a rewritten copy to send in the packet's place, or {@code null} if nothing applied
     *         and the original can go out as it is
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    @Nullable Object rewriteChunkPacket(Object packet, ChunkLocation chunkLocation, VirtualBiomeResolver resolver, int sectionCount);

    /**
     * Rewrites a chunk using one of the legacy environment height presets.
     * @param chunkData the NMS chunk packet data
     * @param chunkLocation the chunk being sent
     * @param resolver the virtual-biome resolver
     * @param dimensionSectionCount the legacy dimension section count
     * @since 2.2.0
     */
    @AsOf("2.2.0")
    default void modifyChunkBiomes(Object chunkData, ChunkLocation chunkLocation, VirtualBiomeResolver resolver, PacketHandler.DimensionSectionCount dimensionSectionCount) {
        modifyChunkBiomes(chunkData, chunkLocation, resolver, dimensionSectionCount.getSectionCount());
    }
}
