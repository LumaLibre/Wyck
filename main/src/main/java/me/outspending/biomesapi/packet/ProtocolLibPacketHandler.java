package me.outspending.biomesapi.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.packet.data.BiomeOverride;
import me.outspending.biomesapi.packet.data.BlockReplacement;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.WeakHashMap;
import java.util.function.Predicate;

public class ProtocolLibPacketHandler implements PacketHandler {

    // TODO: Should this be static?
    private static final WeakHashMap<Player, BiomeOverride> playerBiomeOverrides = new WeakHashMap<>();
    private final PacketAdapter[] protocolListeners;


    public ProtocolLibPacketHandler(@NotNull JavaPlugin provider, PacketHandlerPriority priority) {
        ListenerPriority prio = priority.getDelegatePriority(ListenerPriority.class);
        this.protocolListeners = new PacketAdapter[] {
                new MapChunkPacketListener(provider, prio),
                new BlockChangePacketListener(provider, prio),
                new MultiBlockChangePacketListener(provider, prio)
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
    public void registerBiomeOverride(@NotNull Player player, @NotNull CustomBiome biome, @NotNull Predicate<Player> condition) {
        playerBiomeOverrides.put(player, new BiomeOverride(biome, condition));
    }

    @Override
    public boolean unregisterBiomeOverride(@NotNull Player player) {
        return playerBiomeOverrides.remove(player) != null;
    }



    @Nullable
    private static BlockReplacement[] getBlockReplacementsForPlayer(@NotNull Player player) {
        BiomeOverride override = playerBiomeOverrides.get(player);
        if (override == null) {
            return null;
        }
        return override.customBiome().getBlockReplacements();
    }


    private static class MapChunkPacketListener extends PacketAdapter {

        public MapChunkPacketListener(JavaPlugin provider, ListenerPriority priority) {
            super(provider, priority, PacketType.Play.Server.MAP_CHUNK);
        }

        @Override
        public void onPacketSending(PacketEvent event) {
            Player player = event.getPlayer();
            BiomeOverride override = playerBiomeOverrides.get(player);

            if (override == null || !override.condition().test(player)) {
                return;
            }

            try {
                DimensionSectionCount dimensionSectionCount = DimensionSectionCount.fromBukkitEnvironment(player.getWorld().getEnvironment());

                ClientboundLevelChunkPacketData chunkData = event.getPacket().getSpecificModifier(ClientboundLevelChunkPacketData.class).read(0);

                PacketHandlerHelper.INSTANCE.modifyChunkBiomes(chunkData, override.customBiome(), dimensionSectionCount);
            } catch (Exception e) {
                //provider.getLogger().warning("Failed to modify chunk biomes for player " + player.getName());
                e.printStackTrace();
            }
        }
    }


    private static class BlockChangePacketListener extends PacketAdapter {

        public BlockChangePacketListener(JavaPlugin provider, ListenerPriority priority) {
            super(provider, priority, PacketType.Play.Server.BLOCK_CHANGE);
        }

        @Override
        public void onPacketSending(PacketEvent event) {
            Player player = event.getPlayer();

            BlockReplacement[] blockReplacements = getBlockReplacementsForPlayer(player);
            if (blockReplacements == null || blockReplacements.length == 0) {
                return;
            }


            WrappedBlockData wrappedBlockData = event.getPacket().getBlockData().read(0);
            for (BlockReplacement replacement : blockReplacements) {
                if (wrappedBlockData.getType() == replacement.originalBlock()) {
                    wrappedBlockData.setType(replacement.replacementBlock());
                    event.getPacket().getBlockData().write(0, wrappedBlockData);
                    break;
                }
            }
        }
    }


    private static class MultiBlockChangePacketListener extends PacketAdapter {

        public MultiBlockChangePacketListener(JavaPlugin provider, ListenerPriority priority) {
            super(provider, priority, PacketType.Play.Server.MULTI_BLOCK_CHANGE);
        }

        @Override
        public void onPacketSending(PacketEvent event) {
            Player player = event.getPlayer();

            BlockReplacement[] blockReplacements = getBlockReplacementsForPlayer(player);
            if (blockReplacements == null || blockReplacements.length == 0) {
                return;
            }

            WrappedBlockData[] wrappedBlockDatas = event.getPacket().getBlockDataArrays().read(0);
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
                event.getPacket().getBlockDataArrays().write(0, wrappedBlockDatas);
            }
        }
    }
}
