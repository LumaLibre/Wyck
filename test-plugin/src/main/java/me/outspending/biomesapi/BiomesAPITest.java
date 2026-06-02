package me.outspending.biomesapi;

import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.biome.RegisteredBiomes;
import me.outspending.biomesapi.registry.BiomeRegistry;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.renderer.setter.BiomeSetter;
import me.outspending.biomesapi.providers.BasicBiomeProvider;
import me.outspending.biomesapi.util.Lazy;
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
    private Lazy<CustomBiome> customBiome = RegisteredBiomes.getLazily(BiomeResourceKey.of("test", "custombiome"));

    @Override
    public void onEnable() {
//        getServer().getPluginManager().registerEvents(this, this);
//        biomeSetter = BiomeSetter.of(this);
//
//        new org.bukkit.WorldCreator("pig_world")
//            .biomeProvider(BasicBiomeProvider.of(customBiome.get()))
//            .createWorld();

        BiomeSpawner spawner = BiomeSpawner.builder()
            .setCreatureGenerationProbability(0.2f)
            .addSpawner(MobCategory.CREATURE, 100, NaturalSpawner.of(EntityType.PIG, 10, 15))
            .build();

        // Purely just a placeholder wrapper
        CustomBiome newData = CustomBiome.builder(BiomeResourceKey.fromString("unused:placeholder"))
            .setSpawner(spawner)
            .build();

        BiomeRegistry registry = BiomeRegistry.registry();
        registry.modify(BiomeResourceKey.minecraft("plains"), newData);
        registry.modify(BiomeResourceKey.minecraft("forest"), newData);
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        //biomeSetter.setChunkBiome(event.getChunk(), customBiome.get());
    }
}