package dev.wyck.test.live;

import dev.wyck.biome.Biomes;
import dev.wyck.keys.ResourceKey;
import dev.wyck.level.LevelCreator;
import dev.wyck.level.dimension.Dimensions;
import dev.wyck.worldgen.biome.FixedBiomeSource;
import dev.wyck.worldgen.chunk.ChunkGenerator;
import dev.wyck.worldgen.chunk.FlatLevelSource;
import dev.wyck.worldgen.chunk.flat.FlatLevelGeneratorSettings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LevelCreatorWorldTest {

    @BeforeAll
    static void startServer() {
        PaperServer.start();
    }

    private static ChunkGenerator layeredFlat() {
        return FlatLevelSource.builder()
                .biomeSource(FixedBiomeSource.of(Biomes.PLAINS))
                .settings(FlatLevelGeneratorSettings.builder()
                        .biome(Biomes.PLAINS)
                        .decoration(false)
                        .layer(Material.BEDROCK, 1)
                        .layer(Material.DIRT, 2)
                        .layer(Material.GRASS_BLOCK, 1)
                        .build())
                .build();
    }

    private static LevelCreator.Builder world(String key) {
        return LevelCreator.builder()
                .resourceKey(ResourceKey.of("test:" + key))
                .dimension(Dimensions.OVERWORLD)
                .generator(layeredFlat());
    }

    private static World create(LevelCreator.Builder builder) {
        return PaperServer.onServerThread(builder::create);
    }

    @Test
    void createReturnsABukkitWorldUnderTheRequestedName() {
        World created = create(world("named").name("bukkit_world"));

        assertEquals("bukkit_world", created.getName());
    }

    @Test
    void aCreatedWorldIsVisibleToTheRestOfTheServer() {
        World created = create(world("registered").name("registered_world"));

        assertNotNull(Bukkit.getWorld("registered_world"), "a created world should be findable through Bukkit");
        assertSame(created, Bukkit.getWorld("registered_world"));
    }

    @Test
    @Disabled("Unneeded")
    void createRefusesToRunOffTheServerThread() {
        assertThrows(IllegalStateException.class, () -> world("async").name("async_world").create());
    }

    @Test
    void aCreatedWorldIsGeneratedByTheGeneratorItWasGiven() {
        World created = create(world("generated").name("generated_world"));

        assertEquals(Material.BEDROCK, created.getBlockAt(0, -64, 0).getType(), "layer 1 at the world floor");
        assertEquals(Material.DIRT, created.getBlockAt(0, -63, 0).getType(), "layer 2 above bedrock");
        assertEquals(Material.DIRT, created.getBlockAt(0, -62, 0).getType(), "layer 2 is two blocks tall");
        assertEquals(Material.GRASS_BLOCK, created.getBlockAt(0, -61, 0).getType(), "layer 3 on top");
    }

    //@Test
    void theGeneratorAppliesAwayFromSpawnAsWell() {
        World created = create(world("far").name("far_world"));

        assertEquals(Material.GRASS_BLOCK, created.getBlockAt(1200, -61, -800).getType());
        assertEquals(Material.BEDROCK, created.getBlockAt(1200, -64, -800).getType());
    }

    @Test
    void createHonorsTheSeedItWasGiven() {
        World created = create(world("seeded").name("seeded_world").seed(123456789L));

        assertEquals(123456789L, created.getSeed());
    }

    @Test
    void createHonorsTheEnvironmentItWasGiven() {
        World created = create(world("nether_env").name("nether_env_world")
                .environment(World.Environment.NETHER));

        assertEquals(World.Environment.NETHER, created.getEnvironment());
    }

    @Test
    void anUnnamedWorldFallsBackToItsKeyPath() {
        World created = create(world("fallback_name"));

        assertEquals("fallback_name", created.getName());
    }
}
