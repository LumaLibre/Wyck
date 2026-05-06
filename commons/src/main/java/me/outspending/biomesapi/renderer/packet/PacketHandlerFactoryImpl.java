package me.outspending.biomesapi.renderer.packet;

import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.exceptions.MissingPacketManipulatorLibraryException;
import me.outspending.biomesapi.renderer.packet.handlers.NettyPacketHandler;
import me.outspending.biomesapi.renderer.packet.handlers.PacketEventsPacketHandler;
import me.outspending.biomesapi.renderer.packet.handlers.ProtocolLibPacketHandler;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

@WireFactory
public final class PacketHandlerFactoryImpl implements PacketHandler.Factory {

    @Override
    public @NotNull PacketHandler create(@NotNull Plugin provider, @NotNull PacketHandler.Injector injector, @NotNull PacketHandler.Priority priority) {
        if (!injector.isAvailable()) {
            throw new MissingPacketManipulatorLibraryException(
                "Could not find required classes for injector: " + injector.getName() +
                ". Please ensure " + injector.getName() + " is installed.");
        }
        return switch (injector) {
            case PROTOCOLLIB -> new ProtocolLibPacketHandler(provider, priority);
            case PACKETEVENTS -> new PacketEventsPacketHandler(priority);
            case NETTY -> new NettyPacketHandler(provider.getName().toLowerCase());
        };
    }
}