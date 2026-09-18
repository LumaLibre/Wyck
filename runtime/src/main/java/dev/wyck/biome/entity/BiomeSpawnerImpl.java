package dev.wyck.biome.entity;

import dev.wyck.annotations.AsOf;
import dev.wyck.biome.entity.data.NaturalSpawner;
import dev.wyck.biome.entity.data.SpawnCost;
import dev.wyck.util.MinecraftEntityTypes;
import dev.wyck.util.WeightedList;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Map;

@NullMarked
@AsOf("2.3.0")
@ApiStatus.Internal
@Deprecated(since = "4.0.0")
public class BiomeSpawnerImpl implements BiomeSpawner {

    private final Map<MobCategory, WeightedList<NaturalSpawner>> spawners;
    private final Map<EntityType, SpawnCost> mobSpawnCosts;

    public BiomeSpawnerImpl(Map<MobCategory, WeightedList<NaturalSpawner>> spawners, Map<EntityType, SpawnCost> mobSpawnCosts) {
        this.spawners = spawners;
        this.mobSpawnCosts = mobSpawnCosts;
    }

    @Override
    public Map<MobCategory, WeightedList<NaturalSpawner>> spawners() {
        return this.spawners;
    }

    @Override
    public Map<EntityType, SpawnCost> mobSpawnCosts() {
        return this.mobSpawnCosts;
    }

    /**
     * Builds the underlying NMS {@link MobSpawnSettings} from this wrapper.
     *
     * <p>Spawn weights are taken from each {@link WeightedList} entry (not from {@link NaturalSpawner}),
     * so they survive the translation as long as they were supplied when the list was built.
     */
    @Override
    public MobSpawnSettings toMinecraft() {
        MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
        this.spawners.forEach((category, list) -> {
            net.minecraft.world.entity.MobCategory nmsCategory = category.toNms(net.minecraft.world.entity.MobCategory.class);
            for (WeightedList.Weighted<NaturalSpawner> entry : list.unwrap()) {
                NaturalSpawner spawner = entry.value();
                net.minecraft.world.entity.EntityType<?> nmsType = MinecraftEntityTypes.toMinecraft(spawner.type());
                builder.addSpawn(
                    nmsType,
                    nmsCategory,
                    entry.weight(),
                    spawner.minCount() == spawner.maxCount()
                        ? net.minecraft.util.valueproviders.ConstantInt.of(spawner.minCount())
                        : net.minecraft.util.valueproviders.UniformInt.of(spawner.minCount(), spawner.maxCount())
                );
            }
        });

        this.mobSpawnCosts.forEach((type, cost) -> {
            net.minecraft.world.entity.EntityType<?> nmsType = MinecraftEntityTypes.toMinecraft(type);
            builder.addMobSpawnCost(nmsType, cost.charge(), cost.energyBudget());
        });

        return builder.build();
    }

}
