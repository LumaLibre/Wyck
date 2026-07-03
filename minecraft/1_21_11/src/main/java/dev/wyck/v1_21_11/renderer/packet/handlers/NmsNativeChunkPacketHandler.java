package dev.wyck.v1_21_11.renderer.packet.handlers;

import dev.wyck.renderer.packet.data.PhonyCustomBiome;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.WireFactory;
import dev.wyck.misc.ChunkLocation;
import dev.wyck.renderer.packet.PacketHandler;
import dev.wyck.renderer.packet.PhonyBiomeResolver;
import dev.wyck.renderer.packet.data.BlockReplacement;
import dev.wyck.renderer.packet.data.SnapshotChunkData;
import dev.wyck.renderer.packet.handlers.NativeChunkPacketHandler;
import dev.wyck.v1_21_11.renderer.packet.data.NmsSnapshotChunkData;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainerFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.block.CraftBiome;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.lang.reflect.Field;
import java.util.List;

@NullMarked
@WireFactory
@AsOf("0.0.6")
@ApiStatus.Internal
public final class NmsNativeChunkPacketHandler implements NativeChunkPacketHandler {

    @Override
    public void modifyChunkBiomes(Object chunkDataObj, ChunkLocation chunkLocation, PhonyBiomeResolver resolver, PacketHandler.DimensionSectionCount dimensionSectionCount) {
        ClientboundLevelChunkPacketData chunkData = (ClientboundLevelChunkPacketData) chunkDataObj;


        LevelChunkSection[] sections = extractChunkSections(chunkData, dimensionSectionCount.getSectionCount());

        SnapshotChunkData snapshot = new NmsSnapshotChunkData(chunkLocation, sections);
        PhonyCustomBiome phony = resolver.resolve(snapshot);

        if (phony == null) {
            return;
        }

        org.bukkit.block.Biome bukkitBiome = phony.biome().bukkitBiome();
        Holder<net.minecraft.world.level.biome.Biome> minecraftBiome =
                CraftBiome.bukkitToMinecraftHolder(bukkitBiome);

        if (minecraftBiome == null) {
            throw new IllegalStateException("Failed to get Minecraft biome for " + bukkitBiome);
        }

        List<BlockReplacement> blockReplacements = phony.blockReplacements();

        for (LevelChunkSection section : sections) {
            for (int x = 0; x < CHUNK_SECTIONS; x++) {
                for (int y = 0; y < CHUNK_SECTIONS; y++) {
                    for (int z = 0; z < CHUNK_SECTIONS; z++) {
                        section.setBiome(x, y, z, minecraftBiome);
                    }
                }
            }

            if (blockReplacements.isEmpty()) {
                continue;
            }

            for (int x = 0; x < CHUNK_SECTION_SIZE; x++) {
                for (int y = 0; y < CHUNK_SECTION_SIZE; y++) {
                    for (int z = 0; z < CHUNK_SECTION_SIZE; z++) {
                        BlockState state = section.getBlockState(x, y, z);
                        Material asBukkitMaterial = state.getBukkitMaterial();

                        for (BlockReplacement replacement : blockReplacements) {
                            if (asBukkitMaterial != replacement.originalBlock()) continue;
                            BlockState newState = CraftMagicNumbers.getBlock(replacement.replacementBlock())
                                    .defaultBlockState();
                            section.setBlockState(x, y, z, newState);
                            break;
                        }
                    }
                }
            }
        }

        byte[] modifiedData = serializeChunkSections(sections);

        try {
            Field dataField = ClientboundLevelChunkPacketData.class.getDeclaredField("buffer");
            dataField.setAccessible(true);
            dataField.set(chunkData, modifiedData);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to update chunk data", e);
        }
    }

    private LevelChunkSection[] extractChunkSections(ClientboundLevelChunkPacketData chunkData, int sectionCount) {
        LevelChunkSection[] sections = new LevelChunkSection[sectionCount];
        DedicatedServer server = ((CraftServer) Bukkit.getServer()).getServer();
        FriendlyByteBuf serializer = chunkData.getReadBuffer();

        PalettedContainerFactory paletteFactory = PalettedContainerFactory.create(server.registryAccess());

        for (int i = 0; i < sections.length; i++) {
            sections[i] = new LevelChunkSection(paletteFactory);
        }

        for (LevelChunkSection section : sections) {
            section.read(serializer);
        }

        return sections;
    }

    private byte[] serializeChunkSections(LevelChunkSection[] sections) {
        int totalSize = 0;
        for (LevelChunkSection section : sections) {
            totalSize += section.getSerializedSize();
        }

        byte[] data = new byte[totalSize];
        ByteBuf buffer = Unpooled.wrappedBuffer(data);
        buffer.writerIndex(0);
        FriendlyByteBuf serializer = new FriendlyByteBuf(buffer);

        for (LevelChunkSection section : sections) {
            section.write(serializer);
        }

        return data;
    }
}