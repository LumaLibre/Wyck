package dev.wyck.test;

import dev.wyck.keys.ResourceKey;
import dev.wyck.model.level.LevelCreator;
import dev.wyck.model.level.dimension.Dimension;
import dev.wyck.wrapper.level.BiomeSource;
import dev.wyck.wrapper.level.noise.Noise;
import dev.wyck.wrapper.level.noise.chunk.ChunkGenerator;
import dev.wyck.wrapper.level.spawner.LevelSpawner;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        // Get your dimension from somewhere or create your own!
        Dimension dimension = Dimension.reference(ResourceKey.of("test:dimension"));
        System.out.println(dimension.skybox());

        // A quick example of a chunk generator with the biomes of the nether and noise generation of the overworld.
        ChunkGenerator chunkGenerator = ChunkGenerator.of(
            BiomeSource.nether(),
            Noise.overworld()
        );

        LevelCreator.builder()
            .resourceKey(ResourceKey.of("test:exampleworld"))
            .name("example_world") // Optionally force a name for the world
            .generateStructures(false)
            .bonusChest(true)
            .seed(123456789L) // Random by default
            .spawners(LevelSpawner.patrol(), LevelSpawner.cat(), LevelSpawner.phantom())
            .dimension(dimension)
            .generator(chunkGenerator)
            .create(); // Build and create the world!
    }
}

    /*

        Biome reference = Biome.reference(ResourceKey.minecraft("desert"));

        BiomeRegistryImpl registry = (BiomeRegistryImpl) BiomeRegistry.registry();
        net.minecraft.world.level.biome.Biome nmsBiome = registry.buildDelegate(customBiome);

        RegistryAccess registryAccess = ((CraftServer) getServer()).getServer().registryAccess();
        RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, registryAccess);

        JsonElement json = net.minecraft.world.level.biome.Biome.DIRECT_CODEC
            .encodeStart(ops, nmsBiome)
            .getOrThrow();
        String result = json.toString();
        // write to /Users/jonah/idea/BiomesAPI/test-plugin/biome.json
        try {
            java.nio.file.Files.writeString(java.nio.file.Paths.get("/Users/jonah/idea/BiomesAPI/test-plugin/biome.json"), result);
        } catch (Exception e) {
            e.printStackTrace();
        }
     */
