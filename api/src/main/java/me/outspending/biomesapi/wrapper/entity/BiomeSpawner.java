package me.outspending.biomesapi.wrapper.entity;

import com.google.common.collect.Maps;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.wrapper.NmsHandle;
import me.outspending.biomesapi.wrapper.entity.data.SpawnCost;
import me.outspending.biomesapi.wrapper.entity.data.NaturalSpawner;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Represents the spawning settings for a biome, including the spawners for each mob category, the spawn costs for each mob type, and the creature generation probability.
 * This interface wraps Minecraft's MobSpawnSettings class.
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@AsOf("2.3.0")
public interface BiomeSpawner extends NmsHandle {

    @AsOf("2.3.0")
    float DEFAULT_CREATURE_GENERATION_PROBABILITY = 0.1F;

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.entity.BiomeSpawnerFactoryImpl");

    @AsOf("2.3.0")
    @ApiStatus.Internal
    interface Factory {
        @NotNull BiomeSpawner create(@NotNull Map<MobCategory, WeightedList.Builder<NaturalSpawner>> spawners, @NotNull Map<EntityType, SpawnCost> mobSpawnCosts, float creatureGenerationProbability);
    }

    /**
     * Gets the spawners for each mob category.
     * @return the spawners for each mob category
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    @NotNull Map<MobCategory, WeightedList<NaturalSpawner>> spawners();

    /**
     * Gets the spawn cost for each mob type.
     * @return the spawn cost for each mob type
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    @NotNull Map<EntityType, SpawnCost> mobSpawnCosts();

    /**
     * Gets the creature generation probability.
     * @return the creature generation probability
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    float creatureGenerationProbability();

    /**
     * Creates a new builder from this BiomeSpawner.
     * @return a new builder from this BiomeSpawner
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    default @NotNull Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * @return a new BiomeSpawner builder
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static @NotNull Builder builder() {
        return new Builder();
    }

    /**
     * Creates an empty BiomeSpawner.
     * @return an empty BiomeSpawner
     */
    @AsOf("2.3.0")
    static @NotNull BiomeSpawner empty() {
        return builder().build();
    }

    /**
     * A builder for creating a BiomeSpawner.
     * @since 2.3.0
     * @version 2.3.0
     * @author Jsinco
     */
    @AsOf("2.3.0")
    final class Builder {
        private final Map<MobCategory, WeightedList.Builder<NaturalSpawner>> spawners;
        private final Map<EntityType, SpawnCost> mobSpawnCosts;
        private float creatureGenerationProbability;

        @AsOf("2.3.0")
        public Builder() {
            this.spawners = Maps.newLinkedHashMap();
            this.mobSpawnCosts = Maps.newLinkedHashMap();
            this.creatureGenerationProbability = DEFAULT_CREATURE_GENERATION_PROBABILITY;
        }

        @AsOf("2.3.0")
        public Builder(@NotNull BiomeSpawner biomeSpawner) {
            this.spawners = Maps.newLinkedHashMap();
            for (Map.Entry<MobCategory, WeightedList<NaturalSpawner>> entry : biomeSpawner.spawners().entrySet()) {
                WeightedList.Builder<NaturalSpawner> builder = WeightedList.builder();
                for (WeightedList.Weighted<NaturalSpawner> spawnerEntry : entry.getValue().unwrap()) {
                    builder.add(spawnerEntry.value(), spawnerEntry.weight());
                }
                this.spawners.put(entry.getKey(), builder);
            }
            this.mobSpawnCosts = Maps.newLinkedHashMap(biomeSpawner.mobSpawnCosts());
            this.creatureGenerationProbability = biomeSpawner.creatureGenerationProbability();
        }

        /**
         * Adds a spawner to the builder.
         * @param category the category of the spawner
         * @param spawner the spawner to add
         * @return the builder instance
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public @NotNull Builder addSpawner(MobCategory category, NaturalSpawner spawner) {
            this.spawners.computeIfAbsent(category, c -> WeightedList.builder()).add(spawner);
            return this;
        }

        /**
         * Adds a spawner to the builder.
         * @param category the category of the spawner
         * @param weight the weight of the spawner
         * @param spawner the spawner to add
         * @return the builder instance
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public @NotNull Builder addSpawner(MobCategory category, int weight, NaturalSpawner spawner) {
            this.spawners.computeIfAbsent(category, c -> WeightedList.builder()).add(spawner, weight);
            return this;
        }

        /**
         * Adds a spawner to the builder.
         * @param category the category of the spawner
         * @param weight the weight of the spawner
         * @param type the entity type of the spawner
         * @param minCount the minimum number of entities to spawn
         * @param maxCount the maximum number of entities to spawn
         * @return the builder instance
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public @NotNull Builder addSpawners(MobCategory category, int weight, EntityType type, int minCount, int maxCount) {
            return this.addSpawner(category, weight, new NaturalSpawner(type, minCount, maxCount));
        }

        /**
         * Adds multiple spawners to the builder.
         * @param category the category of the spawners
         * @param list the list of spawners to add
         * @return the builder instance
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public @NotNull Builder addSpawners(MobCategory category, WeightedList<NaturalSpawner> list) {
            WeightedList.Builder<NaturalSpawner> weightedList = this.spawners.computeIfAbsent(category, c -> WeightedList.builder());
            for (WeightedList.Weighted<NaturalSpawner> entry : list.unwrap()) {
                weightedList.add(entry.value(), entry.weight());
            }
            return this;
        }

        /**
         * Adds a spawn cost to the builder.
         * @param type the entity type to add the spawn cost for
         * @param cost the spawn cost to add
         * @return the builder instance
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public @NotNull Builder addMobSpawnCost(EntityType type, SpawnCost cost) {
            this.mobSpawnCosts.put(type, cost);
            return this;
        }

        /**
         * Adds a spawn cost to the builder.
         * @param type the entity type to add the spawn cost for
         * @param charge the charge to add to the spawn cost
         * @param energyBudget the energy budget to add to the spawn cost
         * @return the builder instance
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public @NotNull Builder addMobSpawnCost(EntityType type, double charge, double energyBudget) {
            this.mobSpawnCosts.put(type, new SpawnCost(charge, energyBudget));
            return this;
        }

        /**
         * Adds multiple spawn costs to the builder.
         * @param costs the spawn costs to add
         * @return the builder instance
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public @NotNull Builder addMobSpawnCosts(Map<EntityType, SpawnCost> costs) {
            this.mobSpawnCosts.putAll(costs);
            return this;
        }

        /**
         * Sets the creature generation probability. Default is 0.1F.
         * Range between 0.0F - 0.9999999F.
         * @param probability The probability of spawning a creature.
         * @return The builder instance.
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public @NotNull Builder setCreatureGenerationProbability(float probability) {
            this.creatureGenerationProbability = probability;
            return this;
        }

        /**
         * Builds the BiomeSpawner.
         * @return The built BiomeSpawner.
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public @NotNull BiomeSpawner build() {
            return WIRE.get().create(spawners, mobSpawnCosts, creatureGenerationProbability);
        }
    }
}
