package me.outspending.biomesapi;

import io.papermc.paper.connection.PlayerConfigurationConnection;
import io.papermc.paper.event.connection.configuration.PlayerConnectionReconfigureEvent;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.packet.PacketHandler;
import me.outspending.biomesapi.packet.data.PhonyCustomBiome;
import me.outspending.biomesapi.packet.data.BlockReplacement;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.wrapper.AmbientParticle;
import me.outspending.biomesapi.renderer.ParticleRenderer;
import me.outspending.biomesapi.wrapper.BiomeSettings;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class BiomesAPITest extends JavaPlugin implements Listener {

    //CustomBiome yellowBiome = CustomBiome.builder()
    //                .resourceKey(BiomeResourceKey.of("test", "custombiome"))
    //                .settings(BiomeSettings.defaultSettings())
    //                .fogColor("#ffe606") // #db4929
    //                .foliageColor("#ffe606")
    //                .skyColor("#ffe606")
    //                .waterColor("#ffe606") // #F5F2EB
    //                .waterFogColor("#ffe606")
    //                .grassColor("#ffe606")
    //                .particleRenderer(ParticleRenderer.of(AmbientParticle.DRIPPING_HONEY, 1.0f))
    //                .blockReplacements(new BlockReplacement(Material.OAK_LEAVES, Material.SPRUCE_LEAVES))
    //                .build();



    private final List<UUID> reconfiguring = new ArrayList<>();
    private PacketHandler packetHandler;

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(this, this);

        CustomBiome biome = CustomBiome.builder()
                .resourceKey(BiomeResourceKey.of("test", "custombiome"))
                .settings(BiomeSettings.defaultSettings())
                .fogColor("#FFFFFF") // #db4929
                .foliageColor("#F5F2EB")
                .skyColor("#000000")
                .waterColor("#F5F2EB") // #F5F2EB
                .waterFogColor("#000000")
                .grassColor("#9D00FF")
                .particleRenderer(ParticleRenderer.of(AmbientParticle.WITCH, 0.01f))
                .blockReplacements(
                        BlockReplacement.of(Material.GRASS_BLOCK, Material.WATER),
                        BlockReplacement.of(Material.STONE, Material.DIAMOND_BLOCK)
                )
                .build();

        biome.register();


        packetHandler = PacketHandler.of(this, PacketHandler.Manipulator.PACKETEVENTS);
        packetHandler.register();


        PhonyCustomBiome phonyCustomBiome = PhonyCustomBiome.builder()
                .setCustomBiome(biome)
                .build();

        packetHandler.appendBiome(phonyCustomBiome);
    }

    @Override
    public void onDisable() {
        packetHandler.unregister();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Bukkit.getScheduler().runTaskLater(this, () -> {
            Player player = e.getPlayer();

            player.getConnection().reenterConfiguration();
            reconfiguring.add(player.getUniqueId());
        }, 20);


    }


    @EventHandler
    public void onPlayerPlayerConnectionReconfigure(PlayerConnectionReconfigureEvent event) {
        PlayerConfigurationConnection connection = event.getConnection();
        connection.completeReconfiguration();

        UUID uuid = connection.getProfile().getId();
        if (reconfiguring.contains(uuid)) {
            connection.getAudience().sendMessage(Component.text("Reconfigured connection for biome changes!"));
            reconfiguring.remove(uuid);
        }
    }
}