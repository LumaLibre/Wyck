package me.outspending.biomesapi.registry.handlers;

import me.outspending.biomesapi.biome.AbstractBiome;
import me.outspending.biomesapi.registry.BuilderHandler;
import me.outspending.biomesapi.wrapper.worldgen.BiomeGenerationSettings;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

public class BiomeGenerationSettingsHandler implements BuilderHandler<Biome.BiomeBuilder, BiomeGenerationSettings> {
    @Override
    public void handle(BiomeGenerationSettings value, Biome.@NotNull BiomeBuilder key) {
        if (value == null) return;

        net.minecraft.world.level.biome.BiomeGenerationSettings genSettings =
            (net.minecraft.world.level.biome.BiomeGenerationSettings) value.toMinecraft();
        key.generationSettings(genSettings);
    }

    @Override
    public BiomeGenerationSettings build(@NotNull AbstractBiome biome) {
        return biome.getGenerationSettings();
    }
}
