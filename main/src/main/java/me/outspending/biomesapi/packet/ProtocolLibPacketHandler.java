package me.outspending.biomesapi.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import me.outspending.biomesapi.packet.data.BlockReplacement;
import me.outspending.biomesapi.packet.data.ChunkLocation;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class ProtocolLibPacketHandler implements PacketHandler {

    private final Set<PhonyCustomBiome> backing = new HashSet<>();
    private final PacketAdapter[] protocolListeners;


    public ProtocolLibPacketHandler(@NotNull JavaPlugin provider, Priority priority) {
        ListenerPriority protocolLibPrio = priority.getDelegatePriority(ListenerPriority.class);
        this.protocolListeners = new PacketAdapter[] {
                new MapChunkPacketListener(provider, protocolLibPrio, this),
                new BlockChangePacketListener(provider, protocolLibPrio, this),
                new MultiBlockChangePacketListener(provider, protocolLibPrio, this)
        };
    }

    @Override
    public void register() {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

        for (PacketAdapter listener : protocolListeners) {
            protocolManager.addPacketListener(listener);
        }
    }

    @Override
    public void unregister() {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

        for (PacketAdapter listener : protocolListeners) {
            protocolManager.removePacketListener(listener);
        }
    }

    @Override
    public void appendBiome(@NotNull PhonyCustomBiome biome) {
        if (backing.contains(biome)) {
            throw new IllegalArgumentException("PhonyCustomBiome with key " + biome.customBiome().toNamespacedKey() + " is already registered.");
        }
        backing.add(biome);
    }

    @Override
    public boolean removeBiome(@NotNull PhonyCustomBiome biome) {
        return backing.remove(biome);
    }

    @Override
    public boolean removeBiome(@NotNull NamespacedKey biomeKey) {
        return backing.removeIf((PhonyCustomBiome biome) -> biome.customBiome().toNamespacedKey().equals(biomeKey));
    }

    @Override
    public void clearBiomes() {
        backing.clear();
    }


    /**
     * Picks the 'best' phony custom biome for the given player and chunk location.
     * @param player the player
     * @param chunkLocation the chunk location
     * @return the best phony custom biome, or null if none match
     */
    private @Nullable PhonyCustomBiome bestBiomeFor(@NotNull Player player, @NotNull ChunkLocation chunkLocation) {
        if (backing.isEmpty()) {
            return null;
        }

        return backing.stream()
                .filter((PhonyCustomBiome phony) -> phony.conditional().test(player, chunkLocation))
                .max(Comparator.comparingInt((PhonyCustomBiome phony) -> phony.priority().getLevel()))
                .orElse(null);
    }


    private static class MapChunkPacketListener extends PacketAdapter {

        private final ProtocolLibPacketHandler context;

        public MapChunkPacketListener(JavaPlugin provider, ListenerPriority priority, ProtocolLibPacketHandler context) {
            super(provider, priority, PacketType.Play.Server.MAP_CHUNK);
            this.context = context;
        }

        @Override
        public void onPacketSending(PacketEvent event) {
            // ClientboundLevelChunkWithLightPacket

            PacketContainer packet = event.getPacket();
            Player player = event.getPlayer();
            StructureModifier<Integer> ints = packet.getIntegers();
            ChunkLocation chunkLocation = ChunkLocation.of(ints.read(0), ints.read(1));

            PhonyCustomBiome override = context.bestBiomeFor(player, chunkLocation);
            if (override == null) {
                return;
            }

            try {
                DimensionSectionCount dimensionSectionCount = DimensionSectionCount.fromBukkitEnvironment(player.getWorld().getEnvironment());
                ClientboundLevelChunkPacketData chunkData = packet.getSpecificModifier(ClientboundLevelChunkPacketData.class).read(0);

                PacketHandlerHelper.INSTANCE.modifyChunkBiomes(chunkData, override.customBiome(), dimensionSectionCount);
            } catch (Exception e) {
                //provider.getLogger().warning("Failed to modify chunk biomes for player " + player.getName());
                e.printStackTrace();
            }
        }
    }

    // TODO: extract out common code between BlockChangePacketListener and MultiBlockChangePacketListener

    private static class BlockChangePacketListener extends PacketAdapter {

        private final ProtocolLibPacketHandler context;

        public BlockChangePacketListener(JavaPlugin provider, ListenerPriority priority, ProtocolLibPacketHandler context) {
            super(provider, priority, PacketType.Play.Server.BLOCK_CHANGE);
            this.context = context;
        }

        @Override
        public void onPacketSending(PacketEvent event) {
            // ClientboundBlockUpdatePacket
            PacketContainer packet = event.getPacket();
            Player player = event.getPlayer();
            BlockPosition blockPosition = packet.getBlockPositionModifier().read(0);

            ChunkLocation chunkLocation = ChunkLocation.fromBlockCoords(blockPosition.getX(), blockPosition.getZ());
            PhonyCustomBiome override = context.bestBiomeFor(player, chunkLocation);
            if (override == null) {
                return;
            }


            BlockReplacement[] blockReplacements = override.customBiome().getBlockReplacements();
            if (blockReplacements == null || blockReplacements.length == 0) {
                return;
            }


            WrappedBlockData wrappedBlockData = packet.getBlockData().read(0);
            for (BlockReplacement replacement : blockReplacements) {
                if (wrappedBlockData.getType() == replacement.originalBlock()) {
                    wrappedBlockData.setType(replacement.replacementBlock());
                    packet.getBlockData().write(0, wrappedBlockData);
                    break;
                }
            }
        }
    }


    private static class MultiBlockChangePacketListener extends PacketAdapter {

        private final ProtocolLibPacketHandler context;

        public MultiBlockChangePacketListener(JavaPlugin provider, ListenerPriority priority, ProtocolLibPacketHandler context) {
            super(provider, priority, PacketType.Play.Server.MULTI_BLOCK_CHANGE);
            this.context = context;
        }

        @Override
        public void onPacketSending(PacketEvent event) {
            // ClientboundSectionBlocksUpdatePacket
            Player player = event.getPlayer();
            PacketContainer packet = event.getPacket();

            BlockPosition blockPosition = packet.getSectionPositions().read(0);
            ChunkLocation chunkLocation = ChunkLocation.fromBlockCoords(blockPosition.getX(), blockPosition.getZ());

            PhonyCustomBiome override = context.bestBiomeFor(player, chunkLocation);
            if (override == null) {
                return;
            }

            BlockReplacement[] blockReplacements = override.customBiome().getBlockReplacements();
            if (blockReplacements == null || blockReplacements.length == 0) {
                return;
            }

            WrappedBlockData[] wrappedBlockDatas = packet.getBlockDataArrays().read(0);
            boolean modified = false;
            for (int i = 0; i < wrappedBlockDatas.length; i++) {
                WrappedBlockData wrappedBlockData = wrappedBlockDatas[i];
                for (BlockReplacement replacement : blockReplacements) {
                    if (wrappedBlockData.getType() == replacement.originalBlock()) {
                        wrappedBlockData.setType(replacement.replacementBlock());
                        wrappedBlockDatas[i] = wrappedBlockData;
                        modified = true;
                        break;
                    }
                }
            }

            if (modified) {
                packet.getBlockDataArrays().write(0, wrappedBlockDatas);
            }
        }
    }
}
