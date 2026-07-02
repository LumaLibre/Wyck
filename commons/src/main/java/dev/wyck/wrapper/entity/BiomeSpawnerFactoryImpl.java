package dev.wyck.wrapper.entity;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.WireFactory;
import dev.wyck.util.WeightedList;
import dev.wyck.wrapper.entity.data.SpawnCost;
import dev.wyck.wrapper.entity.data.NaturalSpawner;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@NullMarked
@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public class BiomeSpawnerFactoryImpl implements BiomeSpawner.Factory {
    @Override
    public BiomeSpawner create(Map<MobCategory, WeightedList.Builder<NaturalSpawner>> spawners, Map<EntityType, SpawnCost> mobSpawnCosts, float creatureGenerationProbability) {
        Map<MobCategory, WeightedList<NaturalSpawner>> built = new LinkedHashMap<>();
        spawners.forEach((category, listBuilder) -> built.put(category, listBuilder.build()));

        return new BiomeSpawnerImpl(
            Collections.unmodifiableMap(built),
            Collections.unmodifiableMap(new LinkedHashMap<>(mobSpawnCosts)),
            creatureGenerationProbability
        );
    }
}
