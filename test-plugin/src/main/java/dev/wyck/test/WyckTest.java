package dev.wyck.test;

import com.google.common.base.Preconditions;
import dev.wyck.biome.CustomBiome;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.level.LevelStemEditor;
import dev.wyck.worldgen.biome.BiomeSource;
import dev.wyck.worldgen.chunk.ChunkGenerator;
import dev.wyck.worldgen.noise.Noise;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class WyckTest extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        CustomBiome biome = CustomBiome.builder()
            .resourceKey(ResourceKey.of("wyck:test"))
            .fogColor("#ffffff")
            .register();

        ChunkGenerator chunkGenerator = ChunkGenerator.noise()
            .biomeSource(BiomeSource.fixed(biome))
            .noise(Noise.nether())
            .build();

        LevelStemEditor editor = LevelStemEditor.create()
            .chunkGenerator(chunkGenerator);

        World overworld = Bukkit.getWorld(Key.key("minecraft", "overworld"));
        Preconditions.checkNotNull(overworld, "overworld");
        editor.apply(overworld);
    }
}