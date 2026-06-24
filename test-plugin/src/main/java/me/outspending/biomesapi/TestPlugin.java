package me.outspending.biomesapi;

import me.outspending.biomesapi.level.dimension.Dimension;
import me.outspending.biomesapi.level.LevelCreator;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.registry.level.LevelFactory;
import me.outspending.biomesapi.registry.level.dimension.DimensionRegistry;
import me.outspending.biomesapi.wrapper.environment.attribute.WrappedEnvironmentAttributes;
import me.outspending.biomesapi.wrapper.level.BiomeSource;
import me.outspending.biomesapi.wrapper.level.clock.WorldClock;
import me.outspending.biomesapi.wrapper.level.dimension.Skybox;
import me.outspending.biomesapi.wrapper.level.noise.LevelNoiseGeneratorSettings;
import me.outspending.biomesapi.wrapper.level.noise.LevelNoiseRouter;
import me.outspending.biomesapi.wrapper.level.noise.Noises;
import me.outspending.biomesapi.wrapper.level.noise.chunk.LevelChunkGenerator;
import me.outspending.biomesapi.wrapper.level.noise.function.DensityFunction;
import me.outspending.biomesapi.wrapper.level.noise.function.DensityFunctions;
import me.outspending.biomesapi.wrapper.level.noise.settings.LevelNoiseSettings;
import me.outspending.biomesapi.wrapper.worldgen.climate.BiomeClimatePoint;
import me.outspending.biomesapi.wrapper.worldgen.surface.SurfaceCondition;
import me.outspending.biomesapi.wrapper.worldgen.surface.SurfaceRule;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class TestPlugin extends JavaPlugin implements Listener {

    static final long SEED = 8675309L;

    ResourceKey dimKey = ResourceKey.of("test", "biomesand");
    Dimension dimension = Dimension.builder(dimKey)
        .hasSkyLight(true)
        .height(800)
        .logicalHeight(800)
        .skybox(Skybox.OVERWORLD)
        .defaultClock(WorldClock.OVERWORLD)
        .attribute(WrappedEnvironmentAttributes.FOG_COLOR, -4138753)
        .build();

    @Override
    public void onEnable() {
        ResourceKey levelKey = ResourceKey.of("test", "wobbleworld");

        BiomeSource biomeSource = BiomeSource.multiNoise()
            .add(ResourceKey.minecraft("snowy_taiga"), BiomeClimatePoint.of(-0.7f, -0.2f, 0f, 0f, 0f, 0f, 0f))
            .add(ResourceKey.minecraft("taiga"), BiomeClimatePoint.of(-0.3f, 0.4f, 0f, 0f, 0f, 0f, 0f))
            .add(ResourceKey.minecraft("plains"), BiomeClimatePoint.of(0.1f, -0.4f, 0f, 0f, 0f, 0f, 0f))
            .add(ResourceKey.minecraft("forest"), BiomeClimatePoint.of(0.2f, 0.3f, 0f, 0f, 0f, 0f, 0f))
            .add(ResourceKey.minecraft("desert"), BiomeClimatePoint.of(0.8f, -0.6f, 0f, 0f, 0f, 0f, 0f))
            .add(ResourceKey.minecraft("jungle"), BiomeClimatePoint.of(0.8f, 0.7f, 0f, 0f, 0f, 0f, 0f))
            .build();

        DensityFunction wobble = DensityFunction.noise(Noises.SPAGHETTI_3D_1, 0.4, 0.9)
            .mul(DensityFunction.constant(1.0))
            .mul(DensityFunction.noise(Noises.SPAGHETTI_3D_2, 0.4, 0.9))
            .mul(DensityFunctions.EROSION_LARGE);

        DensityFunction base = DensityFunction.yClampedGradient(0, 256, 1.2, -1.2);
        DensityFunction finalDensity = DensityFunction.add(base, wobble).clamp(-1.0, 1.0);

        LevelNoiseRouter router = LevelNoiseRouter.builder()
            .barrier(DensityFunction.constant(0.0))
            .fluidLevelFloodedness(DensityFunction.constant(0.0))
            .fluidLevelSpread(DensityFunction.constant(0.0))
            .lava(DensityFunction.constant(0.0))
            .temperature(DensityFunction.noise(ResourceKey.minecraft("temperature"), 0.25, 0.0))
            .vegetation(DensityFunction.noise(ResourceKey.minecraft("vegetation"), 0.25, 0.0))
            .continents(DensityFunction.constant(0.0))
            .erosion(DensityFunction.constant(0.0))
            .depth(DensityFunction.constant(0.0))
            .ridges(DensityFunction.constant(0.0))
            .preliminarySurfaceLevel(DensityFunction.constant(0.0))
            .finalDensity(finalDensity)
            .veinToggle(DensityFunction.constant(-1.0))
            .veinRidged(DensityFunction.constant(0.0))
            .veinGap(DensityFunction.constant(0.0))
            .build();

        SurfaceCondition onFloor = SurfaceCondition.stoneDepth(0, false, SurfaceCondition.CaveSurface.FLOOR);
        SurfaceCondition underFloor = SurfaceCondition.stoneDepth(0, true, SurfaceCondition.CaveSurface.FLOOR);

        SurfaceRule topBlocks = SurfaceRule.sequence(List.of(
            SurfaceRule.ifTrue(SurfaceCondition.isBiome(List.of(ResourceKey.minecraft("desert"))), SurfaceRule.block(Material.SAND)),
            SurfaceRule.ifTrue(SurfaceCondition.isBiome(List.of(ResourceKey.minecraft("snowy_taiga"))), SurfaceRule.block(Material.SNOW_BLOCK)),
            SurfaceRule.ifTrue(SurfaceCondition.isBiome(List.of(ResourceKey.minecraft("taiga"))), SurfaceRule.block(Material.PODZOL)),
            SurfaceRule.block(Material.GRASS_BLOCK)
        ));

        SurfaceRule subBlocks = SurfaceRule.sequence(List.of(
            SurfaceRule.ifTrue(SurfaceCondition.isBiome(List.of(ResourceKey.minecraft("desert"))), SurfaceRule.block(Material.SANDSTONE)),
            SurfaceRule.block(Material.DIRT)
        ));

        SurfaceRule surfaceRule = SurfaceRule.sequence(List.of(
            SurfaceRule.ifTrue(onFloor, topBlocks),
            SurfaceRule.ifTrue(underFloor, subBlocks)
        ));

        LevelNoiseGeneratorSettings noiseSettings = LevelNoiseGeneratorSettings.builder()
            .noiseSettings(LevelNoiseSettings.OVERWORLD)
            .defaultBlock(Material.STONE)
            .defaultFluid(Material.WATER)
            .noiseRouter(router)
            .surfaceRule(surfaceRule)
            .seaLevel(63)
            .aquifersEnabled(false)
            .oreVeinsEnabled(false)
            .build();

        LevelChunkGenerator generator = LevelChunkGenerator.noise(biomeSource, noiseSettings);

        LevelCreator spec = LevelCreator.builder(levelKey)
            .dimension(dimension)
            .generator(generator)
            .build();

        World world = LevelFactory.factory().createWorld(spec);
        System.out.println("Created world: " + world.getName());

        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        DimensionRegistry registry = DimensionRegistry.registry();
        Skybox randomSkybox = Skybox.values()[(int) (Math.random() * Skybox.values().length)];
        dimension.setSkybox(randomSkybox);
        registry.modify(dimension);
        event.getPlayer().sendMessage("Skybox changed to " + randomSkybox);
    }
}