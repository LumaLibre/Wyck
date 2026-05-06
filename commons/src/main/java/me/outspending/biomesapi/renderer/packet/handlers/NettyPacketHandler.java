package me.outspending.biomesapi.renderer.packet.handlers;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.misc.ChunkLocation;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.renderer.packet.PacketHandler;
import me.outspending.biomesapi.renderer.packet.PhonyCustomBiomeCollector;
import me.outspending.biomesapi.renderer.packet.data.BlockReplacement;
import me.outspending.biomesapi.renderer.packet.data.PhonyCustomBiome;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A standalone PacketHandler implementation that injects a Netty handler into the Minecraft server.
 * This handler intercepts outbound packets and modifies them to use custom biomes.
 * This handler does not require any external dependencies and only relies on vanilla's netty and Bukkit's listeners.
 *
 * @version 2.1.0
 * @since 2.1.0
 * @author Jsinco
 */
@AsOf("2.1.0")
public class NettyPacketHandler implements PacketHandler, Listener {

    private static final Logger LOGGER = Logger.getLogger(NettyPacketHandler.class.getName());

    private final Plugin provider;
    private final PhonyCustomBiomeCollector collector;
    private final String handlerName;

    @AsOf("2.1.0")
    public NettyPacketHandler(@NotNull Plugin provider, @NotNull PhonyCustomBiomeCollector collector) {
        this.provider = provider;
        this.collector = collector;
        this.handlerName = provider.getName().toLowerCase() + "_biomesapi_handler";
    }

    @AsOf("2.1.0")
    public NettyPacketHandler(@NotNull Plugin provider) {
        this(provider, new PhonyCustomBiomeCollector());
    }

    @Override
    public PacketHandler register() {
        Bukkit.getPluginManager().registerEvents(this, provider);
        // reload safety
        for (Player online : Bukkit.getOnlinePlayers()) {
            inject(online);
        }
        return this;
    }

    @Override
    public PacketHandler unregister() {
        HandlerList.unregisterAll(this);
        for (Player online : Bukkit.getOnlinePlayers()) {
            uninject(online);
        }
        return this;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        inject(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        uninject(event.getPlayer());
    }

    @SuppressWarnings("resource")
    private void inject(Player player) {
        Channel channel = channelOf(player);
        if (channel == null) return;

        channel.eventLoop().execute(() -> {
            ChannelPipeline pipeline = channel.pipeline();
            if (pipeline.get(handlerName) != null) return; // already injected
            try {
                pipeline.addBefore("packet_handler", handlerName, new NettyChannelHandler(player, collector));
            } catch (NoSuchElementException | IllegalArgumentException e) {
                LOGGER.log(Level.WARNING, "Failed to inject Netty handler for " + player.getName(), e);
            }
        });
    }

    @SuppressWarnings("resource")
    private void uninject(Player player) {
        Channel channel = channelOf(player);
        if (channel == null) return;

        channel.eventLoop().execute(() -> {
            ChannelPipeline pipeline = channel.pipeline();
            if (pipeline.get(handlerName) != null) {
                pipeline.remove(handlerName);
            }
        });
    }

    @Override
    public PacketHandler appendBiome(@NotNull PhonyCustomBiome biome) {
        collector.appendBiome(biome);
        return this;
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
    public boolean hasBiome(@NotNull PhonyCustomBiome biome) {
        return collector.hasBiome(biome);
    }

    @Override
    public boolean hasBiome(@NotNull BiomeResourceKey biomeKey) {
        return collector.hasBiome(biomeKey);
    }

    @Override
    public PacketHandler clearBiomes() {
        collector.clearBiomes();
        return this;
    }

    private static Channel channelOf(Player player) {
        try {
            return ((CraftPlayer) player).getHandle().connection.connection.channel;
        } catch (Throwable t) {
            LOGGER.log(Level.WARNING, "Could not resolve Netty channel for " + player.getName(), t);
            return null;
        }
    }


    @AsOf("2.1.0")
    private static class NettyChannelHandler extends ChannelDuplexHandler {

        private static final Logger LOGGER = Logger.getLogger(NettyChannelHandler.class.getName());

        private static final Field BLOCK_UPDATE_STATE_FIELD = findFieldByType(ClientboundBlockUpdatePacket.class, BlockState.class, "blockState");
        private static final Field SECTION_POS_FIELD = findFieldByType(ClientboundSectionBlocksUpdatePacket.class, SectionPos.class, "sectionPos");
        private static final Field SECTION_STATES_FIELD = findFieldByType(ClientboundSectionBlocksUpdatePacket.class, BlockState[].class, "states");


        private final Player player;
        private final PhonyCustomBiomeCollector collector;

        public NettyChannelHandler(Player player, PhonyCustomBiomeCollector collector) {
            this.player = player;
            this.collector = collector;
        }

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            try {
                if (msg instanceof ClientboundLevelChunkWithLightPacket chunkPacket) {
                    handleChunkPacket(chunkPacket);
                } else if (msg instanceof ClientboundBlockUpdatePacket blockPacket) {
                    handleBlockUpdate(blockPacket);
                } else if (msg instanceof ClientboundSectionBlocksUpdatePacket sectionPacket) {
                    handleSectionUpdate(sectionPacket);
                }
            } catch (Throwable t) {
                LOGGER.log(Level.WARNING, "Failed to process outbound packet for " + player.getName(), t);
            }
            super.write(ctx, msg, promise);
        }

        private void handleChunkPacket(ClientboundLevelChunkWithLightPacket packet) {
            ChunkLocation loc = ChunkLocation.of(packet.getX(), packet.getZ());
            PhonyCustomBiome override = collector.bestBiomeFor(player, loc);
            if (override == null) return;

            PacketHandler.DimensionSectionCount sectionCount =
                    PacketHandler.DimensionSectionCount.fromBukkitEnvironment(player.getWorld().getEnvironment());

            NativeChunkPacketHandler.WIRE.get()
                    .modifyChunkBiomes(packet.getChunkData(), override.customBiome(), sectionCount);
        }

        private void handleBlockUpdate(ClientboundBlockUpdatePacket packet) {
            if (BLOCK_UPDATE_STATE_FIELD == null) return;

            BlockPos pos = packet.getPos();
            ChunkLocation loc = ChunkLocation.fromBlockCoords(pos.getX(), pos.getZ());
            PhonyCustomBiome override = collector.bestBiomeFor(player, loc);
            if (override == null) return;

            BlockReplacement[] replacements = override.customBiome().getBlockReplacements();
            if (replacements == null || replacements.length == 0) return;

            BlockState state = packet.getBlockState();
            Material currentMat = state.getBukkitMaterial();

            for (BlockReplacement r : replacements) {
                if (currentMat == r.originalBlock()) {
                    BlockState replaced = materialToState(r.replacementBlock());
                    if (replaced == null) return;
                    try {
                        BLOCK_UPDATE_STATE_FIELD.set(packet, replaced);
                    } catch (IllegalAccessException e) {
                        LOGGER.log(Level.WARNING, "Failed to rewrite block update", e);
                    }
                    return;
                }
            }
        }

        private void handleSectionUpdate(ClientboundSectionBlocksUpdatePacket packet) {
            if (SECTION_POS_FIELD == null || SECTION_STATES_FIELD == null) return;

            try {
                SectionPos sectionPos = (SectionPos) SECTION_POS_FIELD.get(packet);
                ChunkLocation loc = ChunkLocation.of(sectionPos.x(), sectionPos.z());
                PhonyCustomBiome override = collector.bestBiomeFor(player, loc);
                if (override == null) return;

                BlockReplacement[] replacements = override.customBiome().getBlockReplacements();
                if (replacements == null || replacements.length == 0) return;

                BlockState[] states = (BlockState[]) SECTION_STATES_FIELD.get(packet);
                if (states == null || states.length == 0) return;

                boolean modified = false;
                for (int i = 0; i < states.length; i++) {
                    Material mat = states[i].getBukkitMaterial();
                    for (BlockReplacement r : replacements) {
                        if (mat == r.originalBlock()) {
                            BlockState replaced = materialToState(r.replacementBlock());
                            if (replaced != null) {
                                states[i] = replaced;
                                modified = true;
                            }
                            break;
                        }
                    }
                }

                if (modified) {
                    SECTION_STATES_FIELD.set(packet, states);
                }
            } catch (IllegalAccessException e) {
                LOGGER.log(Level.WARNING, "Failed to rewrite section block update", e);
            }
        }


        /**
         * Resolve a field by Mojang-mapped name first, falling back to scanning by exact type.
         * Returns null and logs if neither approach works — callers degrade to no-op.
         */
        private static Field findFieldByType(Class<?> owner, Class<?> type, String mojangName) {
            try {
                Field f = owner.getDeclaredField(mojangName);
                f.setAccessible(true);
                return f;
            } catch (NoSuchFieldException ignored) {
                for (Field candidate : owner.getDeclaredFields()) {
                    if (candidate.getType() == type) {
                        candidate.setAccessible(true);
                        return candidate;
                    }
                }
                LOGGER.log(Level.SEVERE,
                        "Could not locate field of type " + type.getName() + " on " + owner.getName()
                                + " (looked for '" + mojangName + "'). Related packets cannot be rewritten. :(");
                return null;
            }
        }

        private static BlockState materialToState(Material material) {
            if (material == null || !material.isBlock()) return null;
            return ((CraftBlockData) Bukkit.createBlockData(material)).getState();
        }
    }
}