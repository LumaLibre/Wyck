package me.outspending.biomesapi.paper.renderer.packet;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.exceptions.MissingPacketManipulatorLibraryException;
import me.outspending.biomesapi.paper.BiomesAPIPlugin;
import me.outspending.biomesapi.paper.renderer.packet.handlers.MonotonicNettyHandler;
import me.outspending.biomesapi.paper.renderer.packet.handlers.MonotonicPacketEventsHandler;
import me.outspending.biomesapi.paper.renderer.packet.handlers.MonotonicProtocolLibHandler;
import me.outspending.biomesapi.renderer.packet.PacketHandler;
import me.outspending.biomesapi.renderer.packet.PhonyCustomBiomeCollector;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WireFactory
@AsOf("2.1.0")
@ApiStatus.Internal
public final class PacketHandlerFactoryPluginImpl implements PacketHandler.Factory {

    private final BiomesAPIPlugin plugin;
    private final Map<PacketHandler.Injector, PacketHandler> injectorMap = new HashMap<>();
    private final List<PacketHandler> pendingRegistration = new ArrayList<>();
    private final PhonyCustomBiomeCollector collector = new PhonyCustomBiomeCollector();

    private boolean enabled = false;

    public PacketHandlerFactoryPluginImpl(BiomesAPIPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull PacketHandler create(@NotNull Plugin provider, @NotNull PacketHandler.Injector injector, @NotNull PacketHandler.Priority priority) {
        PacketHandler.Injector effective = applyForcedOverride(injector);

        if (!effective.isAvailable()) {
            throw new MissingPacketManipulatorLibraryException(
                    "Could not find required classes for injector: " + effective.getName() +
                            ". Please ensure " + effective.getName() + " is installed.");
        }

        if (injectorMap.containsKey(effective)) {
            return injectorMap.get(effective);
        }

        PacketHandler handler = switch (effective) {
            case PROTOCOLLIB -> new MonotonicProtocolLibHandler(plugin, priority, collector);
            case PACKETEVENTS -> new MonotonicPacketEventsHandler(priority, collector);
            case NETTY -> new MonotonicNettyHandler(plugin, collector);
        };

        if (enabled) {
            handler.register();
            plugin.getComponentLogger().info("Registered {} packet handler.", effective.getName());
        } else {
            pendingRegistration.add(handler);
            plugin.getComponentLogger().info("Deferred {} packet handler registration until onEnable.", effective.getName());
        }

        injectorMap.put(effective, handler);

        if (effective != injector) {
            plugin.getComponentLogger().info(
                    "Plugin {} requested {} but config forces {}.",
                    provider.getName(), injector.getName(), effective.getName());
        }
        return handler;
    }

    /**
     * Registers all handlers created before {@code onEnable}. Should be called from
     * {@link BiomesAPIPlugin#onEnable()}.
     */
    public void enableDeferredHandlers() {
        for (PacketHandler handler : pendingRegistration) {
            handler.register();
            plugin.getComponentLogger().info("Registered deferred {} packet handler.",
                    handler.getClass().getSimpleName());
        }
        pendingRegistration.clear();
        enabled = true;
    }

    public Collection<PacketHandler> getHandlers() {
        return injectorMap.values();
    }

    private @NotNull PacketHandler.Injector applyForcedOverride(@NotNull PacketHandler.Injector requested) {
        PacketHandler.Injector override = plugin.getPluginConfig().forcedInjector.getInjector();
        return override != null ? override : requested;
    }
}