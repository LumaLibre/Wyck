package me.outspending.biomesapi;

import me.outspending.biomesapi.biome.BiomeHandler;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.packet.PacketHandler;
import me.outspending.biomesapi.packet.data.PhonyCustomBiome;
import me.outspending.biomesapi.packet.data.BlockReplacement;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class BiomesAPITest extends JavaPlugin implements Listener {

    private PacketHandler packetHandler;

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

        CustomBiome yellowBiome = CustomBiome.builder()
                .resourceKey(BiomeResourceKey.of("test", "yellow"))
                .settings(BiomeSettings.defaultSettings())
                .fogColor("#ffe606") // #db4929
                .foliageColor("#ffe606")
                .skyColor("#ffe606")
                .waterColor("#ffe606") // #F5F2EB
                .waterFogColor("#ffe606")
                .grassColor("#ffe606")
                .blockReplacements(new BlockReplacement(Material.OAK_LEAVES, Material.SPRUCE_LEAVES))
                .build();

        biome.register();
        yellowBiome.register();
        getServer().getPluginManager().registerEvents(this, this);

        packetHandler = PacketHandler.of(this);
        packetHandler.register();
    }

    @Override
    public void onDisable() {
        packetHandler.unregister();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        CustomBiome biome = BiomeHandler.getBiome(BiomeResourceKey.of("test", "custombiome"));
        CustomBiome yellowBiome = BiomeHandler.getBiome(BiomeResourceKey.of("test", "yellow"));
        if (biome == null || yellowBiome == null) {
            e.getPlayer().sendMessage("biome is null");
            return;
        }

        PhonyCustomBiome phonyCustomBiome = PhonyCustomBiome.builder()
                .setCustomBiome(biome)
                .setConditional((player, chunkLocation) -> {
                    return chunkLocation.x() % 2 == 0 && chunkLocation.z() % 2 == 0;
                })
                .build();
        PhonyCustomBiome phonyYellowBiome = PhonyCustomBiome.builder()
                .setCustomBiome(yellowBiome)
                .setConditional((player, chunkLocation) -> {
                    return chunkLocation.x() % 2 != 0 || chunkLocation.z() % 2 != 0;
                })
                .build();

        packetHandler.appendBiome(phonyCustomBiome);
        packetHandler.appendBiome(phonyYellowBiome);
        e.getPlayer().sendMessage("registered");

//        World world = e.getBlock().getWorld();
//        for (Chunk loadedChunk : world.getLoadedChunks()) {
//            BiomeSetter.of().setChunkBiome(loadedChunk, biome, true);
//        }


        //BiomeSetter.of().setChunkBiome(e.getBlock().getChunk(), biome, true);
        //BiomeSetter.of().setBlockBiome(e.getBlock(), biome, true);
    }
}