package me.outspending.biomesapi.registry.handlers;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.AbstractBiome;
import me.outspending.biomesapi.registry.internal.BuilderHandler;
import me.outspending.biomesapi.wrapper.entity.BiomeSpawner;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
@AsOf("2.3.0")
@ApiStatus.Internal
public class MobSpawnSettingsHandler implements BuilderHandler<Biome.BiomeBuilder, BiomeSpawner> {

    @Override
    public void handle(@Nullable BiomeSpawner value, Biome.BiomeBuilder key) {
        if (value == null) return;

        key.mobSpawnSettings((MobSpawnSettings) value.toMinecraft());
    }

    @Override
    public BiomeSpawner build(AbstractBiome biome) {
        return biome.getBiomeSpawner();
    }
}
