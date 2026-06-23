package me.outspending.biomesapi;

import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.level.dimension.Dimension;
import me.outspending.biomesapi.level.LevelCreator;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.registry.level.LevelFactory;
import me.outspending.biomesapi.registry.level.dimension.DimensionRegistry;
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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class TestPlugin extends JavaPlugin implements Listener {

    ResourceKey dimKey = ResourceKey.of("test", "awesome");
    Dimension dimension = Dimension.builder(dimKey)
        .hasSkyLight(true)
        .skybox(Skybox.NONE)
        .attribute(WrappedEnvironmentAttributes.FAST_LAVA, true)
        //.attribute(WrappedEnvironmentAttributes.BED_RULE, bedrule)
        .attribute(WrappedEnvironmentAttributes.FOG_COLOR, "#FFFFFF")
        .attribute(WrappedEnvironmentAttributes.SKY_COLOR, "#FFFFFF")
        .build();

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


        ResourceKey levelKey = ResourceKey.of("test", "awesome");

        BedRule bedrule = BedRule.builder()
            .setExplodes(true)
            .setCanSleep(BedRule.Rule.NEVER)
            .setErrorMessage(Component.text("no sleeping"))
            .setCanSetSpawn(BedRule.Rule.NEVER)
            .build();



        BiomeSource biomeSource = BiomeSource.multiNoise()
            .add(ResourceKey.minecraft("cherry_grove"), BiomeClimatePoint.builder().build())
            .build();

//        DensityFunction zero = DensityFunction.zero();
//
//        DensityFunction finalDensity = DensityFunction.add(
//                DensityFunction.yClampedGradient(0, 128, 1.0, -1.0),
//                DensityFunction.constant(0.05)
//            )
//            .clamp(-1.0, 1.0);

        DensityFunction temperature = DensityFunctions.ZERO;
        DensityFunction vegetation = DensityFunctions.ZERO;
        DensityFunction continents = DensityFunctions.CONTINENTS_LARGE;
        DensityFunction erosion = DensityFunctions.EROSION_LARGE;
        DensityFunction depth = DensityFunctions.DEPTH_AMPLIFIED;
        DensityFunction ridges = DensityFunctions.RIDGES_FOLDED;

        // Actual terrain shape
        DensityFunction finalDensity = DensityFunctions.SLOPED_CHEESE;

        LevelNoiseRouter router = LevelNoiseRouter.builder()
            // Aquifers and fluids
            .barrier(DensityFunction.constant(0.0))
            .fluidLevelFloodedness(DensityFunction.constant(0.0))
            .fluidLevelSpread(DensityFunction.constant(0.0))
            .lava(DensityFunction.constant(0.0))

            // Climate parameters
            .temperature(temperature)
            .vegetation(vegetation)
            .continents(continents)
            .erosion(erosion)
            .depth(depth)
            .ridges(ridges)

            // Terrain shape
            .preliminarySurfaceLevel(DensityFunction.constant(0.0))
            .finalDensity(finalDensity)

            // Ore veins (disabled)
            .veinToggle(DensityFunction.constant(-1.0))
            .veinRidged(DensityFunction.constant(0.0))
            .veinGap(DensityFunction.constant(0.0))
            // All done!
            .build();

        LevelNoiseGeneratorSettings noiseSettings = LevelNoiseGeneratorSettings.builder()
            .noiseSettings(LevelNoiseSettings.CAVES)
            .defaultBlock(Material.STONE)
            .defaultFluid(Material.WATER)
            .noiseRouter(router)
            .surfaceRule(
                WrappedSurfaceRule.block(Material.MAGMA_BLOCK)
            )
            .seaLevel(63)
            .aquifersEnabled(false)
            .oreVeinsEnabled(false)
            .build();

        LevelChunkGenerator generator = LevelChunkGenerator.noise(
            biomeSource,
            noiseSettings
        );

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