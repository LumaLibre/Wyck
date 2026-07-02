package dev.wyck.paper.renderer.packet.handlers;

import dev.wyck.annotations.AsOf;
import dev.wyck.paper.WyckPlugin;
import dev.wyck.renderer.packet.PacketHandler;
import dev.wyck.renderer.packet.PhonyCustomBiomeCollector;
import dev.wyck.renderer.packet.handlers.PacketEventsPacketHandler;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("2.1.0")
@ApiStatus.Internal
public class MonotonicPacketEventsHandler extends PacketEventsPacketHandler {

    private boolean registered;

    public MonotonicPacketEventsHandler(Priority priority, PhonyCustomBiomeCollector collector) {
        super(priority, collector);
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
