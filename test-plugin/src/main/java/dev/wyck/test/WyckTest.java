package dev.wyck.test;

import dev.wyck.registry.level.LevelStemEditor;
import dev.wyck.worldgen.biome.BiomeSource;
import dev.wyck.worldgen.chunk.ChunkGenerator;
import dev.wyck.worldgen.chunk.flat.FlatLevelGeneratorSettings;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class WyckTest extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        ChunkGenerator chunkGenerator = ChunkGenerator.flat()
            .biomeSource(BiomeSource.nether())
            .settings(FlatLevelGeneratorSettings.OVERWORLD)
            .build();

        LevelStemEditor editor = LevelStemEditor.create()
            .chunkGenerator(chunkGenerator);

        World overworld = Bukkit.getWorld(Key.key("minecraft", "overworld"));

        for (World world : Bukkit.getWorlds()) {
            editor.apply(world);
        }
    }
}