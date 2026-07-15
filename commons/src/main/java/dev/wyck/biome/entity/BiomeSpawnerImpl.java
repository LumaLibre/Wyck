package dev.wyck.biome.entity;

import dev.wyck.annotations.AsOf;
import dev.wyck.util.WeightedList;
import dev.wyck.biome.entity.BiomeSpawner;
import dev.wyck.biome.entity.MobCategory;
import dev.wyck.biome.entity.data.SpawnCost;
import dev.wyck.biome.entity.data.NaturalSpawner;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Map;

@NullMarked
@AsOf("2.3.0")
@ApiStatus.Internal
public class BiomeSpawnerImpl implements BiomeSpawner {

    private final Map<MobCategory, WeightedList<NaturalSpawner>> spawners;
    private final Map<EntityType, SpawnCost> mobSpawnCosts;
    private final float creatureGenerationProbability;

    public BiomeSpawnerImpl(Map<MobCategory, WeightedList<NaturalSpawner>> spawners, Map<EntityType, SpawnCost> mobSpawnCosts, float creatureGenerationProbability) {
        this.spawners = spawners;
        this.mobSpawnCosts = mobSpawnCosts;
        this.creatureGenerationProbability = creatureGenerationProbability;
    }

    @Override
    public Map<MobCategory, WeightedList<NaturalSpawner>> spawners() {
        return this.spawners;
    }

    @Override
    public Map<EntityType, SpawnCost> mobSpawnCosts() {
        return this.mobSpawnCosts;
    }

    @Override
    public float creatureGenerationProbability() {
        return this.creatureGenerationProbability;
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
        builder.creatureGenerationProbability(this.creatureGenerationProbability);

        this.spawners.forEach((category, list) -> {
            net.minecraft.world.entity.MobCategory nmsCategory = category.toNms(net.minecraft.world.entity.MobCategory.class);
            for (WeightedList.Weighted<NaturalSpawner> entry : list.unwrap()) {
                NaturalSpawner spawner = entry.value();
                net.minecraft.world.entity.EntityType<?> nmsType = nmsEntityType(spawner.type());
                builder.addSpawn(
                    nmsCategory,
                    entry.weight(),
                    new MobSpawnSettings.SpawnerData(nmsType, spawner.minCount(), spawner.maxCount())
                );
            }
        });

        this.mobSpawnCosts.forEach((type, cost) -> {
            net.minecraft.world.entity.EntityType<?> nmsType = nmsEntityType(type);
            builder.addMobCharge(nmsType, cost.charge(), cost.energyBudget());
        });

        return builder.build();
    }

    /**
     * Bukkit entity type -> NMS entity type.
     * @throws IllegalArgumentException if the Bukkit type has no key (e.g. {@code UNKNOWN}) or is not present in the NMS entity registry
     */
    private static net.minecraft.world.entity.EntityType<?> nmsEntityType(org.bukkit.entity.EntityType bukkit) {
        NamespacedKey key = bukkit.getKey();
        Identifier location = Identifier.fromNamespaceAndPath(key.getNamespace(), key.getKey());

        if (!BuiltInRegistries.ENTITY_TYPE.containsKey(location)) {
            throw new IllegalArgumentException("No NMS entity type registered for " + key);
        }
        return BuiltInRegistries.ENTITY_TYPE.getValue(location);
    }
}