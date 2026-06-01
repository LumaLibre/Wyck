package me.outspending.biomesapi;

import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.renderer.setter.BiomeSetter;
import me.outspending.biomesapi.providers.BasicBiomeProvider;
import me.outspending.biomesapi.wrapper.BiomeSettings;
import me.outspending.biomesapi.wrapper.entity.BiomeSpawner;
import me.outspending.biomesapi.wrapper.entity.MobCategory;
import me.outspending.biomesapi.wrapper.entity.data.NaturalSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class BiomesAPITest extends JavaPlugin implements Listener {

    private BiomeSetter biomeSetter;
    private CustomBiome customBiome;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        BiomeSpawner spawner = new BiomeSpawner.Builder()
            .setCreatureGenerationProbability(0.1f)
            .addSpawner(MobCategory.CREATURE, 100, new NaturalSpawner(EntityType.PIG, 4, 12))
            .build();

        customBiome = CustomBiome.builder()
                .resourceKey(BiomeResourceKey.of("test", "custombiome"))
                .settings(BiomeSettings.defaultSettings())
                .fogColor("#FFFFFF") // #db4929
                .foliageColor("#F5F2EB")
                .skyColor("#B99DFC")
                .waterColor("#F5F2EB") // #F5F2EB
                .waterFogColor("#000000")
                .grassColor("#DBE9EC")
                .setSpawner(spawner)
                .register();

        biomeSetter = BiomeSetter.of(this);

        new org.bukkit.WorldCreator("pig_world")
            .biomeProvider(BasicBiomeProvider.of(customBiome))
            .createWorld();
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        biomeSetter.setChunkBiome(event.getChunk(), customBiome);
    }
}