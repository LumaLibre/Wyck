package dev.wyck.renderer.packet;

import dev.wyck.annotations.WireFactory;
import dev.wyck.exceptions.MissingPacketManipulatorLibraryException;
import dev.wyck.renderer.packet.handlers.NettyPacketHandler;
import dev.wyck.renderer.packet.handlers.PacketEventsPacketHandler;
import dev.wyck.renderer.packet.handlers.ProtocolLibPacketHandler;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@ApiStatus.Internal
public final class PacketHandlerFactoryImpl implements PacketHandler.Factory {

    @Override
    public PacketHandler create(Plugin provider, PacketHandler.Injector injector, PacketHandler.Priority priority) {
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