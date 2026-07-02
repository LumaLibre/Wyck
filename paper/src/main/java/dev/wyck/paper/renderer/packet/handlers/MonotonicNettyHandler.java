package dev.wyck.paper.renderer.packet.handlers;

import dev.wyck.annotations.AsOf;
import dev.wyck.paper.WyckPlugin;
import dev.wyck.renderer.packet.PacketHandler;
import dev.wyck.renderer.packet.PhonyCustomBiomeCollector;
import dev.wyck.renderer.packet.handlers.NettyPacketHandler;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("2.1.0")
@ApiStatus.Internal
public class MonotonicNettyHandler extends NettyPacketHandler {

    private boolean registered;

    public MonotonicNettyHandler(String name, PhonyCustomBiomeCollector collector) {
        super(name, collector);
    }

    @Override
    public PacketHandler register() {
        if (registered) {
            return this;
        }
        registered = true;
        return super.register();
    }

    @Override
    public PacketHandler unregister() {
        if (registered && WyckPlugin.STOPPING) {
            registered = false;
            return super.unregister();
        }
        return this;
    }
}
