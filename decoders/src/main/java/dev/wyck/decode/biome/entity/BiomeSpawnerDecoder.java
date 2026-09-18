package dev.wyck.decode.biome.entity;

import dev.wyck.biome.entity.BiomeSpawner;
import dev.wyck.biome.entity.MobCategory;
import dev.wyck.biome.entity.data.NaturalSpawner;
import dev.wyck.biome.entity.data.SpawnCost;
import dev.wyck.decode.Decoders;
import dev.wyck.wrapper.decode.Decodable;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
@Deprecated(since = "4.0.0", forRemoval = true)
public final class BiomeSpawnerDecoder implements Decodable<BiomeSpawner, MobSpawnSettings> {

    @Override
    public BiomeSpawner decode(MobSpawnSettings settings) {
        BiomeSpawner.Builder builder = BiomeSpawner.builder();

        for (net.minecraft.world.entity.MobCategory category : settings.definedCategories()) {
            net.minecraft.util.random.WeightedList<MobSpawnSettings.SpawnerData> spawners = settings.getMobsInCategory(category);
            if (spawners != null) {
                builder.spawners(
                    MobCategory.TRANSLATOR.fromNms(category),
                    Decoders.weighted(spawners, NaturalSpawner::decode)
                );
            }
        }

        settings.allSpawnCosts().forEach((type, cost) -> builder.spawnCost(
            Decoders.bukkitEntityType(type),
            SpawnCost.decode(cost)
        ));
        return builder.build();
    }
}
