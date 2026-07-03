package dev.wyck.renderer.packet.handlers;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.ClientVersion;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.protocol.world.chunk.Column;
import com.github.retrooper.packetevents.protocol.world.chunk.impl.v_1_18.Chunk_v1_18;
import com.github.retrooper.packetevents.protocol.world.states.WrappedBlockState;
import com.github.retrooper.packetevents.resources.ResourceLocation;
import com.github.retrooper.packetevents.util.Vector3i;
import com.github.retrooper.packetevents.util.mappings.IRegistry;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerBlockChange;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerChunkData;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerMultiBlockChange;
import com.google.common.base.Preconditions;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import dev.wyck.annotations.AsOf;
import dev.wyck.misc.ChunkLocation;
import dev.wyck.keys.ResourceKey;
import dev.wyck.renderer.packet.PacketHandler;
import dev.wyck.renderer.packet.PhonyCustomBiomeCollector;
import dev.wyck.renderer.packet.data.BlockReplacement;
import dev.wyck.renderer.packet.data.PhonyCustomBiome;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private final PhonyCustomBiomeCollector collector;
    private final PacketListener[] packetListeners;
    private final PacketListenerPriority packetListenerPriority;

    @AsOf("2.1.0")
    public PacketEventsPacketHandler(PacketHandler.Priority priority, PhonyCustomBiomeCollector collector) {
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
        this(priority, new PhonyCustomBiomeCollector());
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
    public PacketHandler appendBiome(PhonyCustomBiome biome) {
        if (biome.biomeCondition() != null) {
            throw new UnsupportedOperationException("Biome conditions are not supported by PacketEventsPacketHandler yet.");
        }
        collector.appendBiome(biome);
        return this;
    }

    @Override
    public boolean removeBiome(PhonyCustomBiome biome) {
        return collector.removeBiome(biome);
    }

    @Override
    public boolean removeBiome(ResourceKey biomeKey) {
        return collector.removeBiome(biomeKey);
    }

    @Override
    public boolean hasBiome(PhonyCustomBiome biome) {
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
        private static final Map<Material, WrappedBlockState> materialToWrappedBlockStateCache = new HashMap<>();
        private static final Map<Integer, Material> idToMaterialCache = new HashMap<>();

        public static WrappedBlockState fromCachedBukkitBlockData(Material blockType) {
            return materialToWrappedBlockStateCache.computeIfAbsent(blockType, key ->
                    SpigotConversionUtil.fromBukkitBlockData(key.createBlockData())
            );
        }

        public static Material toCachedBukkitBlockData(int blockId) {
            return idToMaterialCache.computeIfAbsent(blockId, key ->
                    SpigotConversionUtil.toBukkitBlockData(WrappedBlockState.getByGlobalId(key))
                            .createBlockState()
                            .getType()
            );
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

            // TODO: SnapshotChunkData
            PhonyCustomBiome phonyCustomBiome = context.collector.bestBiomeFor(player, chunkLocation);
            if (phonyCustomBiome == null) {
                return;
            }

            ResourceKey biomeResourceKey = phonyCustomBiome.biomeResourceKey();
            List<BlockReplacement> blockReplacements = phonyCustomBiome.blockReplacements();


            IRegistry<?> elementRegistry = user.getRegistry(ResourceLocation.minecraft(REGISTRY_KEY));
            Preconditions.checkNotNull(elementRegistry, "Biome registry not found for user " + user.getName());
            var element = elementRegistry.getByName(new ResourceLocation(biomeResourceKey.namespace(), biomeResourceKey.path()));
            Preconditions.checkNotNull(element, "Biome " + biomeResourceKey.resourceLocation() + " not found in registry for user " + user.getName());

            int biomeId = element.getId(user.getClientVersion());


            for (var chunk : wrapperPlayServerChunkData.getColumn().getChunks()) {
                if (!(chunk instanceof Chunk_v1_18 chunkV1_18)) {
                    continue;
                }

                for (int x = 0; x < CHUNK_SECTIONS; x++) {
                    for (int z = 0; z < CHUNK_SECTIONS; z++) {
                        for (int y = 0; y < CHUNK_SECTIONS; y++) {
                            chunkV1_18.getBiomeData().set(x, y, z, biomeId);
                        }
                    }
                }

                if (blockReplacements.isEmpty()) {
                    continue;
                }

                for (int x = 0; x < CHUNK_SECTION_SIZE; x++) {
                    for (int y = 0; y < CHUNK_SECTION_SIZE; y++) {
                        for (int z = 0; z < CHUNK_SECTION_SIZE; z++) {
                            WrappedBlockState state = chunkV1_18.get(x, y, z);

                            for (BlockReplacement replacement : blockReplacements) {
                                WrappedBlockState wrappedReplacement = PacketEventsBukkitMaterials.fromCachedBukkitBlockData(replacement.originalBlock());
                                if (!state.equals(wrappedReplacement)) {
                                    continue;
                                }

                                WrappedBlockState newState = PacketEventsBukkitMaterials.fromCachedBukkitBlockData(replacement.replacementBlock());
                                chunkV1_18.set(x, y, z, newState);
                                break;
                            }
                        }
                    }
                }
            }

            event.markForReEncode(true);
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

            PhonyCustomBiome override = context.collector.bestBiomeFor(player, ChunkLocation.fromBlockCoords(vector3i.getX(), vector3i.getZ()));
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
            Vector3i chunkPosition = wrapper.getChunkPosition();
            PhonyCustomBiome override = context.collector.bestBiomeFor(player, ChunkLocation.of(chunkPosition.getX(), chunkPosition.getZ()));

            if (override == null) {
                return;
            }

            List<BlockReplacement> blockReplacements = override.blockReplacements();
            if (blockReplacements.isEmpty()) {
                return;
            }

            for (WrapperPlayServerMultiBlockChange.EncodedBlock encodedBlock : wrapper.getBlocks()) {
                WrappedBlockState wrappedBlockData = encodedBlock.getBlockState(clientVersion);
                for (BlockReplacement replacement : blockReplacements) {
                    WrappedBlockState originalState = PacketEventsBukkitMaterials.fromCachedBukkitBlockData(replacement.originalBlock());
                    if (!wrappedBlockData.equals(originalState)) {
                        continue;
                    }

                    WrappedBlockState newState = PacketEventsBukkitMaterials.fromCachedBukkitBlockData(replacement.replacementBlock());
                    encodedBlock.setBlockState(newState);
                    break;
                }
            }

            event.markForReEncode(true);
        }
    }


}
