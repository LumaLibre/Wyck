package me.outspending.biomesapi.registry.handlers;

import me.outspending.biomesapi.biome.AbstractBiome;
import me.outspending.biomesapi.registry.internal.BuilderHandler;
import me.outspending.biomesapi.wrapper.worldgen.BiomeGenerationSettings;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
@ApiStatus.Internal
public class BiomeGenerationSettingsHandler implements BuilderHandler<Biome.BiomeBuilder, BiomeGenerationSettings> {
    @Override
    public void handle(@Nullable BiomeGenerationSettings value, Biome.BiomeBuilder key) {
        if (value == null) return;

        net.minecraft.world.level.biome.BiomeGenerationSettings genSettings =
            (net.minecraft.world.level.biome.BiomeGenerationSettings) value.toMinecraft();
        key.generationSettings(genSettings);
    }

    @Override
    public @Nullable BiomeGenerationSettings build(AbstractBiome biome) {
        return biome.getGenerationSettings();
    }
}
