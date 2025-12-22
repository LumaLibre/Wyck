package dev.jsinco.biomesapi;

import me.outspending.biomesapi.BiomeSettings;
import me.outspending.biomesapi.biome.BiomeHandler;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.packet.PacketHandler;
import me.outspending.biomesapi.packet.ProtocolLibPacketHandler;
import me.outspending.biomesapi.packet.data.BlockReplacement;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class BiomesAPITest extends JavaPlugin implements Listener {

    private ProtocolLibPacketHandler packetHandler;

    @Override
    public void onEnable() {
        CustomBiome biome = CustomBiome.builder()
                .resourceKey(BiomeResourceKey.of("test", "custombiome"))
                .settings(BiomeSettings.defaultSettings())
                .fogColor("#FFFFFF") // #db4929
                .foliageColor("#F5F2EB")
                .skyColor("#000000")
                .waterColor("#F5F2EB") // #F5F2EB
                .waterFogColor("#000000")
                .grassColor("#F5F2EB")
                .blockReplacements(new BlockReplacement(Material.BIRCH_LEAVES, Material.ACACIA_LEAVES))
                .build();

        biome.register();
        getServer().getPluginManager().registerEvents(this, this);

        packetHandler = new ProtocolLibPacketHandler(this, PacketHandler.PacketHandlerPriority.NORMAL);
        packetHandler.register();
    }

    @Override
    public void onDisable() {
        packetHandler.unregister();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        CustomBiome biome = BiomeHandler.getBiome(BiomeResourceKey.of("test", "custombiome"));
        if (biome == null) return;

        packetHandler.registerBiomeOverride(e.getPlayer(), biome, player -> true);
        e.getPlayer().sendMessage("registered");

//        World world = e.getBlock().getWorld();
//        for (Chunk loadedChunk : world.getLoadedChunks()) {
//            BiomeSetter.of().setChunkBiome(loadedChunk, biome, true);
//        }


        //BiomeSetter.of().setChunkBiome(e.getBlock().getChunk(), biome, true);
        //BiomeSetter.of().setBlockBiome(e.getBlock(), biome, true);
    }
}