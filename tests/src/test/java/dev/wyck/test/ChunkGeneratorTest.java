package dev.wyck.test;

import dev.wyck.biome.Biomes;
import dev.wyck.worldgen.biome.FixedBiomeSource;
import dev.wyck.worldgen.chunk.ChunkGenerator;
import dev.wyck.worldgen.chunk.FlatLevelSource;
import dev.wyck.worldgen.chunk.flat.FlatLevelGeneratorSettings;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.block.Blocks;
import org.bukkit.Material;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MinecraftBootstrap.class)
class ChunkGeneratorTest {

    private static final LevelHeightAccessor OVERWORLD = LevelHeightAccessor.create(-64, 384);

    private static RandomState randomState() {
        return RandomState.create(
                BootstrapSafeMinecraftRegistries.vanilla(),
                net.minecraft.world.level.levelgen.NoiseGeneratorSettings.OVERWORLD,
                0L);
    }

    private static net.minecraft.world.level.chunk.ChunkGenerator classicFlat() {
        return FlatLevelSource.builder()
                .biomeSource(FixedBiomeSource.of(Biomes.PLAINS))
                .settings(FlatLevelGeneratorSettings.builder()
                        .biome(Biomes.PLAINS)
                        .layer(Material.BEDROCK, 1)
                        .layer(Material.DIRT, 2)
                        .layer(Material.GRASS_BLOCK, 1)
                        .build())
                .build()
                .asHandle();
    }

    @Test
    void aFlatGeneratorProducesItsLayersInOrderFromTheBottom() {
        NoiseColumn column = classicFlat().getBaseColumn(0, 0, OVERWORLD, randomState());

        assertSame(Blocks.BEDROCK, column.getBlock(-64).getBlock(), "layer 1 (bedrock) at the world floor");
        assertSame(Blocks.DIRT, column.getBlock(-63).getBlock(), "layer 2 (dirt) directly above bedrock");
        assertSame(Blocks.DIRT, column.getBlock(-62).getBlock(), "layer 2 is two blocks tall");
        assertSame(Blocks.GRASS_BLOCK, column.getBlock(-61).getBlock(), "layer 3 (grass) on top");
        assertSame(Blocks.AIR, column.getBlock(-60).getBlock(), "nothing above the last layer");
    }

    @Test
    void aFlatGeneratorReportsTheHeightItActuallyBuilt() {
        int height = classicFlat().getBaseHeight(0, 0, Heightmap.Types.WORLD_SURFACE_WG, OVERWORLD, randomState());

        assertEquals(-60, height);
    }

    @Test
    void layerThicknessIsHonored() {
        net.minecraft.world.level.chunk.ChunkGenerator thick = FlatLevelSource.builder()
                .biomeSource(FixedBiomeSource.of(Biomes.PLAINS))
                .settings(FlatLevelGeneratorSettings.builder()
                        .biome(Biomes.PLAINS)
                        .layer(Material.STONE, 10)
                        .build())
                .build()
                .asHandle();

        NoiseColumn column = thick.getBaseColumn(0, 0, OVERWORLD, randomState());

        assertSame(Blocks.STONE, column.getBlock(-64).getBlock(), "bottom of the stone layer");
        assertSame(Blocks.STONE, column.getBlock(-55).getBlock(), "top of the stone layer");
        assertSame(Blocks.AIR, column.getBlock(-54).getBlock(), "one past a 10-block layer");
    }

    @Test
    void aFixedBiomeSourceOffersOnlyItsOneBiome() {
        net.minecraft.world.level.biome.BiomeSource source = classicFlat().getBiomeSource();

        assertEquals(1, source.possibleBiomes().size(), "a fixed source should carry exactly one biome");
        assertEquals(Identifier.parse("minecraft:plains"),
                source.possibleBiomes().iterator().next().unwrapKey().orElseThrow().identifier());
    }

    @Test
    void theChunkGeneratorFactoryReturnsTheSameShapeAsTheBuilder() {
        net.minecraft.world.level.chunk.ChunkGenerator viaFactory = ChunkGenerator.flat()
                .biomeSource(FixedBiomeSource.of(Biomes.PLAINS))
                .settings(FlatLevelGeneratorSettings.builder()
                        .biome(Biomes.PLAINS)
                        .layer(Material.BEDROCK, 1)
                        .layer(Material.DIRT, 2)
                        .layer(Material.GRASS_BLOCK, 1)
                        .build())
                .build()
                .asHandle();

        NoiseColumn viaFactoryColumn = viaFactory.getBaseColumn(0, 0, OVERWORLD, randomState());
        NoiseColumn viaBuilderColumn = classicFlat().getBaseColumn(0, 0, OVERWORLD, randomState());

        for (int y = -64; y < -58; y++) {
            assertSame(viaBuilderColumn.getBlock(y).getBlock(), viaFactoryColumn.getBlock(y).getBlock(), "ChunkGenerator.flat() and FlatLevelSource.builder() disagree at y=" + y);
        }
    }
}
