package me.outspending.biomesapi.packet.handlers;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.world.chunk.Column;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerChunkData;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.nms.NMS;
import me.outspending.biomesapi.nms.NMSHandler;
import me.outspending.biomesapi.packet.PacketHandler;
import me.outspending.biomesapi.packet.PhonyCustomBiomeCollector;
import me.outspending.biomesapi.packet.data.ChunkLocation;
import me.outspending.biomesapi.packet.data.PhonyCustomBiome;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import org.bukkit.craftbukkit.block.CraftBiome;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@me.outspending.biomesapi.annotations.Experimental
@ApiStatus.Experimental
@ApiStatus.Internal
@AsOf("0.0.6")
public class PacketEventsPacketHandler implements PacketHandler {

    private final PhonyCustomBiomeCollector collector;
    private final PacketListener[] packetListeners;
    private final PacketListenerPriority packetListenerPriority;

    public PacketEventsPacketHandler(PacketHandler.Priority priority) {
        this.collector = new PhonyCustomBiomeCollector();
        this.packetListeners = new PacketListener[] {
                new MapChunkPacketListener(this)
        };
        this.packetListenerPriority = priority.getDelegatePriority(PacketListenerPriority.class);
    }


    @Override
    public void register() {
        for (PacketListener listener : packetListeners) {
            PacketEvents.getAPI().getEventManager().registerListener(listener, packetListenerPriority);
        }
    }

    @Override
    public void unregister() {
        for (PacketListener listener : packetListeners) {
            PacketEvents.getAPI().getEventManager().unregisterListener(listener.asAbstract(packetListenerPriority));
        }
    }

    @Override
    public void appendBiome(@NotNull PhonyCustomBiome biome) {
        collector.appendBiome(biome);
    }

    @Override
    public boolean removeBiome(@NotNull PhonyCustomBiome biome) {
        return collector.removeBiome(biome);
    }

    @Override
    public boolean removeBiome(@NotNull BiomeResourceKey biomeKey) {
        return collector.removeBiome(biomeKey);
    }

    @Override
    public void clearBiomes() {
        collector.clearBiomes();
    }


    // FIXME: packetevents api is really weird
    private static class MapChunkPacketListener implements PacketListener {

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




            Player player = event.getPlayer();
            ChunkLocation chunkLocation = ChunkLocation.of(column.getX(), column.getZ());


            PhonyCustomBiome phonyCustomBiome = context.collector.bestBiomeFor(player, chunkLocation);
            if (phonyCustomBiome == null) {
                return;
            }

            CustomBiome customBiome = phonyCustomBiome.customBiome();


            org.bukkit.block.Biome bukkitBiome = customBiome.toBukkitBiome();
            Holder<net.minecraft.world.level.biome.@NotNull Biome> minecraftBiome = CraftBiome.bukkitToMinecraftHolder(bukkitBiome);
            if (minecraftBiome == null) {
                throw new IllegalStateException("Failed to get Minecraft biome for " + bukkitBiome);
            }

            NMS nms = NMSHandler.getNMS().orElseThrow();
            Registry<net.minecraft.world.level.biome.@NotNull Biome> registry = (Registry<net.minecraft.world.level.biome.@NotNull Biome>) nms.getRegistry();
            byte id = (byte) registry.getIdOrThrow(minecraftBiome.value());


            if (column.hasBiomeData()) {
                byte[] biomeDataBytes = column.getBiomeDataBytes();

                for (int i = 0; i < biomeDataBytes.length; i++) {
                    biomeDataBytes[i] = id;
                }
            } else {
                byte[] biomeData = new byte[24 * 64];
                for (int i = 0; i < biomeData.length; i++) {
                    biomeData[i] = id;
                }
                // reflect
                try {
                    java.lang.reflect.Field biomeDataField = Column.class.getDeclaredField("biomeDataBytes");
                    biomeDataField.setAccessible(true);
                    biomeDataField.set(column, biomeData);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            event.markForReEncode(true);
        }
    }
}
