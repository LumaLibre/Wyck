package dev.wyck.renderer.packet.handlers;

import dev.wyck.misc.ChunkLocation;
import dev.wyck.renderer.packet.VirtualBiomeResolver;
import dev.wyck.renderer.packet.data.BlockReplacement;
import dev.wyck.renderer.packet.data.SnapshotChunkData;
import dev.wyck.renderer.packet.data.VirtualBiome;
import dev.wyck.renderer.packet.handlers.data.SnapshotChunkDataImpl;
import dev.wyck.util.internal.InternalReflectUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainerFactory;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.block.CraftBiome;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

@NullMarked
@ApiStatus.Internal
public final class NativeChunkPacketHandlerImpl implements NativeChunkPacketHandler {

    private static final int BIOME_CELLS_PER_SECTION = CHUNK_SECTIONS * CHUNK_SECTIONS * CHUNK_SECTIONS;

    private static final Field CHUNK_BUFFER_FIELD = resolveChunkBufferField();

    private static volatile @Nullable PaletteCache paletteCache;

    @Override
    public void modifyChunkBiomes(Object chunkDataObj, ChunkLocation chunkLocation, VirtualBiomeResolver resolver, int sectionCount) {
        ClientboundLevelChunkPacketData chunkData = (ClientboundLevelChunkPacketData) chunkDataObj;

        byte[] rewritten = rewriteSections(chunkData, chunkLocation, resolver, sectionCount);
        if (rewritten != null) {
            writeChunkBuffer(chunkData, rewritten);
        }
    }

    @Override
    public @Nullable Object rewriteChunkPacket(Object packetObj, ChunkLocation chunkLocation, VirtualBiomeResolver resolver, int sectionCount) {
        ClientboundLevelChunkWithLightPacket packet = (ClientboundLevelChunkWithLightPacket) packetObj;

        byte[] rewritten = rewriteSections(packet.chunkData(), chunkLocation, resolver, sectionCount);
        if (rewritten == null) {
            return null;
        }

        // Only the section buffer varies per player. Heightmaps, block entities and light stay
        // shared with the original, which other recipients may still be encoding.
        ClientboundLevelChunkPacketData chunkData = InternalReflectUtil.shallowCopy(packet.chunkData());
        writeChunkBuffer(chunkData, rewritten);

        return new ClientboundLevelChunkWithLightPacket(packet.x(), packet.z(), chunkData, packet.lightData());
    }

    private static byte @Nullable [] rewriteSections(ClientboundLevelChunkPacketData chunkData, ChunkLocation chunkLocation, VirtualBiomeResolver resolver, int sectionCount) {
        LevelChunkSection[] sections = extractChunkSections(chunkData, sectionCount);
        SnapshotChunkData snapshot = new SnapshotChunkDataImpl(chunkLocation, sections);

        boolean modified = resolver.positionDependent()
            ? applyPerCell(sections, snapshot, resolver)
            : applyChunkWide(sections, snapshot, resolver);

        return modified ? serializeChunkSections(sections) : null;
    }

    private static boolean applyChunkWide(LevelChunkSection[] sections, SnapshotChunkData snapshot, VirtualBiomeResolver resolver) {
        VirtualBiome phony = resolver.resolve(snapshot, 0, 0, 0);
        if (phony == null) {
            return false;
        }

        Rendered rendered = render(phony);
        for (LevelChunkSection section : sections) {
            for (int x = 0; x < CHUNK_SECTIONS; x++) {
                for (int y = 0; y < CHUNK_SECTIONS; y++) {
                    for (int z = 0; z < CHUNK_SECTIONS; z++) {
                        section.setNoiseBiome(x, y, z, rendered.biome());
                    }
                }
            }
            applyReplacements(section, rendered.replacements());
        }
        return true;
    }

    private static boolean applyPerCell(LevelChunkSection[] sections, SnapshotChunkData snapshot, VirtualBiomeResolver resolver) {
        VirtualBiome[] cells = new VirtualBiome[BIOME_CELLS_PER_SECTION];
        Map<VirtualBiome, Rendered> renderCache = new IdentityHashMap<>();
        boolean modified = false;

        for (int sectionIndex = 0; sectionIndex < sections.length; sectionIndex++) {
            LevelChunkSection section = sections[sectionIndex];
            int baseQuartY = sectionIndex * CHUNK_SECTIONS;
            boolean anyReplacements = false;
            int cell = 0;

            for (int x = 0; x < CHUNK_SECTIONS; x++) {
                for (int y = 0; y < CHUNK_SECTIONS; y++) {
                    for (int z = 0; z < CHUNK_SECTIONS; z++) {
                        VirtualBiome phony = resolver.resolve(snapshot, x, baseQuartY + y, z);
                        cells[cell++] = phony;
                        if (phony == null) {
                            continue;
                        }
                        Rendered rendered = render(phony, renderCache);
                        section.setNoiseBiome(x, y, z, rendered.biome());
                        modified = true;
                        anyReplacements |= rendered.replacements() != null;
                    }
                }
            }

            if (anyReplacements && applyReplacements(section, cells, renderCache)) {
                modified = true;
            }
        }
        return modified;
    }

    private static boolean applyReplacements(LevelChunkSection section, @Nullable Map<Block, BlockState> table) {
        if (table == null || !section.maybeHas((BlockState state) -> table.containsKey(state.getBlock()))) {
            return false;
        }

        boolean modified = false;
        for (int x = 0; x < CHUNK_SECTION_SIZE; x++) {
            for (int y = 0; y < CHUNK_SECTION_SIZE; y++) {
                for (int z = 0; z < CHUNK_SECTION_SIZE; z++) {
                    BlockState replacement = table.get(section.getBlockState(x, y, z).getBlock());
                    if (replacement != null) {
                        // Unchecked: this section was decoded from the packet and is thread-local.
                        section.setBlockState(x, y, z, replacement, false);
                        modified = true;
                    }
                }
            }
        }
        return modified;
    }

    private static boolean applyReplacements(LevelChunkSection section, VirtualBiome[] cells, Map<VirtualBiome, Rendered> renderCache) {
        if (!section.maybeHas((BlockState state) -> replacesAnywhere(renderCache, state.getBlock()))) {
            return false;
        }

        boolean modified = false;
        for (int x = 0; x < CHUNK_SECTION_SIZE; x++) {
            for (int y = 0; y < CHUNK_SECTION_SIZE; y++) {
                for (int z = 0; z < CHUNK_SECTION_SIZE; z++) {
                    VirtualBiome phony = cells[cellIndex(x >> 2, y >> 2, z >> 2)];
                    if (phony == null) {
                        continue;
                    }
                    Map<Block, BlockState> table = renderCache.get(phony).replacements();
                    if (table == null) {
                        continue;
                    }
                    BlockState replacement = table.get(section.getBlockState(x, y, z).getBlock());
                    if (replacement != null) {
                        section.setBlockState(x, y, z, replacement, false);
                        modified = true;
                    }
                }
            }
        }
        return modified;
    }

    private static boolean replacesAnywhere(Map<VirtualBiome, Rendered> renderCache, Block block) {
        for (Rendered rendered : renderCache.values()) {
            Map<Block, BlockState> table = rendered.replacements();
            if (table != null && table.containsKey(block)) {
                return true;
            }
        }
        return false;
    }

    private static int cellIndex(int quartX, int quartY, int quartZ) {
        return (quartX * CHUNK_SECTIONS + quartY) * CHUNK_SECTIONS + quartZ;
    }

    private static Rendered render(VirtualBiome phony, Map<VirtualBiome, Rendered> cache) {
        Rendered cached = cache.get(phony);
        if (cached == null) {
            cached = render(phony);
            cache.put(phony, cached);
        }
        return cached;
    }

    private static Rendered render(VirtualBiome phony) {
        org.bukkit.block.Biome bukkitBiome = phony.biome().bukkitBiome();
        Holder<net.minecraft.world.level.biome.Biome> minecraftBiome = CraftBiome.bukkitToMinecraftHolder(bukkitBiome);
        if (minecraftBiome == null) {
            throw new IllegalStateException("Failed to get Minecraft biome for " + bukkitBiome);
        }
        return new Rendered(minecraftBiome, replacementTable(phony));
    }

    private static @Nullable Map<Block, BlockState> replacementTable(VirtualBiome phony) {
        List<BlockReplacement> replacements = phony.blockReplacements();
        if (replacements.isEmpty()) {
            return null;
        }

        Map<Block, BlockState> table = new IdentityHashMap<>(replacements.size());
        for (BlockReplacement replacement : replacements) {
            Block original = CraftMagicNumbers.getBlock(replacement.originalBlock());
            Block target = CraftMagicNumbers.getBlock(replacement.replacementBlock());
            if (original == null || target == null) {
                continue;
            }
            // First rule for a given block wins, matching the original ordered-scan behaviour.
            table.putIfAbsent(original, target.defaultBlockState());
        }
        return table.isEmpty() ? null : table;
    }

    private static LevelChunkSection[] extractChunkSections(ClientboundLevelChunkPacketData chunkData, int sectionCount) {
        DedicatedServer server = ((CraftServer) Bukkit.getServer()).getServer();
        PalettedContainerFactory paletteFactory = paletteFactory(server.registryAccess());
        FriendlyByteBuf serializer = chunkData.getReadBuffer();

        LevelChunkSection[] sections = new LevelChunkSection[sectionCount];
        for (int i = 0; i < sections.length; i++) {
            LevelChunkSection section = new LevelChunkSection(paletteFactory);
            section.read(serializer);
            sections[i] = section;
        }
        return sections;
    }

    private static PalettedContainerFactory paletteFactory(RegistryAccess registries) {
        PaletteCache cached = paletteCache;
        if (cached != null && cached.registries() == registries) {
            return cached.factory();
        }
        PalettedContainerFactory factory = PalettedContainerFactory.create(registries);
        paletteCache = new PaletteCache(registries, factory);
        return factory;
    }

    @SuppressWarnings("deprecation")
    private static byte[] serializeChunkSections(LevelChunkSection[] sections) {
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

    private static void writeChunkBuffer(ClientboundLevelChunkPacketData chunkData, byte[] data) {
        try {
            CHUNK_BUFFER_FIELD.set(chunkData, data);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to update chunk data", e);
        }
    }

    private static Field resolveChunkBufferField() {
        try {
            Field field = ClientboundLevelChunkPacketData.class.getDeclaredField("buffer");
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private record PaletteCache(RegistryAccess registries, PalettedContainerFactory factory) {}

    private record Rendered(Holder<net.minecraft.world.level.biome.Biome> biome, @Nullable Map<Block, BlockState> replacements) {}
}
