package me.outspending.biomesapi.packet.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.packet.PacketHandler;
import me.outspending.biomesapi.packet.data.BlockReplacement;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainerFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.block.CraftBiome;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

/**
 * Internal helper class for packet handling related to chunk data and biome modification.
 *
 * @version 0.0.6
 * @since 0.0.6
 * @author Jsinco
 */
@AsOf("0.0.6")
class PacketHandlerHelper {

    static PacketHandlerHelper INSTANCE = new PacketHandlerHelper();

    static int CHUNK_SECTION_SIZE = 16; // Each chunk section is 16x16x16 blocks
    static int CHUNK_SECTIONS = 4; // Each chunk section has 4x4x4 = 64 biome entries

    @AsOf("0.0.6")
    LevelChunkSection[] extractChunkSections(ClientboundLevelChunkPacketData chunkData, int sectionCount) {
        LevelChunkSection[] sections = new LevelChunkSection[sectionCount];
        DedicatedServer server = ((CraftServer) Bukkit.getServer()).getServer();
        FriendlyByteBuf serializer = chunkData.getReadBuffer();

        // Create palette factory
        PalettedContainerFactory paletteFactory = PalettedContainerFactory.create(server.registryAccess());

        // Initialize sections
        for (int i = 0; i < sections.length; i++) {
            sections[i] = new LevelChunkSection(paletteFactory);
        }

        // Read section data from the serializer
        for (LevelChunkSection section : sections) {
            section.read(serializer);
        }

        return sections;
    }

    @AsOf("0.0.6")
    byte[] serializeChunkSections(LevelChunkSection[] sections) {
        // Calculate total size needed
        int totalSize = 0;
        for (LevelChunkSection section : sections) {
            totalSize += section.getSerializedSize();
        }

        // Create byte buffer
        byte[] data = new byte[totalSize];
        ByteBuf buffer = Unpooled.wrappedBuffer(data);
        buffer.writerIndex(0);
        FriendlyByteBuf serializer = new FriendlyByteBuf(buffer);

        // Write each section
        for (LevelChunkSection section : sections) {
            section.write(serializer); // deprecated but no alternative
        }

        return data;
    }

    @AsOf("0.0.6")
    void modifyChunkBiomes(ClientboundLevelChunkPacketData chunkData, CustomBiome customBiome, PacketHandler.DimensionSectionCount dimensionSectionCount) {

        // Extract chunk sections safely
        LevelChunkSection[] sections = this.extractChunkSections(chunkData, dimensionSectionCount.getSectionCount());

        // Get the Minecraft biome ID for the custom biome
        Biome bukkitBiome = customBiome.toBukkitBiome();
        Holder<net.minecraft.world.level.biome.@NotNull Biome> minecraftBiome = CraftBiome.bukkitToMinecraftHolder(bukkitBiome);

        if (minecraftBiome == null) {
            throw new IllegalStateException("Failed to get Minecraft biome for " + bukkitBiome);
        }

        BlockReplacement[] blockReplacements = customBiome.getBlockReplacements();

        // Replace all biomes in each section
        for (LevelChunkSection section : sections) {
            // Each section has 4x4x4 = 64 biome entries
            for (int x = 0; x < CHUNK_SECTIONS; x++) {
                for (int y = 0; y < CHUNK_SECTIONS; y++) {
                    for (int z = 0; z < CHUNK_SECTIONS; z++) {
                        section.setBiome(x, y, z, minecraftBiome);
                    }
                }
            }

            if (blockReplacements.length == 0) {
                continue;
            }

            for (int x = 0; x < CHUNK_SECTION_SIZE; x++) {
                for (int y = 0; y < CHUNK_SECTION_SIZE; y++) {
                    for (int z = 0; z < CHUNK_SECTION_SIZE; z++) {
                        BlockState state = section.getBlockState(x, y, z);
                        Material asBukkitMaterial = state.getBukkitMaterial();

                        for (BlockReplacement replacement : blockReplacements) {
                            BlockState newState = CraftMagicNumbers.getBlock(replacement.replacementBlock()).defaultBlockState();

                            if (asBukkitMaterial == replacement.originalBlock()) {
                                section.setBlockState(x, y, z, newState);
                                break;
                            }
                        }
                    }
                }
            }
        }

        // Serialize the modified sections back to bytes
        byte[] modifiedData = this.serializeChunkSections(sections);

        // Use reflection to set the modified data back
        try {
            Field dataField = ClientboundLevelChunkPacketData.class.getDeclaredField("buffer");
            dataField.setAccessible(true);
            dataField.set(chunkData, modifiedData);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to update chunk data", e);
        }
    }

}
