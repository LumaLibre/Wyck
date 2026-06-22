package me.outspending.biomesapi;

import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.level.StemPersistence;
import me.outspending.biomesapi.level.dimension.Dimension;
import me.outspending.biomesapi.level.LevelCreator;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.registry.level.LevelFactory;
import me.outspending.biomesapi.wrapper.BiomeSettings;
import me.outspending.biomesapi.wrapper.level.LevelChunkGenerator;
import me.outspending.biomesapi.wrapper.level.dimension.Skybox;
import me.outspending.biomesapi.wrapper.environment.BedRule;
import me.outspending.biomesapi.wrapper.environment.attribute.WrappedEnvironmentAttributeMap;
import me.outspending.biomesapi.wrapper.environment.attribute.WrappedEnvironmentAttributes;
import me.outspending.biomesapi.wrapper.level.BiomeSource;
import me.outspending.biomesapi.wrapper.worldgen.climate.BiomeClimatePoint;
import net.kyori.adventure.text.Component;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        var myBiomeKey = ResourceKey.of("test", "mybiome");
        CustomBiome mybiome = CustomBiome.builder()
            .resourceKey(myBiomeKey)
            .settings(BiomeSettings.defaultSettings())
            .fogColor("#DB00FD")
            .skyColor("#2F46FF")
            .waterColor("#00FFD0")
            .grassColor("#D1D13A")
            .foliageColor("#FF6A00")
            .build();


        ResourceKey dimKey = ResourceKey.of("test", "awesome");
        ResourceKey levelKey = ResourceKey.of("test", "awesome");

        BedRule bedrule = BedRule.builder()
            .setCanSleep(BedRule.Rule.NEVER)
            .setErrorMessage(Component.text("no sleeping"))
            .setCanSetSpawn(BedRule.Rule.NEVER)
            .build();


        Dimension dimension = Dimension.builder(dimKey)
            .hasSkyLight(true)
            .skybox(Skybox.OVERWORLD)
            .attribute(WrappedEnvironmentAttributes.FAST_LAVA, true)
            .attribute(WrappedEnvironmentAttributes.BED_RULE, bedrule)
            .attribute(WrappedEnvironmentAttributes.FOG_COLOR, "#FFFFFF")
            .build();

        BiomeSource biomeSource = BiomeSource.multiNoise()
            .add(ResourceKey.minecraft("stony_shore"), BiomeClimatePoint.builder().build())
            .build();


        LevelChunkGenerator generator = LevelChunkGenerator.noise(
            biomeSource,
            LevelChunkGenerator.overworldNoise()
        );

        LevelCreator spec = LevelCreator.builder(levelKey)
            .dimension(dimension)
            .generator(generator)
            .build();

        World world = LevelFactory.factory().createWorld(spec);
        System.out.println("Created world: " + world.getName());
    }
}
