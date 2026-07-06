package dev.wyck.test;

import dev.wyck.model.biome.Biome;
import dev.wyck.model.level.dimension.Dimension;
import dev.wyck.model.level.LevelCreator;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.level.LevelFactory;
import dev.wyck.test.worldgen.SchematicTreeFeature;
import dev.wyck.wrapper.biome.BiomeSpecialEffects;
import dev.wyck.wrapper.entity.BiomeSpawner;
import dev.wyck.wrapper.entity.MobCategory;
import dev.wyck.wrapper.entity.data.NaturalSpawner;
import dev.wyck.wrapper.environment.attribute.EnvironmentAttributes;
import dev.wyck.wrapper.environment.particle.ParticleOptions;
import dev.wyck.wrapper.environment.particle.ParticleTypes;
import dev.wyck.wrapper.level.BiomeSource;
import dev.wyck.wrapper.level.clock.WorldClock;
import dev.wyck.wrapper.level.dimension.Skybox;
import dev.wyck.wrapper.level.noise.NoiseGeneratorSettings;
import dev.wyck.wrapper.level.noise.NoiseRouter;
import dev.wyck.wrapper.level.noise.Noises;
import dev.wyck.wrapper.level.noise.chunk.ChunkGenerator;
import dev.wyck.wrapper.level.noise.function.DensityFunction;
import dev.wyck.wrapper.level.noise.settings.NoiseSettings;
import dev.wyck.wrapper.worldgen.BiomeGenerationSettings;
import dev.wyck.wrapper.worldgen.GenerationStep;
import dev.wyck.wrapper.worldgen.HeightmapType;
import dev.wyck.wrapper.worldgen.feature.ConfiguredFeature;
import dev.wyck.wrapper.worldgen.feature.FeatureType;
import dev.wyck.wrapper.worldgen.feature.configurations.FeatureConfiguration;
import dev.wyck.wrapper.worldgen.placement.PlacedFeature;
import dev.wyck.wrapper.worldgen.placement.PlacementModifier;
import dev.wyck.wrapper.worldgen.surface.SurfaceCondition;
import dev.wyck.wrapper.worldgen.surface.SurfaceRule;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class TestPlugin extends JavaPlugin implements Listener {

    ResourceKey dimKey = ResourceKey.of("test", "biomesand");
    Dimension dimension = Dimension.builder(dimKey)
        .hasSkyLight(true)
        .height(800)
        .logicalHeight(800)
        .skybox(Skybox.OVERWORLD)
        .defaultClock(WorldClock.OVERWORLD)
        .attribute(EnvironmentAttributes.FOG_COLOR, -4138753)
        .register();

    @Override
    public void onEnable() {
        //BlockData stone = Material.STONE.createBlockData();
        //DimensionType
        ResourceKey levelKey = ResourceKey.of("test", "wobbleworld");

        Biome biome = Biome.builder()
            .resourceKey(ResourceKey.of("test:biome")) // Required key
            // Climate settings
//            .climateSettings(ClimateSettings.builder()
//                .downfall(0.5f)
//                .temperature(0.14f)
//                .temperatureModifier(TemperatureModifier.FROZEN)
//                .build()
//            )
            // Special effects
            .specialEffects(BiomeSpecialEffects.builder()
                    .waterColor("#00FF00")
//                .grassColorOverride("#00FF00")
//                .foliageColorOverride("#00FF00")
//                .dryFoliageColorOverride("#0000FF")
                    .build()
            )
            // Environmental attributes
//            .attribute(EnvironmentAttributes.BLOCK_LIGHT_TINT, "#FFFFFF")
//            .attribute(EnvironmentAttributes.FOG_COLOR, "#FFFFFF")
//            .attribute(EnvironmentAttributes.AMBIENT_PARTICLES, ParticleCatalog.builder()
//                .particle(ParticleTypes.BLOCK, 0.01f, BlockParticle.of(Material.ACACIA_LOG))
//                .build()
//            )
            .attribute(EnvironmentAttributes.DEFAULT_DRIPSTONE_PARTICLE, ParticleOptions.of(ParticleTypes.DRIPPING_HONEY))
            // Mob spawn settings
            .spawner(BiomeSpawner.builder()
                .spawner(MobCategory.MONSTER, NaturalSpawner.of(EntityType.ZOMBIE, 5, 15))
                .build()
            )
            // Worldgen
            .generationSettings(
                BiomeGenerationSettings.builder()
                    .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeature.builder()
                        .feature(new SchematicTreeFeature().register(), SchematicTreeFeature.SchematicTreeConfig.defaults())
                        .modifier(PlacementModifier.rarityFilter(1))
                        .modifier(PlacementModifier.inSquare())
                        .modifier(PlacementModifier.surfaceWaterDepth(0))
                        .modifier(PlacementModifier.heightmap(HeightmapType.OCEAN_FLOOR))
                        .modifier(PlacementModifier.biomeFilter())
                        .build())

                    .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeature.builder()
                        .feature(new SchematicTreeFeature().registerAs(ResourceKey.of("t", "s")), SchematicTreeFeature.SchematicTreeConfig.defaults2())
                        .modifier(PlacementModifier.rarityFilter(1))
                        .modifier(PlacementModifier.inSquare())
                        .modifier(PlacementModifier.surfaceWaterDepth(0))
                        .modifier(PlacementModifier.heightmap(HeightmapType.OCEAN_FLOOR))
                        .modifier(PlacementModifier.biomeFilter())
                        .build())

//                    .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeature.builder()
//                        .feature(ConfiguredFeature.of(FeatureType.TREE, FeatureConfiguration.tree()
//                            .belowTrunkProvider()))
//                        .modifier(PlacementModifier.rarityFilter(1))
//                        .modifier(PlacementModifier.inSquare())
//                        .modifier(PlacementModifier.surfaceWaterDepth(0))
//                        .modifier(PlacementModifier.heightmap(HeightmapType.OCEAN_FLOOR))
//                        .modifier(PlacementModifier.biomeFilter())
//                        .build())
                    .build()
            )
            .register();

//        BiomeSource biomeSource = BiomeSource.multiNoise()
//            .add(ResourceKey.minecraft("snowy_taiga"), ClimatePoint.of(-0.7f, -0.2f, 0f, 0f, 0f, 0f, 0f))
//            .add(ResourceKey.minecraft("taiga"), ClimatePoint.of(-0.3f, 0.4f, 0f, 0f, 0f, 0f, 0f))
//            .add(ResourceKey.minecraft("plains"), ClimatePoint.of(0.1f, -0.4f, 0f, 0f, 0f, 0f, 0f))
//            .add(ResourceKey.minecraft("forest"), ClimatePoint.of(0.2f, 0.3f, 0f, 0f, 0f, 0f, 0f))
//            .add(ResourceKey.minecraft("desert"), ClimatePoint.of(0.8f, -0.6f, 0f, 0f, 0f, 0f, 0f))
//            .add(ResourceKey.minecraft("jungle"), ClimatePoint.of(0.8f, 0.7f, 0f, 0f, 0f, 0f, 0f))
//            .build();
        BiomeSource biomeSource = BiomeSource.fixed(biome);

        DensityFunction wobble = DensityFunction.noise(Noises.SPAGHETTI_3D_1, 0.4, 0.9)
            .mul(DensityFunction.constant(25.0));

        DensityFunction base = DensityFunction.yClampedGradient(0, 256, 1.2, -1.2);
        DensityFunction finalDensity = DensityFunction.add(base, wobble).clamp(-1.0, 1.0);

        NoiseRouter router = NoiseRouter.builder()
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

        NoiseGeneratorSettings noiseSettings = NoiseGeneratorSettings.builder()
            .noiseSettings(NoiseSettings.OVERWORLD)
            .defaultBlock(Material.STONE)
            .defaultFluid(Material.WATER)
            .noiseRouter(router)
            .surfaceRule(surfaceRule)
            .seaLevel(63)
            .aquifersEnabled(false)
            .oreVeinsEnabled(false)
            .build();

        ChunkGenerator generator = ChunkGenerator.of(biomeSource, noiseSettings);

        LevelCreator spec = LevelCreator.builder(levelKey)
            .dimension(dimension)
            .generator(generator)
            .build();

        World world = LevelFactory.factory().createWorld(spec);
        System.out.println("Created world: " + world.getName());

        //getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        Skybox randomSkybox = Skybox.values()[(int) (Math.random() * Skybox.values().length)];
        dimension.toBuilder().skybox(randomSkybox).modify();
        event.getPlayer().sendMessage("Skybox changed to " + randomSkybox);
    }
}