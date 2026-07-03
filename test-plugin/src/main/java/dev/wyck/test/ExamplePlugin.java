package dev.wyck.test;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import dev.wyck.keys.ResourceKey;
import dev.wyck.model.biome.Biome;
import dev.wyck.model.level.dimension.Dimension;
import dev.wyck.registry.BiomeRegistry;
import dev.wyck.registry.BiomeRegistryImpl;
import dev.wyck.test.worldgen.PillarFeature;
import dev.wyck.wrapper.biome.BiomeSpecialEffects;
import dev.wyck.wrapper.biome.ClimateSettings;
import dev.wyck.wrapper.biome.TemperatureModifier;
import dev.wyck.wrapper.entity.BiomeSpawner;
import dev.wyck.wrapper.entity.MobCategory;
import dev.wyck.wrapper.entity.data.NaturalSpawner;
import dev.wyck.wrapper.environment.attribute.EnvironmentAttributes;
import dev.wyck.wrapper.environment.particle.ParticleCatalog;
import dev.wyck.wrapper.environment.particle.ParticleOptions;
import dev.wyck.wrapper.environment.particle.ParticleTypes;
import dev.wyck.wrapper.environment.particle.options.BlockParticle;
import dev.wyck.wrapper.worldgen.BiomeGenerationSettings;
import dev.wyck.wrapper.worldgen.GenerationStep;
import dev.wyck.wrapper.worldgen.carver.CanyonCarverConfiguration;
import dev.wyck.wrapper.worldgen.carver.CanyonShapeConfiguration;
import dev.wyck.wrapper.worldgen.carver.ConfiguredWorldCarver;
import dev.wyck.wrapper.worldgen.feature.ConfiguredFeature;
import dev.wyck.wrapper.worldgen.feature.ConfiguredFeatures;
import dev.wyck.wrapper.worldgen.feature.FeatureType;
import dev.wyck.wrapper.worldgen.feature.config.FeatureConfiguration;
import dev.wyck.wrapper.worldgen.placement.PlacedFeature;
import dev.wyck.wrapper.worldgen.placement.PlacedFeatures;
import dev.wyck.wrapper.worldgen.placement.PlacementModifier;
import dev.wyck.wrapper.worldgen.valueproviders.FloatProvider;
import dev.wyck.wrapper.worldgen.valueproviders.HeightProvider;
import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import dev.wyck.wrapper.worldgen.valueproviders.VerticalAnchor;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryOps;
import org.bukkit.Material;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ExamplePlugin extends JavaPlugin {
    @Override
    public void onEnable() {


        Biome customBiome = Biome.builder()
            .resourceKey(ResourceKey.of("test:biome"))
            .climateSettings(ClimateSettings.builder()
                .downfall(0.5f)
                .temperature(0.14f)
                .temperatureModifier(TemperatureModifier.FROZEN)
                .build()
            )
            .specialEffects(BiomeSpecialEffects.builder()
                .waterColor("#00FF00")
                .grassColorOverride("#00FF00")
                .foliageColorOverride("#00FF00")
                .dryFoliageColorOverride("#0000FF")
                .build()
            )
            .attribute(EnvironmentAttributes.BLOCK_LIGHT_TINT, "#FFFFFF")
            .attribute(EnvironmentAttributes.FOG_COLOR, "#FFFFFF")
            .attribute(EnvironmentAttributes.AMBIENT_PARTICLES, ParticleCatalog.builder()
                .particle(ParticleTypes.BLOCK, 0.01f, BlockParticle.of(Material.ACACIA_LOG))
                .build()
            )
            .attribute(EnvironmentAttributes.DEFAULT_DRIPSTONE_PARTICLE, ParticleOptions.of(ParticleTypes.DRIPPING_HONEY))
            .spawner(BiomeSpawner.builder()
                .spawner(MobCategory.MONSTER, NaturalSpawner.of(EntityType.ZOMBIE, 5, 15))
                .build()
            )
            .generationSettings(
                BiomeGenerationSettings.builder()
                    .addCarver(ConfiguredWorldCarver.canyon(CanyonCarverConfiguration.builder()
                        .probability(0.9f)
                        .y(HeightProvider.biasedToBottom(VerticalAnchor.bottom(), VerticalAnchor.bottom(), 20))
                        .yScale(FloatProvider.constant(1.0f))
                        .lavaLevel(VerticalAnchor.absolute(63))
                        .replaceable(List.of(Material.STONE, Material.DIRT, Material.GRASS_BLOCK))
                        .verticalRotation(FloatProvider.constant(0.0f))
                        .shape(CanyonShapeConfiguration.builder()
                            .distanceFactor(FloatProvider.constant(5f))
                            .thickness(FloatProvider.constant(0.1f))
                            .widthSmoothness(30)
                            .horizontalRadiusFactor(FloatProvider.constant(1.0f))
                            .verticalRadiusCenterFactor(0.3f)
                            .verticalRadiusDefaultFactor(0.3f)
                            .build())
                        .build())
                    )
                    .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeature.builder()
                        .feature(new PillarFeature().register()) // Declared in another class from my plugin
                        .modifier(PlacementModifier.count(1))
                        .build())
                    .addFeature(GenerationStep.TOP_LAYER_MODIFICATION, PlacedFeature.builder()
                        .feature(ConfiguredFeatures.PILE_SNOW) // Or use one that's already registered
                        .modifier(PlacementModifier.count(1))
                        .build())
                    .addFeature(GenerationStep.UNDERGROUND_ORES, PlacedFeatures.ORE_IRON_SMALL)
                    .addFeature(GenerationStep.RAW_GENERATION, PlacedFeature.builder()
                        .feature(ConfiguredFeature.vanilla(FeatureType.VEGETATION_PATCH, FeatureConfiguration.count(IntProvider.constant(3))))
                        .modifier(PlacementModifier.count(1))
                        .build())
                    .build()
            )
            .register();

        Biome reference = Biome.reference(ResourceKey.minecraft("desert"));

        BiomeRegistryImpl registry = (BiomeRegistryImpl) BiomeRegistry.registry();
        net.minecraft.world.level.biome.Biome nmsBiome = registry.buildDelegate(customBiome);

        RegistryAccess registryAccess = ((CraftServer) getServer()).getServer().registryAccess();
        RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, registryAccess);

        JsonElement json = net.minecraft.world.level.biome.Biome.DIRECT_CODEC
            .encodeStart(ops, nmsBiome)
            .getOrThrow();
        String result = json.toString();
        // write to /Users/jonah/idea/BiomesAPI/test-plugin/biome.json
        try {
            java.nio.file.Files.writeString(java.nio.file.Paths.get("/Users/jonah/idea/BiomesAPI/test-plugin/biome.json"), result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
