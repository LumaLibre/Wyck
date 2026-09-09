package dev.wyck.renderer.packet.handlers;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.ClientVersion;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.protocol.world.chunk.BaseChunk;
import com.github.retrooper.packetevents.protocol.world.chunk.Column;
import com.github.retrooper.packetevents.protocol.world.chunk.impl.v_1_18.Chunk_v1_18;
import com.github.retrooper.packetevents.protocol.world.chunk.palette.DataPalette;
import com.github.retrooper.packetevents.protocol.world.chunk.palette.GlobalPalette;
import com.github.retrooper.packetevents.protocol.world.chunk.palette.Palette;
import com.github.retrooper.packetevents.protocol.world.states.WrappedBlockState;
import com.github.retrooper.packetevents.resources.ResourceLocation;
import com.github.retrooper.packetevents.util.Vector3i;
import com.github.retrooper.packetevents.util.mappings.IRegistry;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerBlockChange;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerChunkData;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerMultiBlockChange;
import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.misc.ChunkLocation;
import dev.wyck.renderer.packet.PacketHandler;
import dev.wyck.renderer.packet.VirtualBiomeCollector;
import dev.wyck.renderer.packet.VirtualBiomeResolver;
import dev.wyck.renderer.packet.data.BlockReplacement;
import dev.wyck.renderer.packet.data.SnapshotChunkData;
import dev.wyck.renderer.packet.data.VirtualBiome;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static dev.wyck.renderer.packet.handlers.NativeChunkPacketHandler.CHUNK_SECTIONS;
import static dev.wyck.renderer.packet.handlers.NativeChunkPacketHandler.CHUNK_SECTION_SIZE;


// Credits to: bitmochibit/Defcon
/**
 * PacketEvents-based implementation of the PacketHandler interface.
 *
 * @version 2.1.0
 * @since 0.0.6
 * @author Jsinco
 */
@NullMarked
@AsOf("0.0.19")
@ApiStatus.Internal
public class PacketEventsPacketHandler implements PacketHandler {

    // TODO: Abstract out common code

    private static final int BIOME_CELLS_PER_SECTION = CHUNK_SECTIONS * CHUNK_SECTIONS * CHUNK_SECTIONS;
    private static final int[] EMPTY_STATE_IDS = new int[0];

    private final VirtualBiomeCollector collector;
    private final PacketListener[] packetListeners;
    private final PacketListenerPriority packetListenerPriority;

    @AsOf("2.1.0")
    public PacketEventsPacketHandler(PacketHandler.Priority priority, VirtualBiomeCollector collector) {
        this.collector = collector;
        this.packetListeners = new PacketListener[] {
                new MapChunkPacketListener(this),
                new BlockChangePacketListener(this),
                new MultiBlockChangePacketListener(this)
        };
        this.packetListenerPriority = priority.getDelegatePriority(PacketListenerPriority.class);
    }

    @AsOf("0.0.19")
    public PacketEventsPacketHandler(PacketHandler.Priority priority) {
        this(priority, new VirtualBiomeCollector());
    }


    @Override
    public PacketHandler register() {
        for (PacketListener listener : packetListeners) {
            PacketEvents.getAPI().getEventManager().registerListener(listener, packetListenerPriority);
        }
        return this;
    }

    @Override
    public PacketHandler unregister() {
        for (PacketListener listener : packetListeners) {
            PacketEvents.getAPI().getEventManager().unregisterListener(listener.asAbstract(packetListenerPriority));
        }
        return this;
    }

    @Override
    public PacketHandler appendBiome(VirtualBiome biome) {
        collector.appendBiome(biome);
        return this;
    }

    @Override
    public boolean removeBiome(VirtualBiome biome) {
        return collector.removeBiome(biome);
    }

    @Override
    public boolean removeBiome(ResourceKey biomeKey) {
        return collector.removeBiome(biomeKey);
    }

    @Override
    public boolean hasBiome(VirtualBiome biome) {
        return collector.hasBiome(biome);
    }

    @Override
    public boolean hasBiome(ResourceKey biomeKey) {
        return collector.hasBiome(biomeKey);
    }

    @Override
    public PacketHandler clearBiomes() {
        collector.clearBiomes();
        return this;
    }


    // Credits to: mrmcyeet
    @AsOf("0.0.19")
    private static class PacketEventsBukkitMaterials {
        // Concurrent: chunk packets for different players are converted on different Netty threads.
        private static final Map<Material, WrappedBlockState> materialToWrappedBlockStateCache = new ConcurrentHashMap<>();
        private static final Map<Integer, Material> idToMaterialCache = new ConcurrentHashMap<>();

        public static WrappedBlockState fromCachedBukkitBlockData(Material blockType) {
            return materialToWrappedBlockStateCache.computeIfAbsent(blockType, key ->
                    SpigotConversionUtil.fromBukkitBlockData(key.createBlockData())
            );
        }

        public static int cachedGlobalId(Material blockType) {
            return fromCachedBukkitBlockData(blockType).getGlobalId();
        }

        public static Material toCachedBukkitBlockData(int blockId) {
            return idToMaterialCache.computeIfAbsent(blockId, key ->
                    SpigotConversionUtil.toBukkitBlockData(WrappedBlockState.getByGlobalId(key))
                            .createBlockState()
                            .getType()
            );
        }
    }

    private record RenderedBiome(int biomeId, int[] originalStateIds, int[] replacementStateIds) {

        boolean hasReplacements() {
            return this.originalStateIds.length > 0;
        }

        int replacementFor(int stateId) {
            int[] originals = this.originalStateIds;
            for (int i = 0; i < originals.length; i++) {
                if (originals[i] == stateId) {
                    return this.replacementStateIds[i];
                }
            }
            return -1;
        }
    }

    private static final class BiomeRenderer {

        private final Map<VirtualBiome, RenderedBiome> cache = new IdentityHashMap<>();
        private final User user;

        private @Nullable IRegistry<?> biomeRegistry;

        BiomeRenderer(User user) {
            this.user = user;
        }

        RenderedBiome render(VirtualBiome phony) {
            RenderedBiome cached = this.cache.get(phony);
            if (cached == null) {
                cached = build(phony);
                this.cache.put(phony, cached);
            }
            return cached;
        }

        boolean replacesAnywhere(int stateId) {
            for (RenderedBiome rendered : this.cache.values()) {
                if (rendered.replacementFor(stateId) >= 0) {
                    return true;
                }
            }
            return false;
        }

        @SuppressWarnings("PatternValidation")
        private RenderedBiome build(VirtualBiome phony) {
            ResourceKey biomeKey = phony.biomeResourceKey();
            MappedEntity element = biomeRegistry().getByName(
                new ResourceLocation(biomeKey.namespace(), biomeKey.path())
            );
            Preconditions.checkNotNull(
                element,
                "Biome " + biomeKey.resourceLocation() + " not found in registry for user " + this.user.getName()
            );
            int biomeId = element.getId(this.user.getClientVersion());

            List<BlockReplacement> replacements = phony.blockReplacements();
            if (replacements.isEmpty()) {
                return new RenderedBiome(biomeId, EMPTY_STATE_IDS, EMPTY_STATE_IDS);
            }

            int[] originals = new int[replacements.size()];
            int[] targets = new int[replacements.size()];
            int count = 0;
            for (BlockReplacement replacement : replacements) {
                int original = PacketEventsBukkitMaterials.cachedGlobalId(replacement.originalBlock());
                if (contains(originals, count, original)) {
                    continue; // first rule for a state wins, matching the original ordered scan
                }
                originals[count] = original;
                targets[count] = PacketEventsBukkitMaterials.cachedGlobalId(replacement.replacementBlock());
                count++;
            }
            return new RenderedBiome(
                biomeId,
                count == originals.length ? originals : Arrays.copyOf(originals, count),
                count == targets.length ? targets : Arrays.copyOf(targets, count)
            );
        }

        private IRegistry<?> biomeRegistry() {
            IRegistry<?> registry = this.biomeRegistry;
            if (registry == null) {
                registry = this.user.getRegistry(ResourceLocation.minecraft(MapChunkPacketListener.REGISTRY_KEY));
                Preconditions.checkNotNull(registry, "Biome registry not found for user " + this.user.getName());
                this.biomeRegistry = registry;
            }
            return registry;
        }

        private static boolean contains(int[] values, int length, int value) {
            for (int i = 0; i < length; i++) {
                if (values[i] == value) {
                    return true;
                }
            }
            return false;
        }
    }

    @AsOf("0.0.19")
    private static class MapChunkPacketListener implements PacketListener {

        static final String REGISTRY_KEY = "worldgen/biome";

        private final PacketEventsPacketHandler context;

        public MapChunkPacketListener(PacketEventsPacketHandler context) {
            this.context = context;
        }

        @Override
        public void onPacketSend(PacketSendEvent event) {
            if (event.getPacketType() != PacketType.Play.Server.CHUNK_DATA) {
                return;
            }
            WrapperPlayServerChunkData wrapperPlayServerChunkData = new WrapperPlayServerChunkData(event);
            Column column = wrapperPlayServerChunkData.getColumn();

            User user = event.getUser();
            Player player = event.getPlayer();
            ChunkLocation chunkLocation = ChunkLocation.of(column.getX(), column.getZ());

            VirtualBiomeResolver resolver = context.collector.resolverFor(player, chunkLocation);
            if (resolver == null) {
                return;
            }

            SnapshotChunkData snapshot = new PacketEventsSnapshotChunkData(chunkLocation, column, user);
            BaseChunk[] chunks = column.getChunks();
            BiomeRenderer renderer = new BiomeRenderer(user);

            boolean modified = resolver.positionDependent()
                ? applyPerCell(chunks, snapshot, resolver, renderer)
                : applyChunkWide(chunks, snapshot, resolver, renderer);

            if (modified) {
                event.markForReEncode(true);
            }
        }

        private static boolean applyChunkWide(
            BaseChunk[] chunks,
            SnapshotChunkData snapshot,
            VirtualBiomeResolver resolver,
            BiomeRenderer renderer
        ) {
            VirtualBiome phony = resolver.resolve(snapshot, 0, 0, 0);
            if (phony == null) {
                return false;
            }

            RenderedBiome rendered = renderer.render(phony);
            boolean modified = false;
            for (BaseChunk chunk : chunks) {
                if (!(chunk instanceof Chunk_v1_18 section)) {
                    continue;
                }
                DataPalette biomes = section.getBiomeData();
                for (int x = 0; x < CHUNK_SECTIONS; x++) {
                    for (int y = 0; y < CHUNK_SECTIONS; y++) {
                        for (int z = 0; z < CHUNK_SECTIONS; z++) {
                            biomes.set(x, y, z, rendered.biomeId());
                        }
                    }
                }
                modified = true;

                if (rendered.hasReplacements() && paletteMayContain(section, rendered)) {
                    applyReplacements(section, rendered);
                }
            }
            return modified;
        }

        private static boolean applyPerCell(
            BaseChunk[] chunks,
            SnapshotChunkData snapshot,
            VirtualBiomeResolver resolver,
            BiomeRenderer renderer
        ) {
            RenderedBiome[] cells = new RenderedBiome[BIOME_CELLS_PER_SECTION];
            boolean modified = false;

            for (int sectionIndex = 0; sectionIndex < chunks.length; sectionIndex++) {
                if (!(chunks[sectionIndex] instanceof Chunk_v1_18 section)) {
                    continue;
                }

                DataPalette biomes = section.getBiomeData();
                int baseQuartY = sectionIndex * CHUNK_SECTIONS;
                boolean anyReplacements = false;
                int cell = 0;

                for (int x = 0; x < CHUNK_SECTIONS; x++) {
                    for (int y = 0; y < CHUNK_SECTIONS; y++) {
                        for (int z = 0; z < CHUNK_SECTIONS; z++) {
                            VirtualBiome phony = resolver.resolve(snapshot, x, baseQuartY + y, z);
                            if (phony == null) {
                                cells[cell++] = null;
                                continue;
                            }
                            RenderedBiome rendered = renderer.render(phony);
                            cells[cell++] = rendered;
                            biomes.set(x, y, z, rendered.biomeId());
                            modified = true;
                            anyReplacements |= rendered.hasReplacements();
                        }
                    }
                }

                if (anyReplacements && paletteMayContain(section, renderer) && applyReplacements(section, cells)) {
                    modified = true;
                }
            }
            return modified;
        }

        private static boolean applyReplacements(Chunk_v1_18 section, RenderedBiome rendered) {
            boolean modified = false;
            for (int x = 0; x < CHUNK_SECTION_SIZE; x++) {
                for (int y = 0; y < CHUNK_SECTION_SIZE; y++) {
                    for (int z = 0; z < CHUNK_SECTION_SIZE; z++) {
                        int replacement = rendered.replacementFor(section.getBlockId(x, y, z));
                        if (replacement >= 0) {
                            section.set(x, y, z, replacement);
                            modified = true;
                        }
                    }
                }
            }
            return modified;
        }

        private static boolean applyReplacements(Chunk_v1_18 section, RenderedBiome[] cells) {
            boolean modified = false;
            for (int x = 0; x < CHUNK_SECTION_SIZE; x++) {
                for (int y = 0; y < CHUNK_SECTION_SIZE; y++) {
                    for (int z = 0; z < CHUNK_SECTION_SIZE; z++) {
                        RenderedBiome rendered = cells[cellIndex(x >> 2, y >> 2, z >> 2)];
                        if (rendered == null || !rendered.hasReplacements()) {
                            continue;
                        }
                        int replacement = rendered.replacementFor(section.getBlockId(x, y, z));
                        if (replacement >= 0) {
                            section.set(x, y, z, replacement);
                            modified = true;
                        }
                    }
                }
            }
            return modified;
        }

        private static int cellIndex(int quartX, int quartY, int quartZ) {
            return (quartX * CHUNK_SECTIONS + quartY) * CHUNK_SECTIONS + quartZ;
        }

        private static boolean paletteMayContain(Chunk_v1_18 section, RenderedBiome rendered) {
            Palette palette = section.getChunkData().palette;
            if (palette instanceof GlobalPalette) {
                return true; // no usable palette to scan
            }
            int size = palette.size();
            for (int i = 0; i < size; i++) {
                if (rendered.replacementFor(palette.idToState(i)) >= 0) {
                    return true;
                }
            }
            return false;
        }

        private static boolean paletteMayContain(Chunk_v1_18 section, BiomeRenderer renderer) {
            Palette palette = section.getChunkData().palette;
            if (palette instanceof GlobalPalette) {
                return true;
            }
            int size = palette.size();
            for (int i = 0; i < size; i++) {
                if (renderer.replacesAnywhere(palette.idToState(i))) {
                    return true;
                }
            }
            return false;
        }
    }

    @AsOf("0.0.19")
    private static class BlockChangePacketListener implements PacketListener {
        private final PacketEventsPacketHandler context;

        public BlockChangePacketListener(PacketEventsPacketHandler context) {
            this.context = context;
        }

        @Override
        public void onPacketSend(PacketSendEvent event) {
            if (event.getPacketType() != PacketType.Play.Server.BLOCK_CHANGE) {
                return;
            }

            WrapperPlayServerBlockChange wrapper = new WrapperPlayServerBlockChange(event);
            Player player = event.getPlayer();
            Vector3i vector3i =  wrapper.getBlockPosition();

            VirtualBiome override = context.collector.bestBiomeFor(
                player, vector3i.getX(), vector3i.getY(), vector3i.getZ()
            );
            if (override == null) {
                return;
            }

            List<BlockReplacement> blockReplacements = override.blockReplacements();
            if (blockReplacements.isEmpty()) {
                return;
            }

            WrappedBlockState wrappedBlockData = wrapper.getBlockState();
            for (BlockReplacement replacement : blockReplacements) {
                WrappedBlockState originalState = PacketEventsBukkitMaterials.fromCachedBukkitBlockData(replacement.originalBlock());
                if (!wrappedBlockData.equals(originalState)) {
                    continue;
                }

                WrappedBlockState newState = PacketEventsBukkitMaterials.fromCachedBukkitBlockData(replacement.replacementBlock());
                wrapper.setBlockState(newState);
                break;
            }

            event.markForReEncode(true);
        }
    }

    @AsOf("0.0.19")
    private static class MultiBlockChangePacketListener implements PacketListener {
        private final PacketEventsPacketHandler context;

        public MultiBlockChangePacketListener(PacketEventsPacketHandler context) {
            this.context = context;
        }

        @Override
        public void onPacketSend(PacketSendEvent event) {
            if (event.getPacketType() != PacketType.Play.Server.MULTI_BLOCK_CHANGE) {
                return;
            }

            WrapperPlayServerMultiBlockChange wrapper = new WrapperPlayServerMultiBlockChange(event);
            Player player = event.getPlayer();
            ClientVersion clientVersion = event.getUser().getClientVersion();
            boolean modified = false;
            for (WrapperPlayServerMultiBlockChange.EncodedBlock encodedBlock : wrapper.getBlocks()) {
                VirtualBiome override = context.collector.bestBiomeFor(
                    player, encodedBlock.getX(), encodedBlock.getY(), encodedBlock.getZ()
                );
                if (override == null || override.blockReplacements().isEmpty()) {
                    continue;
                }
                WrappedBlockState wrappedBlockData = encodedBlock.getBlockState(clientVersion);
                for (BlockReplacement replacement : override.blockReplacements()) {
                    WrappedBlockState originalState = PacketEventsBukkitMaterials.fromCachedBukkitBlockData(replacement.originalBlock());
                    if (!wrappedBlockData.equals(originalState)) {
                        continue;
                    }

                    WrappedBlockState newState = PacketEventsBukkitMaterials.fromCachedBukkitBlockData(replacement.replacementBlock());
                    encodedBlock.setBlockState(newState);
                    modified = true;
                    break;
                }
            }

            if (modified) {
                event.markForReEncode(true);
            }
        }
    }


    public static final class PacketEventsSnapshotChunkData implements SnapshotChunkData {

        private static final String BIOME_REGISTRY_KEY = "worldgen/biome";

        private final ChunkLocation location;
        private final Column column;
        private final User user;
        private final ClientVersion clientVersion;

        private final Map<Integer, Biome> idCache = new HashMap<>();
        private @Nullable IRegistry<?> biomeRegistry;

        private @Nullable Biome center;

        public PacketEventsSnapshotChunkData(ChunkLocation location, Column column, User user) {
            this.location = location;
            this.column = column;
            this.user = user;
            this.clientVersion = user.getClientVersion();
        }

        @Override
        public ChunkLocation location() {
            return location;
        }

        @Override
        public Biome centerBiome() {
            Biome c = this.center;
            if (c == null) {
                c = resolveBiome(biomeIdAt(0, CENTER_NOISE_XZ, 0, CENTER_NOISE_XZ));
                this.center = c;
            }
            return c;
        }

        @Override
        public Biome biomeAt(int x, int y, int z) {
            int sectionIdx = y >> 4;
            return resolveBiome(biomeIdAt(sectionIdx, (x & 15) >> 2, (y & 15) >> 2, (z & 15) >> 2));
        }

        @Override
        public Optional<ChunkSnapshot> bukkitSnapshot() {
            return Optional.empty();
        }

        private int biomeIdAt(int sectionIdx, int nx, int ny, int nz) {
            BaseChunk[] chunks = column.getChunks();
            if (sectionIdx < 0 || sectionIdx >= chunks.length || !(chunks[sectionIdx] instanceof Chunk_v1_18 chunk)) {
                throw new IllegalStateException("No biome section at index " + sectionIdx + " for chunk " + location);
            }
            return chunk.getBiomeData().get(nx, ny, nz);
        }

        private Biome resolveBiome(int id) {
            Biome cached = idCache.get(id);
            if (cached != null) {
                return cached;
            }

            MappedEntity entry = biomeRegistry().getById(clientVersion, id);
            if (entry == null) {
                throw new IllegalStateException("No biome registry entry for id " + id + " (client " + clientVersion + ")");
            }

            ResourceLocation name = entry.getName();
            NamespacedKey key = new NamespacedKey(name.getNamespace(), name.getKey());
            Biome biome = RegistryAccess.registryAccess().getRegistry(RegistryKey.BIOME).get(key);
            if (biome == null) {
                throw new IllegalStateException("No Bukkit biome for " + key);
            }

            idCache.put(id, biome);
            return biome;
        }

        private IRegistry<?> biomeRegistry() {
            IRegistry<?> registry = this.biomeRegistry;
            if (registry == null) {
                registry = user.getRegistry(ResourceLocation.minecraft(BIOME_REGISTRY_KEY));
                if (registry == null) {
                    throw new IllegalStateException("Biome registry not found for user " + user.getName());
                }
                this.biomeRegistry = registry;
            }
            return registry;
        }
    }
}
