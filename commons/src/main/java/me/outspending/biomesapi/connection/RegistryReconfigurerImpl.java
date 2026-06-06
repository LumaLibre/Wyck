package me.outspending.biomesapi.connection;

import com.mojang.authlib.GameProfile;
import io.papermc.paper.connection.PaperCommonConnection;
import io.papermc.paper.connection.PaperPlayerConfigurationConnection;
import io.papermc.paper.connection.PlayerConfigurationConnection;
import io.papermc.paper.event.connection.configuration.PlayerConnectionReconfigureEvent;
import me.outspending.biomesapi.exceptions.HorriblePlayerLoginEvent;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.common.ClientboundUpdateTagsPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.network.ConfigurationTask;
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl;
import net.minecraft.server.network.config.JoinWorldTask;
import net.minecraft.server.network.config.PrepareSpawnTask;
import net.minecraft.server.network.config.SynchronizeRegistriesTask;
import net.minecraft.server.packs.repository.KnownPack;
import net.minecraft.tags.TagNetworkSerialization;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@NullMarked
@ApiStatus.Internal
@SuppressWarnings("UnstableApiUsage")
public class RegistryReconfigurerImpl implements RegistryReconfigurer {

    private static final Field SYNC_TASK_FIELD = getField(ServerConfigurationPacketListenerImpl.class, "synchronizeRegistriesTask");
    private static final Field CURRENT_TASK_FIELD = getField(ServerConfigurationPacketListenerImpl.class, "currentTask");
    private static final Field TASKS_FIELD = getField(ServerConfigurationPacketListenerImpl.class, "configurationTasks");
    private static final Field PREPARE_SPAWN_TASK_FIELD = getField(ServerConfigurationPacketListenerImpl.class, "prepareSpawnTask");
    private static final Field PAPER_HANDLE_FIELD = getField(PaperCommonConnection.class, "handle", "packetListener");


    private final Plugin plugin;
    private final Map<Connection, PendingResync> pending = new ConcurrentHashMap<>();
    private final InternalListener listener = new InternalListener();

    // TODO: Should this be a lazy listener?
    private volatile boolean listenerActive;

    public RegistryReconfigurerImpl(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void resendRegistries(Player player, @Nullable Consumer<PlayerConfigurationConnection> consumer) throws HorriblePlayerLoginEvent {
        var sp = ((CraftPlayer) player).getHandle();
        Connection netConn = sp.connection.connection;

        if (netConn.handledLegacyLoginEvent) {
            throw new HorriblePlayerLoginEvent("Cannot resend registries for " + player.getName() + " because a plugin is listening to PlayerLoginEvent. To fix this, find the plugin that listens to PlayerLoginEvent and either remove the listener or plugin.");
        }

        CompletableFuture<Void> future = new CompletableFuture<>();

        pending.put(netConn, new PendingResync(sp.getGameProfile(), future, consumer));
        ensureListenerActive();

        player.getConnection().reenterConfiguration();
    }


    private void ensureListenerActive() {
        if (listenerActive) return;
        synchronized (this) {
            if (listenerActive) return;
            plugin.getServer().getPluginManager().registerEvents(listener, plugin);
            listenerActive = true;
        }
    }

    private void releaseListenerIfIdle() {
        if (!pending.isEmpty()) return;
        synchronized (this) {
            if (!pending.isEmpty() || !listenerActive) return;
            HandlerList.unregisterAll(listener);
            listenerActive = false;
        }
    }

    private final class InternalListener implements Listener {
        @EventHandler(priority = EventPriority.MONITOR)
        public void onReconfigure(PlayerConnectionReconfigureEvent event) {
            if (!(event.getConnection() instanceof PaperPlayerConfigurationConnection paperCfg)) return;

            ServerConfigurationPacketListenerImpl mcListener;
            try {
                mcListener = (ServerConfigurationPacketListenerImpl) PAPER_HANDLE_FIELD.get(paperCfg);
            } catch (ReflectiveOperationException e) {
                plugin.getLogger().severe("Failed to access listener handle: " + e);
                return;
            }

            PendingResync ctx = pending.remove(mcListener.connection);
            if (ctx == null) return;

            try {
                try {
                    Consumer<PlayerConfigurationConnection> consumer = ctx.consumer();
                    if (consumer != null) {
                        consumer.accept(paperCfg);
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                }
                drive(mcListener, ctx.profile());
                ctx.future().complete(null);
            } catch (Throwable t) {
                ctx.future().completeExceptionally(t);
                t.printStackTrace();
            } finally {
                releaseListenerIfIdle();
            }
        }
    }


    private void drive(ServerConfigurationPacketListenerImpl packetListener, GameProfile profile) throws ReflectiveOperationException {
        MinecraftServer server = MinecraftServer.getServer();
        LayeredRegistryAccess<RegistryLayer> registries = server.registries();
        List<KnownPack> knownPacks = server.getResourceManager().listPacks()
                .flatMap(p -> p.location().knownPackInfo().stream())
                .toList();

        SynchronizeRegistriesTask task = new SynchronizeRegistriesTask(knownPacks, registries);

        // The packetListener needs to know about this task so the client's response can be routed back to it
        SYNC_TASK_FIELD.set(packetListener, task);
        CURRENT_TASK_FIELD.set(packetListener, task);

        // Queue prepare-spawn and join-world tasks behind the sync task. They'll
        // fire automatically once handleSelectKnownPacks finishes the sync task
        // and calls startNextTask. We can't call returnToWorld() because it
        // eagerly calls startNextTask, which throws while the sync task is
        // still current.
        PrepareSpawnTask prepareSpawnTask = new PrepareSpawnTask(server, profile, packetListener);
        PREPARE_SPAWN_TASK_FIELD.set(packetListener, prepareSpawnTask);

        @SuppressWarnings("unchecked")
        Queue<ConfigurationTask> tasks = (Queue<ConfigurationTask>) TASKS_FIELD.get(packetListener);
        tasks.add(prepareSpawnTask);
        tasks.add(new JoinWorldTask());

        // The client replies; the packetListener's
        // handleSelectKnownPacks calls task.handleResponse, then finishCurrentTask which
        // advances to PrepareSpawnTask then JoinWorldTask.
        task.start(packetListener::send);

        // Then we can resent the tags
        packetListener.send(new ClientboundUpdateTagsPacket(
                TagNetworkSerialization.serializeTagsToNetwork(registries)
        ));
    }


    private static Field getField(Class<?> clazz, String... names) {
        for (String name : names) {
            try {
                Field field = clazz.getDeclaredField(name);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException _) {
            }
        }
        throw new IllegalArgumentException(clazz.getName() + " has no fields: " + Arrays.toString(names));
    }

    private static Field getField(List<String> classes, List<String> names) {
        for (String name : names) {
            for (String clazz : classes) {
                try {
                    return getField(Class.forName(clazz), name);
                } catch (ClassNotFoundException _) {
                }
            }
        }
        throw new IllegalArgumentException(classes + " has no fields: " + names);
    }

    private record PendingResync(GameProfile profile, CompletableFuture<Void> future, @Nullable Consumer<PlayerConfigurationConnection> consumer) {}
}
