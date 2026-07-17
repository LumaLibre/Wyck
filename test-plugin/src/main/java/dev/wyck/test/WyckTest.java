package dev.wyck.test;

import com.google.common.base.Preconditions;
import dev.wyck.biome.CustomBiome;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.level.LevelStemEditor;
import dev.wyck.tags.TagSet;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.worldgen.biome.BiomeSource;
import dev.wyck.worldgen.chunk.ChunkGenerator;
import dev.wyck.worldgen.noise.Noise;
import net.kyori.adventure.key.Key;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;

public final class WyckTest extends JavaPlugin implements Listener {

    // TODO: swap to junit for misc tests

    @Override
    public void onEnable() {
        net.minecraft.core.Registry<Block> nms = BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.BLOCK);
        TagSet<@NonNull Material> tag = TagSet.ofBlocks(ResourceKey.of("wyck:test_tag"), Material.STONE, Material.DIRT);
        tag.register();

        nms.getTags().forEach(a -> System.out.println(a.key()));


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