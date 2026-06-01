package me.outspending.biomesapi.paper.renderer.packet.handlers;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.paper.BiomesAPIPlugin;
import me.outspending.biomesapi.renderer.packet.PacketHandler;
import me.outspending.biomesapi.renderer.packet.PhonyCustomBiomeCollector;
import me.outspending.biomesapi.renderer.packet.handlers.ProtocolLibPacketHandler;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@AsOf("2.1.0")
@ApiStatus.Internal
public class MonotonicProtocolLibHandler extends ProtocolLibPacketHandler {

    private boolean registered;

    public MonotonicProtocolLibHandler(@NotNull Plugin provider, @NotNull Priority priority, @NotNull PhonyCustomBiomeCollector collector) {
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
        if (registered && BiomesAPIPlugin.STOPPING) {
            registered = false;
            return super.unregister();
        }
        return this;
    }
}
