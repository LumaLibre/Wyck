package dev.wyck.registry.handlers;

import dev.wyck.annotations.AsOf;
import dev.wyck.model.biome.AbstractBiome;
import dev.wyck.registry.internal.BuilderHandler;
import dev.wyck.wrapper.entity.BiomeSpawner;
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
