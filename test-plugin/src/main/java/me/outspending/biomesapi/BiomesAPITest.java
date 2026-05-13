package me.outspending.biomesapi;

import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.connection.RegistryReconfigurer;
import me.outspending.biomesapi.exceptions.HorriblePlayerLoginEvent;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.renderer.packet.PacketHandler;
import me.outspending.biomesapi.wrapper.BiomeSettings;
import me.outspending.biomesapi.wrapper.environment.attribute.WrappedEnvironmentAttributes;
import me.outspending.biomesapi.wrapper.environment.particle.WrappedParticleTypes;
import me.outspending.biomesapi.wrapper.environment.particle.options.DustParticle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public final class BiomesAPITest extends JavaPlugin implements Listener {

    private static final Random RANDOM = new Random();


    private PacketHandler packetHandler;
    private RegistryReconfigurer reconfigurer;
    private CustomBiome customBiome;

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(this, this);


        customBiome = CustomBiome.builder()
                .resourceKey(BiomeResourceKey.of("test", "custombiome"))
                .settings(BiomeSettings.defaultSettings())
                .fogColor("#FFFFFF") // #db4929
                .foliageColor("#F5F2EB")
                .skyColor("#B99DFC")
                .waterColor("#F5F2EB") // #F5F2EB
                .waterFogColor("#000000")
                .grassColor("#DBE9EC")
                .ambientParticle(WrappedParticleTypes.DUST, 0.1f, DustParticle.of("#B99DFC"))
                .setAttribute(WrappedEnvironmentAttributes.BLOCK_LIGHT_TINT, "#FF10F0")
                .setAttribute(WrappedEnvironmentAttributes.SKY_LIGHT_FACTOR, 0.9f)
                //.setAttribute(WrappedEnvironmentAttributes.SKY_LIGHT_COLOR, "#FDE3AA")
                .register();

        var phonyCustomBiome = customBiome.toPhony();


        packetHandler = PacketHandler.of(this, PacketHandler.Injector.NETTY)
                .appendBiome(phonyCustomBiome)
                .register();


        reconfigurer = RegistryReconfigurer.newReconfigurer();
    }

    @Override
    public void onDisable() {
        packetHandler.unregister();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();

        player.sendMessage("Resyncing registries soon...");
        player.getScheduler().runDelayed(this, t -> {
            try {
                reconfigurer.resendRegistries(player, _ -> {
                    customBiome.setWaterColor(RANDOM.nextInt(0xFFFFFF)).modify();
                });
            } catch (HorriblePlayerLoginEvent ex) {
                throw new RuntimeException(ex);
            }
        }, null, 2);
    }


}


//        CustomBiome customBiome = BiomeHandler.getBiome(BiomeResourceKey.of("test", "custombiome"));
//        if (customBiome == null) {
//            throw new IllegalStateException("Custom biome not found!");
//        }
//
//        Player player = e.getPlayer();
//        player.getConnection().reenterConfiguration();
//
//        BiomeSetter biomeSetter = BiomeSetter.of();
//        biomeSetter.setChunkBiome(e.getBlock().getChunk(), customBiome, true);