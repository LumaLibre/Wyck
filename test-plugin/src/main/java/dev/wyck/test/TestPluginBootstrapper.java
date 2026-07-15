package dev.wyck.test;

import dev.wyck.util.BukkitBootstrapUtil;
import dev.wyck.worldgen.Decoration;
import dev.wyck.worldgen.valueproviders.IntProvider;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import dev.wyck.keys.ResourceKey;
import dev.wyck.test.worldgen.PillarFeature;
import dev.wyck.biome.BiomeGenerationSettings;
import dev.wyck.worldgen.HeightmapType;
import dev.wyck.worldgen.feature.ConfiguredFeature;
import dev.wyck.worldgen.placement.PlacedFeature;
import dev.wyck.worldgen.placement.PlacedFeatures;
import dev.wyck.worldgen.placement.PlacementModifier;
import org.bukkit.Axis;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Orientable;

public class TestPluginBootstrapper implements PluginBootstrap {

    public static ResourceKey PILLAR_KEY = ResourceKey.of("example", "pillar");

    public static BiomeGenerationSettings newGen;

    @Override
    public void bootstrap(BootstrapContext context) {
        BlockData blockData = BukkitBootstrapUtil.util().createBlockData(Material.OAK_LOG);
        Orientable orientable = (Orientable) blockData;
        orientable.setAxis(Axis.X);
        System.out.println(orientable);

        PillarFeature feat = new PillarFeature().registerAs(PILLAR_KEY);
        ConfiguredFeature tallFeature  = ConfiguredFeature.custom(feat, new PillarFeature.PillarConfig(Material.OBSIDIAN, Material.GLOWSTONE, 10, 15));
        ConfiguredFeature shortFeature = ConfiguredFeature.custom(feat, new PillarFeature.PillarConfig(Material.BLACKSTONE, Material.SHROOMLIGHT, 3, 5));


        PlacedFeature tallPlaced = PlacedFeature.builder()
            .feature(tallFeature)
            .modifier(PlacementModifier.count(IntProvider.constant(2)))
            .modifier(PlacementModifier.inSquare())
            .modifier(PlacementModifier.heightmap(HeightmapType.WORLD_SURFACE))
            .modifier(PlacementModifier.biomeFilter())
            .build();

        PlacedFeature shortPlaced = PlacedFeature.builder()
            .feature(shortFeature)
            .modifier(PlacementModifier.count(IntProvider.constant(4)))
            .modifier(PlacementModifier.inSquare())
            .modifier(PlacementModifier.heightmap(HeightmapType.WORLD_SURFACE))
            .modifier(PlacementModifier.biomeFilter())
            .build();


        newGen = BiomeGenerationSettings.builder()
            .feature(Decoration.VEGETAL_DECORATION, PlacedFeatures.TREES_PLAINS)
            .feature(Decoration.VEGETAL_DECORATION, PlacedFeatures.FALLEN_OAK_TREE)
            .feature(Decoration.VEGETAL_DECORATION, tallPlaced)
            .feature(Decoration.VEGETAL_DECORATION, shortPlaced)
            .build();
    }
}