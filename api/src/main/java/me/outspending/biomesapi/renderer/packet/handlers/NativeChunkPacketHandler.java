package me.outspending.biomesapi.renderer.packet.handlers;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.renderer.packet.PacketHandler;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Internal handler for NMS-level chunk packet manipulation. Implemented by the NMS module.
 *
 * @version 2.0.0
 * @since 2.0.0
 * @author Jsinco
 */
@AsOf("2.0.0")
@ApiStatus.Internal
public interface NativeChunkPacketHandler {

    @ApiStatus.Internal
    WireProvider<NativeChunkPacketHandler> WIRE = WireProvider.create("me.outspending.biomesapi.*.renderer.packet.handlers.NmsNativeChunkPacketHandler");

    int CHUNK_SECTION_SIZE = 16;
    int CHUNK_SECTIONS = 4;

    /**
     * Rewrites a clientbound chunk packet to use the given custom biome's biome ID
     * and apply its block replacements.
     *
     * @param chunkData the NMS ClientboundLevelChunkPacketData (passed as Object since the
     *                  API module cannot reference NMS types directly)
     * @param customBiome the custom biome whose biome ID and block replacements should be applied
     * @param dimensionSectionCount how many sections the dimension has
     */
    void modifyChunkBiomes(@NotNull Object chunkData, @NotNull CustomBiome customBiome, @NotNull PacketHandler.DimensionSectionCount dimensionSectionCount);
}