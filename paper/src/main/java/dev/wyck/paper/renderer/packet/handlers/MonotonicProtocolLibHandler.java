package dev.wyck.paper.renderer.packet.handlers;

import dev.wyck.annotations.AsOf;
import dev.wyck.paper.WyckPlugin;
import dev.wyck.renderer.packet.PacketHandler;
import dev.wyck.renderer.packet.VirtualBiomeCollector;
import dev.wyck.renderer.packet.handlers.ProtocolLibPacketHandler;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("2.1.0")
@ApiStatus.Internal
public class MonotonicProtocolLibHandler extends ProtocolLibPacketHandler {

    private boolean registered;

    public MonotonicProtocolLibHandler(Plugin provider, Priority priority, VirtualBiomeCollector collector) {
        super(provider, priority, collector);
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
