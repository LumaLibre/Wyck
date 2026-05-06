package me.outspending.biomesapi.paper.configs;

import me.outspending.biomesapi.renderer.packet.PacketHandler;
import org.jetbrains.annotations.Nullable;

public enum ForcedInjector {
    PROTOCOLLIB(PacketHandler.Injector.PROTOCOLLIB),
    PACKETEVENTS(PacketHandler.Injector.PACKETEVENTS),
    NETTY(PacketHandler.Injector.NETTY),
    NONE(null);

    private final @Nullable PacketHandler.Injector injector;

    ForcedInjector(@Nullable PacketHandler.Injector injector) {
        this.injector = injector;
    }

    public @Nullable PacketHandler.Injector getInjector() {
        return injector;
    }

    @Override
    public String toString() {
        return injector == null ? "None" : injector.getName();
    }
}
