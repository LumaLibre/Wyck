package me.outspending.biomesapi;

import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.level.dimension.Dimension;
import me.outspending.biomesapi.level.LevelCreator;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.registry.level.LevelFactory;
import me.outspending.biomesapi.wrapper.BiomeSettings;
import me.outspending.biomesapi.wrapper.level.BiomeSource;
import me.outspending.biomesapi.wrapper.level.dimension.Skybox;
import me.outspending.biomesapi.wrapper.environment.BedRule;
import me.outspending.biomesapi.wrapper.environment.attribute.WrappedEnvironmentAttributes;
import me.outspending.biomesapi.wrapper.level.noise.LevelNoiseGeneratorSettings;
import me.outspending.biomesapi.wrapper.level.noise.LevelNoiseRouter;
import me.outspending.biomesapi.wrapper.level.noise.Noise;
import me.outspending.biomesapi.wrapper.level.noise.chunk.LevelChunkGenerator;
import me.outspending.biomesapi.wrapper.level.noise.function.DensityFunction;
import me.outspending.biomesapi.wrapper.level.noise.function.DensityFunctions;
import me.outspending.biomesapi.wrapper.level.noise.settings.LevelNoiseSettings;
import me.outspending.biomesapi.wrapper.worldgen.climate.BiomeClimatePoint;
import me.outspending.biomesapi.wrapper.worldgen.surface.WrappedSurfaceRule;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class TestPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        var myBiomeKey = ResourceKey.of("test", "mybiome");
        CustomBiome mybiome = CustomBiome.builder(myBiomeKey)
            .settings(BiomeSettings.defaultSettings())
            .fogColor("#DB00FD")
            .skyColor("#2F46FF")
            .waterColor("#00FFD0")
            .grassColor("#D1D13A")
            .foliageColor("#FF6A00")
            .build()
            .register();

        ResourceKey dimKey = ResourceKey.of("test", "awesome");
        ResourceKey levelKey = ResourceKey.of("test", "awesome");

        BedRule bedrule = BedRule.builder()
            .setExplodes(true)
            .setCanSleep(BedRule.Rule.NEVER)
            .setErrorMessage(Component.text("no sleeping"))
            .setCanSetSpawn(BedRule.Rule.NEVER)
            .build();

        Dimension dimension = Dimension.builder(dimKey)
            .hasSkyLight(true)
            .skybox(Skybox.NONE)
            .attribute(WrappedEnvironmentAttributes.FAST_LAVA, true)
            .attribute(WrappedEnvironmentAttributes.BED_RULE, bedrule)
            .attribute(WrappedEnvironmentAttributes.FOG_COLOR, "#FFFFFF")
            .attribute(WrappedEnvironmentAttributes.SKY_COLOR, "#FFFFFF")
            .build();

        BiomeSource biomeSource = BiomeSource.multiNoise()
            .add(ResourceKey.minecraft("cherry_grove"), BiomeClimatePoint.builder().build())
            .build();

        DensityFunction zero = DensityFunction.zero();

        DensityFunction finalDensity = DensityFunction.add(
                DensityFunction.yClampedGradient(0, 128, 1.0, -1.0),
                DensityFunction.constant(0.05)
            )
            .clamp(-1.0, 1.0);

        LevelNoiseRouter router = LevelNoiseRouter.builder()
            .barrier(zero)
            .fluidLevelFloodedness(zero)
            .fluidLevelSpread(zero)
            .lava(zero)
            .temperature(zero)
            .vegetation(zero)
            .continents(DensityFunctions.CONTINENTS)
            .erosion(DensityFunctions.EROSION)
            .depth(zero)
            .ridges(DensityFunctions.RIDGES)
            .preliminarySurfaceLevel(zero)
            .finalDensity(finalDensity)
            .veinToggle(zero)
            .veinRidged(zero)
            .veinGap(zero)
            .build();

        LevelNoiseGeneratorSettings noiseSettings = LevelNoiseGeneratorSettings.builder()
            .noiseSettings(LevelNoiseSettings.OVERWORLD)
            .defaultBlock(Material.STONE)
            .defaultFluid(Material.WATER)
            .noiseRouter(router)
            .surfaceRule(WrappedSurfaceRule.block(Material.GRASS_BLOCK))
            .seaLevel(63)
            .aquifersEnabled(false)
            .oreVeinsEnabled(false)
            .build();

        LevelChunkGenerator generator = LevelChunkGenerator.noise(
            biomeSource,
            Noise.amplified()
        );

        LevelCreator spec = LevelCreator.builder(levelKey)
            .dimension(dimension)
            .generator(generator)
            .build();

        World world = LevelFactory.factory().createWorld(spec);
        System.out.println("Created world: " + world.getName());
    }
}