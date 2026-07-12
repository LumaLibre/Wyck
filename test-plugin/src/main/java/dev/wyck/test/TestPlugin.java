package dev.wyck.test;

import dev.wyck.model.biome.Biome;
import dev.wyck.model.level.dimension.Dimension;
import dev.wyck.model.level.LevelCreator;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.level.LevelFactory;
import dev.wyck.test.carver.StarCarver;
import dev.wyck.test.carver.StarConfig;
import dev.wyck.wrapper.biome.BiomeSpecialEffects;
import dev.wyck.wrapper.environment.attribute.EnvironmentAttributes;
import dev.wyck.wrapper.level.BiomeSource;
import dev.wyck.wrapper.level.dimension.WorldClock;
import dev.wyck.wrapper.level.dimension.Skybox;
import dev.wyck.wrapper.worldgen.noise.Noise;
import dev.wyck.wrapper.worldgen.noise.types.NoiseGeneratorSettings;
import dev.wyck.wrapper.worldgen.noise.NoiseRouter;
import dev.wyck.wrapper.worldgen.synth.Noises;
import dev.wyck.wrapper.worldgen.chunk.NoiseBasedChunkGenerator;
import dev.wyck.wrapper.worldgen.function.DensityFunction;
import dev.wyck.wrapper.worldgen.noise.NoiseSettings;
import dev.wyck.wrapper.biome.BiomeGenerationSettings;
import dev.wyck.wrapper.worldgen.blockpredicates.BlockPredicate;
import dev.wyck.wrapper.worldgen.GenerationStep;
import dev.wyck.wrapper.worldgen.HeightmapType;
import dev.wyck.wrapper.worldgen.carver.ConfiguredWorldCarver;
import dev.wyck.wrapper.worldgen.feature.ConfiguredFeature;
import dev.wyck.wrapper.worldgen.feature.FeatureType;
import dev.wyck.wrapper.worldgen.feature.configurations.FeatureConfiguration;
import dev.wyck.wrapper.worldgen.feature.configurations.TreeConfiguration;
import dev.wyck.wrapper.worldgen.feature.featuresize.FeatureSize;
import dev.wyck.wrapper.worldgen.feature.foliageplacers.FoliagePlacer;
import dev.wyck.wrapper.worldgen.feature.treedecorators.TreeDecorator;
import dev.wyck.wrapper.worldgen.feature.trunkplacers.TrunkPlacer;
import dev.wyck.wrapper.worldgen.placement.PlacedFeature;
import dev.wyck.wrapper.worldgen.placement.PlacementModifier;
import dev.wyck.wrapper.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.wrapper.worldgen.surface.SurfaceCondition;
import dev.wyck.wrapper.worldgen.surface.SurfaceRule;
import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
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
        ResourceKey levelKey = ResourceKey.of("test", "wobbleworld5");

        TreeConfiguration treeConfig = FeatureConfiguration.tree()
            .foliageProvider(BlockStateProvider.simple(Material.MANGROVE_LEAVES))
            .trunkProvider(BlockStateProvider.simple(Material.CHERRY_WOOD))
            .belowTrunkProvider(
                BlockStateProvider.ruleBased()
                    .rule(
                        BlockPredicate.matchingBlockTag(Tag.CANNOT_REPLACE_BELOW_TREE_TRUNK).not(),
                        BlockStateProvider.simple(Material.DIRT)
                    )
                    .build()
            )
            .foliagePlacer(FoliagePlacer.pine()
                .height(IntProvider.constant(3))
                .offset(IntProvider.constant(1))
                .radius(IntProvider.constant(4))
                .build()
            )
            .minimumSize(FeatureSize.twoLayers()
                .limit(1)
                .lowerSize(0)
                .minClippedHeight(0)
                .upperSize(0)
                .build()
            )
            .trunkPlacer(TrunkPlacer.fancy()
                .baseHeight(7)
                .heightRandA(4)
                .heightRandB(6)
                .build()
            )
            .decorator(TreeDecorator.attachedToLeaves()
                .direction(BlockFace.UP)
                .blockProvider(BlockStateProvider.simple(Material.MEDIUM_AMETHYST_BUD))
                .probability(0.1f)
                .build()
            )
            .ignoreVines()
            .build();

        Biome biome = Biome.builder()
            .resourceKey(ResourceKey.of("test:biome"))
            .specialEffects(BiomeSpecialEffects.builder()
                .foliageColorOverride("#89395c")
                .build())
            .generationSettings(
                BiomeGenerationSettings.builder()
                    .feature(GenerationStep.VEGETAL_DECORATION, PlacedFeature.builder()
                        .feature(ConfiguredFeature.of(FeatureType.TREE, treeConfig))
                        .modifier(PlacementModifier.rarityFilter(1))
                        .modifier(PlacementModifier.inSquare())
                        .modifier(PlacementModifier.surfaceWaterDepthFilter(0))
                        .modifier(PlacementModifier.heightmap(HeightmapType.OCEAN_FLOOR))
                        .modifier(PlacementModifier.heightmap(HeightmapType.MOTION_BLOCKING))
                        .modifier(PlacementModifier.biomeFilter())
                        .build())
                    .carver(ConfiguredWorldCarver.custom(new StarCarver().register(), StarConfig.defaults()))
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
        DensityFunction finalDensity = base.add(wobble).clamp(-1.0, 1.0);

        NoiseRouter router = NoiseRouter.builder()
            .barrier(DensityFunction.constant(0.0))
            .fluidLevelFloodedness(DensityFunction.constant(0.0))
            .fluidLevelSpread(DensityFunction.constant(0.0))
            .lava(DensityFunction.constant(0.0))
            .temperature(DensityFunction.noise(Noises.TEMPERATURE, 0.25, 0.0))
            .vegetation(DensityFunction.noise(Noises.VEGETATION, 0.25, 0.0))
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

        NoiseBasedChunkGenerator generator = NoiseBasedChunkGenerator.of(biomeSource, Noise.overworld());

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