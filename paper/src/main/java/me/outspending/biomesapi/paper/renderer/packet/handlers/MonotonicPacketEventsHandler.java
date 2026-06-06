package me.outspending.biomesapi.paper.renderer.packet.handlers;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.paper.BiomesAPIPlugin;
import me.outspending.biomesapi.renderer.packet.PacketHandler;
import me.outspending.biomesapi.renderer.packet.PhonyCustomBiomeCollector;
import me.outspending.biomesapi.renderer.packet.handlers.PacketEventsPacketHandler;
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
        if (registered && BiomesAPIPlugin.STOPPING) {
            registered = false;
            return super.unregister();
        }
        return this;
    }
}
