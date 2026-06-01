package me.outspending.biomesapi.registry.handlers;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.registry.BuilderHandler;
import me.outspending.biomesapi.wrapper.entity.BiomeSpawner;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.jetbrains.annotations.NotNull;

@AsOf("2.3.0")
public class MobSpawnSettingsHandler implements BuilderHandler<Biome.BiomeBuilder, BiomeSpawner> {

    @Override
    public void handle(BiomeSpawner value, Biome.@NotNull BiomeBuilder key) {
        if (value == null) return;

        key.mobSpawnSettings((MobSpawnSettings) value.toMinecraft());
    }

    @Override
    public BiomeSpawner build(@NotNull CustomBiome biome) {
        return biome.getBiomeSpawner();
    }
}
