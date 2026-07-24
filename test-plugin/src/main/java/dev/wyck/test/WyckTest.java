package dev.wyck.test;

import com.google.common.base.Preconditions;
import dev.wyck.biome.Biome;
import dev.wyck.biome.Biomes;
import dev.wyck.biome.CustomBiome;
import dev.wyck.keys.ResourceKey;
import dev.wyck.level.LevelCreator;
import dev.wyck.level.LevelType;
import dev.wyck.level.dimension.Dimension;
import dev.wyck.level.dimension.Dimensions;
import dev.wyck.registry.level.LevelStemEditor;
import dev.wyck.tags.TagSet;
import dev.wyck.worldgen.biome.BiomeSource;
import dev.wyck.worldgen.chunk.ChunkGenerator;
import dev.wyck.worldgen.chunk.FlatLevelSource;
import dev.wyck.worldgen.chunk.flat.FlatLevelGeneratorSettings;
import dev.wyck.worldgen.climate.ClimateParameter;
import dev.wyck.worldgen.climate.ClimatePoint;
import dev.wyck.worldgen.noise.Noise;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class WyckTest extends JavaPlugin implements Listener {

    // TODO: swap to junit for misc tests

    @Override
    public void onEnable() {

        // there are more settings on climate points that im not exhaustively listing out
        BiomeSource multiNoise = BiomeSource.multiNoise()
            .add(Biomes.RIVER, ClimatePoint.builder().depth(ClimateParameter.span(1.0f, 1.5f)).build())
            .add(Biome.builder(ResourceKey.of("wyck:custombiome")).build(), ClimatePoint.builder().erosion(ClimateParameter.span(0.0f, 0.5f)).build())
            .build();

        ChunkGenerator flatGenerator = FlatLevelSource.builder()
            .settings(FlatLevelGeneratorSettings.THE_VOID) // You can make your own, if you prefer
            .biomeSource(BiomeSource.fixed(Biomes.THE_VOID))
            .build();

        Dimension dimension = Dimensions.OVERWORLD;

        World world = LevelCreator.builder()
            .levelKey(ResourceKey.of("wyck:test_world"))
            .generator(flatGenerator)
            .dimension(dimension)
            .type(LevelType.FLAT)
            .environment(World.Environment.NORMAL) // doesn't really do anything, just bukkit nonsense — you can omit if it not needed
            .create();


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

        TagSet<Material> test = TagSet.blocks()
            .resourceKey(ResourceKey.of("wyck:testing"))
            .value(Material.DIAMOND_ORE)
            .value(Material.ACACIA_LOG)
            .register()
            .toBuilder()
            .resourceKey(ResourceKey.of("wyck:testing2"))
            .value(Material.ACACIA_DOOR)
            .register();
    }
}