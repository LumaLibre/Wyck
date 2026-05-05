package me.outspending.biomesapi;

import me.outspending.biomesapi.biome.BiomeHandler;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.renderer.packet.PacketHandler;
import me.outspending.biomesapi.renderer.setter.BiomeSetter;
import me.outspending.biomesapi.wrapper.BiomeSettings;
import me.outspending.biomesapi.wrapper.environment.attribute.WrappedEnvironmentAttributes;
import me.outspending.biomesapi.wrapper.environment.particle.WrappedParticleTypes;
import me.outspending.biomesapi.wrapper.environment.particle.options.DustParticle;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class BiomesAPITest extends JavaPlugin implements Listener {


    private PacketHandler packetHandler;

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(this, this);


        var phonyCustomBiome = CustomBiome.builder()
                .resourceKey(BiomeResourceKey.of("test", "custombiome"))
                .settings(BiomeSettings.defaultSettings())
                //.fogColor("#FFFFFF") // #db4929
                //.foliageColor("#F5F2EB")
                //.skyColor("#B99DFC")
                //.waterColor("#F5F2EB") // #F5F2EB
                //.waterFogColor("#000000")
                //.grassColor("#DBE9EC")
                .ambientParticle(WrappedParticleTypes.DUST, 0.1f, DustParticle.of("#B99DFC"))
                .setAttribute(WrappedEnvironmentAttributes.BLOCK_LIGHT_TINT, "#FF10F0")
                .setAttribute(WrappedEnvironmentAttributes.SKY_LIGHT_FACTOR, 0.9f)
                //.setAttribute(WrappedEnvironmentAttributes.SKY_LIGHT_COLOR, "#FDE3AA")
                .register()
                .asPhony()
                .setConditional(((player, chunkLocation) -> {
                    return chunkLocation.x() % 2 == 0 && chunkLocation.z() % 2 == 0; // Only apply to chunks where both x and z are even
                }))
                .build();



        packetHandler = PacketHandler.of(this, PacketHandler.Injector.NETTY)
                        .appendBiome(phonyCustomBiome)
                        .register();
    }

    @Override
    public void onDisable() {
        packetHandler.unregister();
    }

    //@EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        CustomBiome customBiome = BiomeHandler.getBiome(BiomeResourceKey.of("test", "custombiome"));
        if (customBiome == null) {
            throw new IllegalStateException("Custom biome not found!");
        }

        Player player = e.getPlayer();

        BiomeSetter biomeSetter = BiomeSetter.of();
        biomeSetter.setChunkBiome(e.getBlock().getChunk(), customBiome, true);

    }
}