package dev.wyck.biome.entity;

import dev.wyck.util.MinecraftEntityTypes;
import dev.wyck.util.WeightedList;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@NullMarked
@ApiStatus.Internal
public record MobSpawnSettingsImpl(
    Map<MobCategory, WeightedList<SpawnerData>> spawnsByCategory,
    @Override Map<EntityType, MobSpawnCost> allSpawnCosts
) implements MobSpawnSettings {

    public MobSpawnSettingsImpl {
        Map<MobCategory, WeightedList<SpawnerData>> spawnCopy = new EnumMap<>(MobCategory.class);
        spawnCopy.putAll(spawnsByCategory);
        spawnsByCategory = Collections.unmodifiableMap(spawnCopy);
        allSpawnCosts = Collections.unmodifiableMap(new LinkedHashMap<>(allSpawnCosts));
    }

    @Override
    public WeightedList<SpawnerData> getMobsToSpawn(MobCategory category) {
        return this.spawnsByCategory.getOrDefault(category, EMPTY_MOB_LIST);
    }

    @Override
    public @Nullable WeightedList<SpawnerData> getMobsInCategory(MobCategory category) {
        return this.spawnsByCategory.get(category);
    }

    @Override
    public Set<MobCategory> definedCategories() {
        return this.spawnsByCategory.keySet();
    }

    @Override
    public @Nullable MobSpawnCost getMobSpawnCost(EntityType type) {
        return this.allSpawnCosts.get(type);
    }

    @Override
    public net.minecraft.world.level.biome.MobSpawnSettings toMinecraft() {
        net.minecraft.world.level.biome.MobSpawnSettings.Builder builder =
            new net.minecraft.world.level.biome.MobSpawnSettings.Builder();

        this.spawnsByCategory.forEach((category, spawns) -> {
            net.minecraft.world.entity.MobCategory minecraftCategory =
                category.toNms(net.minecraft.world.entity.MobCategory.class);
            if (spawns.isEmpty()) {
                builder.noSpawns(minecraftCategory);
                return;
            }

            for (WeightedList.Weighted<SpawnerData> entry : spawns.unwrap()) {
                SpawnerData spawn = entry.value();
                net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData minecraftSpawn =
                    spawn.asHandle();
                builder.addSpawn(
                    minecraftSpawn.type(),
                    minecraftCategory,
                    entry.weight(),
                    minecraftSpawn.count()
                );
            }
        });

        this.allSpawnCosts.forEach((type, cost) -> builder.addMobSpawnCost(
            MinecraftEntityTypes.toMinecraft(type),
            cost.charge(),
            cost.energyBudget()
        ));
        return builder.build();
    }
}
