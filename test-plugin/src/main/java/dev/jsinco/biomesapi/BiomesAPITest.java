package dev.jsinco.biomesapi;

import me.outspending.biomesapi.BiomeSettings;
import me.outspending.biomesapi.biome.BiomeHandler;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.setter.BiomeSetter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class BiomesAPITest extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        CustomBiome biome = CustomBiome.builder()
                .resourceKey(BiomeResourceKey.of("test", "custombiome"))
                .settings(BiomeSettings.defaultSettings())
                .fogColor("#db4929")
                .foliageColor("#22c1c8")
                .skyColor("#c8227d")
                .waterColor("#c82222")
                .waterFogColor("#b9de2e")
                .grassColor("#40df8b")
                .build();

        biome.register();
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        CustomBiome biome = BiomeHandler.getBiome(BiomeResourceKey.of("test", "custombiome"));
        if (biome == null) return;


        BiomeSetter.of().setBlockBiome(e.getBlock(), biome, true);
    }
}