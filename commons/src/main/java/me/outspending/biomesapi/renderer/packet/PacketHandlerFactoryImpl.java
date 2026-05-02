package me.outspending.biomesapi.renderer.packet;

import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.exceptions.MissingPacketManipulatorLibraryException;
import me.outspending.biomesapi.renderer.packet.handlers.PacketEventsPacketHandler;
import me.outspending.biomesapi.renderer.packet.handlers.ProtocolLibPacketHandler;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

@WireFactory
public final class PacketHandlerFactoryImpl implements PacketHandler.Factory {

    @Override
    public @NotNull PacketHandler create(@NotNull Plugin provider, @NotNull PacketHandler.Manipulator manipulator, @NotNull PacketHandler.Priority priority) {
        if (!manipulator.isAvailable()) {
            throw new MissingPacketManipulatorLibraryException(
                "Could not find required classes for " + manipulator.getName() +
                ". Please ensure " + manipulator.getName() + " is installed.");
        }
        return switch (manipulator) {
            case PROTOCOLLIB -> new ProtocolLibPacketHandler(provider, priority);
            case PACKETEVENTS -> new PacketEventsPacketHandler(priority);
        };
    }
}