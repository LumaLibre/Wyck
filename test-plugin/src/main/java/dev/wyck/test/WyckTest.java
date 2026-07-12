package dev.wyck.test;

import dev.wyck.keys.ResourceKey;
import dev.wyck.model.level.LevelCreator;
import dev.wyck.model.level.dimension.Dimension;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.registry.level.LevelFactory;
import dev.wyck.wrapper.environment.attribute.EnvironmentAttributes;
import dev.wyck.wrapper.environment.sounds.AmbientAdditionsSettings;
import dev.wyck.wrapper.environment.sounds.AmbientMoodSettings;
import dev.wyck.wrapper.environment.sounds.AmbientSounds;
import dev.wyck.wrapper.environment.sounds.BackgroundMusic;
import dev.wyck.wrapper.environment.sounds.Music;
import dev.wyck.wrapper.environment.sounds.SoundEvents;
import dev.wyck.wrapper.worldgen.biome.BiomeSource;
import dev.wyck.wrapper.worldgen.noise.Noise;
import dev.wyck.wrapper.worldgen.chunk.NoiseBasedChunkGenerator;
import net.minecraft.core.registries.Registries;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class WyckTest extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        //OverworldBiomeBuilder

        var registry = BootstrapSafeMinecraftRegistries.mappedRegistry(
            Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST);

        registry.entrySet().forEach(entry ->
            getLogger().info("Biome source preset: " + entry.getKey().identifier()));


        BiomeSource src = BiomeSource.overworld();
        getLogger().info("preset src = " + src);
        NoiseBasedChunkGenerator generator = NoiseBasedChunkGenerator.of(src, Noise.overworld());

        BackgroundMusic backgroundMusic = BackgroundMusic.builder()
            .defaultMusic(Music.of(SoundEvents.BARREL_OPEN, 0, 1, true))
            .creativeMusic(Music.of(SoundEvents.COD_FLOP, 0, 1, true))
            .underwaterMusic(Music.of(SoundEvents.CREEPER_HURT, 0, 1, true))
            .build();

        AmbientSounds ambientSounds = AmbientSounds.builder()
            .loop(SoundEvents.CREEPER_DEATH)
            .mood(
                AmbientMoodSettings.builder()
                .soundEvent(SoundEvents.CROP_BREAK)
                .tickDelay(1)
                .blockSearchExtent(10)
                .soundPositionOffset(0.0f)
                .build()
            )
            .addition(AmbientAdditionsSettings.of(SoundEvents.CREEPER_DEATH, 100.0))
            .build();

        ResourceKey dimKey = ResourceKey.of("test", "example");
        Dimension dimension = Dimension.builder(dimKey)
            .attribute(EnvironmentAttributes.BACKGROUND_MUSIC, backgroundMusic)
            .attribute(EnvironmentAttributes.AMBIENT_SOUNDS, ambientSounds)
            .build();


        ResourceKey levelKey = ResourceKey.of("test:exampleworld");
        LevelCreator spec = LevelCreator.builder(levelKey)
            .dimension(dimension)
            .generator(generator)
            .build();


        World world = LevelFactory.factory().createWorld(spec);
    }
}