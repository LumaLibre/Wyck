package me.outspending.biomesapi;

import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.renderer.packet.PacketHandler;
import me.outspending.biomesapi.renderer.packet.data.PhonyCustomBiome;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin {
    private PacketHandler packetHandler;

    @Override
    public void onEnable() {
        ResourceKey key = ResourceKey.minecraft("plains");
        PhonyCustomBiome phonyCustomBiome = PhonyCustomBiome.builder()
            .setCustomBiome(key)
            .build();

        this.packetHandler = PacketHandler.of(this)
            .appendBiome(phonyCustomBiome)
            .register();
    }

    @Override
    public void onDisable() {
        this.packetHandler.unregister();
    }
}
