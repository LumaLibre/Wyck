package me.outspending.biomesapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.level.LevelCreator;
import me.outspending.biomesapi.level.dimension.Dimension;
import me.outspending.biomesapi.wrapper.BiomeSettings;
import me.outspending.biomesapi.wrapper.environment.BedRule;
import me.outspending.biomesapi.wrapper.environment.MoonPhase;
import me.outspending.biomesapi.wrapper.environment.attribute.WrappedEnvironmentAttributes;
import me.outspending.biomesapi.wrapper.environment.particle.WrappedParticleTypes;
import me.outspending.biomesapi.wrapper.level.BiomeSource;
import me.outspending.biomesapi.wrapper.level.noise.LevelNoiseGeneratorSettings;
import me.outspending.biomesapi.wrapper.level.noise.LevelNoiseRouter;
import me.outspending.biomesapi.wrapper.level.noise.Noises;
import me.outspending.biomesapi.wrapper.level.noise.chunk.LevelChunkGenerator;
import me.outspending.biomesapi.wrapper.level.noise.function.DensityFunction;
import me.outspending.biomesapi.wrapper.level.noise.function.DensityFunctions;
import me.outspending.biomesapi.wrapper.level.noise.settings.LevelNoiseSettings;
import me.outspending.biomesapi.wrapper.worldgen.BiomeGenerationSettings;
import me.outspending.biomesapi.wrapper.worldgen.GenerationStep;
import me.outspending.biomesapi.wrapper.worldgen.HeightmapType;
import me.outspending.biomesapi.wrapper.worldgen.carver.CanyonCarverConfiguration;
import me.outspending.biomesapi.wrapper.worldgen.carver.CanyonShapeConfiguration;
import me.outspending.biomesapi.wrapper.worldgen.carver.ConfiguredWorldCarver;
import me.outspending.biomesapi.wrapper.worldgen.climate.ClimatePoint;
import me.outspending.biomesapi.wrapper.worldgen.feature.ConfiguredFeatures;
import me.outspending.biomesapi.wrapper.worldgen.placement.PlacedFeature;
import me.outspending.biomesapi.wrapper.worldgen.placement.PlacedFeatures;
import me.outspending.biomesapi.wrapper.worldgen.placement.PlacementModifier;
import me.outspending.biomesapi.wrapper.worldgen.surface.CaveSurface;
import me.outspending.biomesapi.wrapper.worldgen.surface.SurfaceCondition;
import me.outspending.biomesapi.wrapper.worldgen.surface.SurfaceRule;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.FloatProvider;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.HeightProvider;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.VerticalAnchor;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ExamplePlugin extends JavaPlugin {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void onEnable() {
        BiomeGenerationSettings generation = BiomeGenerationSettings.builder()
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.TREES_PLAINS)
            //.addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.TREES_MANGROVE)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.FALLEN_OAK_TREE)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.FLOWER_DEFAULT)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.FLOWER_PLAINS)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.WILDFLOWERS_MEADOW)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.PATCH_GRASS_PLAIN)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.PATCH_TALL_GRASS)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.PATCH_TALL_GRASS_2)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.PATCH_SUNFLOWER)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.BROWN_MUSHROOM_NORMAL)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.RED_MUSHROOM_NORMAL)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.PATCH_PUMPKIN)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.PATCH_FIREFLY_BUSH_NEAR_WATER)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.OAK_BEES_002)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.FANCY_OAK_BEES_002)
            //.addFeature(GenerationStep.RAW_GENERATION, PlacedFeatures.ORE_EMERALD)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeature.of(
                ConfiguredFeatures.FLOWER_DEFAULT,
                PlacementModifier.count(18),
                PlacementModifier.inSquare(),
                PlacementModifier.heightmap(HeightmapType.MOTION_BLOCKING),
                PlacementModifier.biomeFilter()))
            //.addFeature(GenerationStep.FLUID_SPRINGS, PlacedFeatures.SPRING_WATER)
            .addFeature(GenerationStep.TOP_LAYER_MODIFICATION, PlacedFeatures.FREEZE_TOP_LAYER)
            .addCarver(ConfiguredWorldCarver.canyon(
                CanyonCarverConfiguration.builder()
                    .probability(20.0f)
                    .lavaLevel(VerticalAnchor.absolute(10))
                    .y(HeightProvider.uniform(VerticalAnchor.absolute(10), VerticalAnchor.absolute(70)))
                    .yScale(FloatProvider.uniform(0.75f, 1.25f))
                    .verticalRotation(FloatProvider.uniform(-15.0f, 15.0f))
                    .replaceable(List.of(Material.GRASS_BLOCK, Material.STONE))
                    .shape(CanyonShapeConfiguration.builder()
                        .distanceFactor(FloatProvider.uniform(0.75f, 1.25f))
                        .thickness(FloatProvider.uniform(1.0f, 3.0f))
                        .widthSmoothness(3)
                        .horizontalRadiusFactor(FloatProvider.uniform(0.75f, 1.25f))
                        .verticalRadiusDefaultFactor(1.0f)
                        .verticalRadiusCenterFactor(1.0f)
                        .build()
                    )
                    .build()
            ))
            .build();

        CustomBiome customBiome = CustomBiome.builder()
            .resourceKey(ResourceKey.of("test", "custombiome"))
            .settings(BiomeSettings.defaultSettings())
            .fogColor("#DB00FD")
            .skyColor("#2F46FF")
            .waterColor("#00FFD0")
            .grassColor("#D1D13A")
            .foliageColor("#FF6A00")
            .ambientParticle(WrappedParticleTypes.ASH, 0.1f)
            .setAttribute(WrappedEnvironmentAttributes.MOON_PHASE, MoonPhase.FIRST_QUARTER)
            .setGenerationSettings(generation)
            .build();

        DataResult<JsonElement> result = CustomBiome.CODEC.encodeStart(JsonOps.INSTANCE, customBiome);
        if (result.error().isPresent()) {
            throw new IllegalStateException("Biome codec encode failed -> " + result.error().get().message());
        }
        JsonElement json = result.result().orElseThrow();

        dumpJson(json, "custombiome.biome.json");
        System.out.println(json);


        Dimension dimension = Dimension.builder()
            .resourceKey(ResourceKey.of("test", "customdimension"))
            .hasSkyLight(true)
            .height(256)
            .logicalHeight(256)
            .attribute(WrappedEnvironmentAttributes.MOON_PHASE, MoonPhase.THIRD_QUARTER)
            .attribute(WrappedEnvironmentAttributes.SKY_COLOR, 0xFF00FF)
            .attribute(WrappedEnvironmentAttributes.BED_RULE, BedRule.builder().setExplodes(true).build())
            .build();

        DataResult<JsonElement> dimensionResult = Dimension.CODEC.encodeStart(JsonOps.INSTANCE, dimension);

        if (dimensionResult.error().isPresent()) {
            throw new IllegalStateException("Dimension codec encode failed -> " + dimensionResult.error().get().message());
        }
        JsonElement dimensionJson = dimensionResult.result().orElseThrow();
        dumpJson(dimensionJson, "customdimension.dimension.json");
        System.out.println(dimensionJson);

        BiomeSource biomeSource = BiomeSource.multiNoise()
            .add(ResourceKey.minecraft("snowy_taiga"), ClimatePoint.of(-0.7f, -0.2f, 0f, 0f, 0f, 0f, 0f))
            .add(ResourceKey.minecraft("taiga"), ClimatePoint.of(-0.3f, 0.4f, 0f, 0f, 0f, 0f, 0f))
            .add(ResourceKey.minecraft("plains"), ClimatePoint.of(0.1f, -0.4f, 0f, 0f, 0f, 0f, 0f))
            .add(ResourceKey.minecraft("forest"), ClimatePoint.of(0.2f, 0.3f, 0f, 0f, 0f, 0f, 0f))
            .add(ResourceKey.minecraft("desert"), ClimatePoint.of(0.8f, -0.6f, 0f, 0f, 0f, 0f, 0f))
            .add(ResourceKey.minecraft("jungle"), ClimatePoint.of(0.8f, 0.7f, 0f, 0f, 0f, 0f, 0f))
            .build();

        DensityFunction wobble = DensityFunction.noise(Noises.SPAGHETTI_3D_1, 0.4, 0.9)
            .mul(DensityFunction.constant(1.0))
            .mul(DensityFunction.noise(Noises.SPAGHETTI_3D_2, 0.4, 0.9))
            .mul(DensityFunctions.EROSION_LARGE);

        DensityFunction base = DensityFunction.yClampedGradient(0, 256, 1.2, -1.2);
        DensityFunction finalDensity = DensityFunction.add(base, wobble).clamp(-1.0, 1.0);

        LevelNoiseRouter router = LevelNoiseRouter.builder()
            .temperature(DensityFunction.noise(ResourceKey.minecraft("temperature"), 0.25, 0.0))
            .vegetation(DensityFunction.noise(ResourceKey.minecraft("vegetation"), 0.25, 0.0))
            .finalDensity(finalDensity)
            .veinToggle(DensityFunction.constant(-1.0))
            .build();

        SurfaceCondition onFloor = SurfaceCondition.stoneDepth(0, false, CaveSurface.FLOOR);
        SurfaceCondition underFloor = SurfaceCondition.stoneDepth(0, true, CaveSurface.FLOOR);

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
            .resourceKey(ResourceKey.of("test", "customnoisesettings"))
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

        LevelCreator spec = LevelCreator.builder(ResourceKey.of("test", "customworld"))
            .dimension(dimension)
            .generator(generator)
            .build();

        DataResult<JsonElement> worldResult = LevelCreator.CODEC.encodeStart(JsonOps.INSTANCE, spec);

        if (worldResult.error().isPresent()) {
            throw new IllegalStateException("World codec encode failed -> " + worldResult.error().get().message());
        }

        JsonElement worldJson = worldResult.result().orElseThrow();
        dumpJson(worldJson, "customworld.world.json");
        System.out.println(worldJson);
    }

    private void dumpJson(JsonElement json, String fileName) {
        String pretty = GSON.toJson(json);
        getLogger().info(fileName + ":\n" + pretty);
        try {
            Path out = getDataFolder().toPath().resolve(fileName);
            Files.createDirectories(out.getParent());
            Files.writeString(out, pretty);
            getLogger().info("Wrote " + out.toAbsolutePath());
        } catch (IOException e) {
            getLogger().severe("Failed to write " + fileName + ": " + e.getMessage());
        }
    }
}
