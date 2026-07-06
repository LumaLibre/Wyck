package dev.wyck.test;

import dev.wyck.model.level.dimension.Dimension;
import dev.wyck.registry.bootstrap.BootstrapDimensionRegistry;
import dev.wyck.wrapper.environment.BedRule;
import dev.wyck.wrapper.environment.attribute.EnvironmentAttributes;
import dev.wyck.wrapper.environment.particle.ParticleTypes;
import dev.wyck.wrapper.environment.particle.options.DustParticle;
import dev.wyck.wrapper.level.clock.WorldClock;
import dev.wyck.wrapper.level.dimension.CardinalLightType;
import dev.wyck.wrapper.level.dimension.Infiniburn;
import dev.wyck.wrapper.level.dimension.Skybox;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import dev.wyck.model.biome.CustomBiome;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.bootstrap.BootstrapBiomeRegistry;
import dev.wyck.registry.bootstrap.Composer;
import dev.wyck.wrapper.worldgen.BiomeGenerationSettings;
import dev.wyck.wrapper.worldgen.GenerationStep;
import dev.wyck.wrapper.worldgen.placement.PlacedFeatures;
import io.papermc.paper.world.MoonPhase;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Tag;
import org.bukkit.block.data.BlockData;

public class ExampleBootstrapper implements PluginBootstrap {
    @Override
    public void bootstrap(BootstrapContext context) {

        // This must use the 'DATAPACK' composer!
        BootstrapBiomeRegistry registry = BootstrapBiomeRegistry.compose(context, Composer.INJECTOR);
        BiomeGenerationSettings generation = BiomeGenerationSettings.builder()
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.TREES_PLAINS)
            .addFeature(GenerationStep.VEGETAL_DECORATION, PlacedFeatures.FALLEN_OAK_TREE)
            .build();

        ResourceKey myBiomeKey = ResourceKey.of("example", "my_biome2");
        registry.queue(
            CustomBiome.builder(myBiomeKey)
                .fogColor("#DB00FD")
                .skyColor("#2F46FF")
                .waterColor("#00FFD0")
                .grassColor("#D1D13A")
                .foliageColor("#FF6A00")
                .generationSettings(generation)
                .particle(ParticleTypes.DUST, 0.1f, DustParticle.of("#FF0000"))
                .particle(ParticleTypes.ASH, 0.01f)
                .build()
        );

        BootstrapDimensionRegistry dimRegistry = BootstrapDimensionRegistry.compose(context, Composer.INJECTOR);

        dimRegistry.queue(
            Dimension.builder()
                .resourceKey(ResourceKey.of("test:dimension"))
                .ambientLight(0.1f) // 10% ambient light
                .hasSkyLight(true)
                .defaultClock(WorldClock.OVERWORLD)
                .hasCeiling(false)
                .hasFixedTime(false)
                .hasEnderDragonFight(false)
                .skybox(Skybox.END)
                .infiniburn(Infiniburn.of(ResourceKey.minecraft("sand"))) // Make sand burn infinitely
                .minY(-64)
                .height(800)
                .logicalHeight(800)
                .cardinalLightType(CardinalLightType.DEFAULT)
                .attribute(EnvironmentAttributes.FOG_COLOR, "#FFAA00")
                .attribute(EnvironmentAttributes.SKY_COLOR, "#0000FF")
                .attribute(EnvironmentAttributes.BED_RULE, BedRule.builder()
                    .setErrorMessage(Component.text("No sleeping!").color(NamedTextColor.RED))
                    .setCanSleep(BedRule.Rule.NEVER)
                    .setCanSetSpawn(BedRule.Rule.WHEN_DARK)
                    .build()
                )
                .attribute(EnvironmentAttributes.STAR_BRIGHTNESS, 0.9f) // 90% star brightness
                .build()
        );
    }
}
