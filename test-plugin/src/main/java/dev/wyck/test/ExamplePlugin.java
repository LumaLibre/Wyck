package dev.wyck.test;

import dev.wyck.environment.attribute.EnvironmentAttributes;
import dev.wyck.keys.ResourceKey;
import dev.wyck.biome.Biome;
import dev.wyck.biome.BiomeSpecialEffects;
import dev.wyck.biome.BiomeGenerationSettings;
import dev.wyck.worldgen.blockpredicates.BlockPredicate;
import dev.wyck.worldgen.Decoration;
import dev.wyck.worldgen.HeightmapType;
import dev.wyck.worldgen.feature.ConfiguredFeature;
import dev.wyck.worldgen.feature.FeatureType;
import dev.wyck.worldgen.feature.configurations.FeatureConfiguration;
import dev.wyck.worldgen.feature.configurations.TreeConfiguration;
import dev.wyck.worldgen.feature.featuresize.FeatureSize;
import dev.wyck.worldgen.feature.foliageplacers.FoliagePlacer;
import dev.wyck.worldgen.feature.treedecorators.TreeDecorator;
import dev.wyck.worldgen.feature.trunkplacers.TrunkPlacer;
import dev.wyck.worldgen.function.DensityFunction;
import dev.wyck.worldgen.function.DensityFunctions;
import dev.wyck.worldgen.function.misc.Marker;
import dev.wyck.worldgen.placement.PlacedFeature;
import dev.wyck.worldgen.placement.PlacementModifier;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryOps;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.BlockFace;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        // Making a tall spruce tree
        TreeConfiguration treeConfig = FeatureConfiguration.tree()
            .foliageProvider(BlockStateProvider.simple(Material.MANGROVE_LEAVES))
            .trunkProvider(BlockStateProvider.simple(Material.CHERRY_WOOD))
            .belowTrunkProvider(
                BlockStateProvider.ruleBased()
                    .rule(
                        BlockPredicate.not(BlockPredicate.matchingBlockTag(Tag.CANNOT_REPLACE_BELOW_TREE_TRUNK)),
                        BlockStateProvider.simple(Material.DIRT)
                    )
                    .build()
            )
            .foliagePlacer(FoliagePlacer.fancy()
                .height(4)
                .offset(IntProvider.constant(0))
                .radius(IntProvider.uniform(6, 7))
                .build()
            )
            .minimumSize(FeatureSize.twoLayers()
                .limit(1)
                .lowerSize(0)
                .minClippedHeight(0)
                .upperSize(0)
                .build()
            )
            .trunkPlacer(TrunkPlacer.cherry()
                .branchCount(IntProvider.constant(2))
                .branchHorizontalLength(IntProvider.constant(7))
                .baseHeight(7)
                .heightRandA(4)
                .heightRandB(6)
                .build()
            )
            .decorator(TreeDecorator.attachedToLeaves()
                .direction(BlockFace.UP)
                .blockProvider(BlockStateProvider.simple(Material.MEDIUM_AMETHYST_BUD))
                .probability(0.2f)
                .build()
            )
            .ignoreVines()
            .build();

        // Using it in a biome
        ResourceKey biomeKey = ResourceKey.of("test:biome");
        Biome.builder()
            .resourceKey(biomeKey)
            .specialEffects(BiomeSpecialEffects.builder()
                .foliageColorOverride("#8b69ca")
                .build())
            .attribute(EnvironmentAttributes.SUNRISE_SUNSET_COLOR, "#ff0000")
            .generationSettings(
                BiomeGenerationSettings.builder()
                    .feature(Decoration.VEGETAL_DECORATION, PlacedFeature.builder()
                        .feature(ConfiguredFeature.of(FeatureType.TREE, treeConfig))
                        .modifier(PlacementModifier.rarityFilter(1))
                        .modifier(PlacementModifier.inSquare())
                        .modifier(PlacementModifier.surfaceWaterDepthFilter(0))
                        .modifier(PlacementModifier.heightmap(HeightmapType.OCEAN_FLOOR))
                        .modifier(PlacementModifier.biomeFilter())
                        .build())
                    .build()
            )
            .register();

        dumpBiomeJson(biomeKey);

        DensityFunction densityFunction = Marker.of(
            ResourceKey.of("test:example_density"),
            Marker.Type.BLEND_DENSITY,
            DensityFunctions.PILLARS
                .add(DensityFunctions.CONTINENTS_LARGE)
                .mul(DensityFunction.constant(10f))
        ).register();
    }

    /**
     * Reads a biome back out of the live registry and prints it as the JSON vanilla's own codec
     * produces — i.e. what the biome would look like as a datapack file. Useful for checking that a
     * builder actually produced the shape you meant.
     */
    private void dumpBiomeJson(ResourceKey key) {
        Registry<net.minecraft.world.level.biome.Biome> registry =
            BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.BIOME);

        // ResourceKey#identifier is generic, so pin the type or getValue's overloads are ambiguous.
        Identifier id = key.identifier();
        net.minecraft.world.level.biome.Biome biome = registry.getValue(id);
        if (biome == null) {
            getLogger().warning("Biome " + key + " is not in the registry, nothing to dump");
            return;
        }

        // Generation settings hold registry references (placed features, carvers), so the codec needs
        // a registry-aware ops to encode them — plain JsonOps would fail on the holders.
        RegistryOps<JsonElement> ops = BootstrapSafeMinecraftRegistries.serialization()
            .createSerializationContext(JsonOps.INSTANCE);

        net.minecraft.world.level.biome.Biome.DIRECT_CODEC.encodeStart(ops, biome)
            .resultOrPartial(error -> getLogger().warning("Could not encode biome " + key + ": " + error))
            .ifPresent(json -> System.out.println(
                "[wyck] " + key + " =\n" + new GsonBuilder().setPrettyPrinting().create().toJson(json)));
    }
}