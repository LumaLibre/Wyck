package me.outspending.biomesapi;

import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.level.LevelCreator;
import me.outspending.biomesapi.level.dimension.Dimension;
import me.outspending.biomesapi.registry.bootstrap.util.BootstrapSafeMinecraftRegistries;
import me.outspending.biomesapi.registry.level.LevelFactory;
import me.outspending.biomesapi.wrapper.level.BiomeSource;
import me.outspending.biomesapi.wrapper.level.noise.Noise;
import me.outspending.biomesapi.wrapper.level.noise.chunk.ChunkGenerator;
import net.minecraft.core.registries.Registries;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class BiomesAPITest extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        //OverworldBiomeBuilder

        var registry = BootstrapSafeMinecraftRegistries.mappedRegistry(
            Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST);

        registry.entrySet().forEach(entry ->
            getLogger().info("Biome source preset: " + entry.getKey().identifier()));


        BiomeSource src = BiomeSource.overworld();
        getLogger().info("preset src = " + src);
        ChunkGenerator generator = ChunkGenerator.noise(src, Noise.overworld());

        ResourceKey dimKey = ResourceKey.of("test", "example");
        Dimension dimension = Dimension.builder(dimKey)
            .build();


        ResourceKey levelKey = ResourceKey.of("test:exampleworld");
        LevelCreator spec = LevelCreator.builder(levelKey)
            .dimension(dimension)
            .generator(generator)
            .build();


        World world = LevelFactory.factory().createWorld(spec);
    }
}