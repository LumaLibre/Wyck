package dev.wyck.biome.entity;

import com.google.common.collect.Maps;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.util.WeightedList;
import dev.wyck.worldgen.valueproviders.IntProvider;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import org.bukkit.entity.EntityType;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

/**
 * Controls the mobs that may spawn naturally and the biome spawn cost assigned to each mob.
 *
 * @see dev.wyck.environment.attribute.EnvironmentAttributes#NATURAL_MOB_SPAWNS
 * @see <a href="https://minecraft.wiki/w/Mob_spawning">Mob spawning</a>
 * @since 4.0.0
 * @version 4.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("4.0.0")
public interface MobSpawnSettings extends Wrapper {


    float DEFAULT_CREATURE_WORLD_GEN_SPAWN_PROBABILITY = 0.1F;
    WeightedList<SpawnerData> EMPTY_MOB_LIST = WeightedList.of();
    MobSpawnSettings EMPTY = builder().build();
    MobSpawnSettings NO_SPAWNS = noSpawns();

    /**
     * Gets the weighted spawn entries for a category, returning an empty list when the category
     * is not defined.
     * @param category the mob category to query
     * @return the category's spawn entries, or an empty weighted list when it is not defined
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    WeightedList<SpawnerData> getMobsToSpawn(MobCategory category);

    /**
     * Gets the weighted spawn entries explicitly defined for a category.
     *
     * <p>An empty list means the category is explicitly disabled, while {@code null} means these
     * settings do not override the category.
     *
     * @param category the mob category to query
     * @return the defined spawn entries, or {@code null} when the category is not overridden
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    @Nullable WeightedList<SpawnerData> getMobsInCategory(MobCategory category);

    /**
     * Gets the categories explicitly controlled by these settings.
     * @return the defined mob categories
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    Set<MobCategory> definedCategories();

    /**
     * Gets the spawn cost assigned to an entity type.
     * @param type the entity type to query
     * @return the entity's spawn cost, or {@code null} when no cost is defined
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    @Nullable MobSpawnCost getMobSpawnCost(EntityType type);

    /**
     * Gets every explicitly defined mob spawn cost.
     * @return an unmodifiable map of entity types to spawn costs
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    Map<EntityType, MobSpawnCost> allSpawnCosts();

    /**
     * Converts these settings back into a builder.
     * @return a builder containing these settings
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates mob spawn settings.
     * @param spawnsByCategory the spawn entries defined for each category
     * @param mobSpawnCosts the spawn costs defined for each entity type
     * @return new mob spawn settings
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static MobSpawnSettings of(Map<MobCategory, WeightedList<SpawnerData>> spawnsByCategory, Map<EntityType, MobSpawnCost> mobSpawnCosts) {
        record Holder() {
            static final ConstructWireProvider<MobSpawnSettings> WIRE = ConstructWireProvider.create("dev.wyck.biome.entity.MobSpawnSettingsImpl");
        }
        return Holder.WIRE.construct(spawnsByCategory, mobSpawnCosts);
    }

    /**
     * Creates a new mob spawn settings builder.
     * @return a new builder
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Reads Minecraft mob spawn settings into a wrapper.
     * @param minecraftMobSpawnSettings the Minecraft mob spawn settings to read
     * @return the wrapped mob spawn settings
     * @since 4.0.0
     */
    @AsOf("4.0.0")
    static MobSpawnSettings decode(Object minecraftMobSpawnSettings) {
        record Holder() {
            static final Decoder<MobSpawnSettings> DECODER = Decoder.create("dev.wyck.decode.biome.entity.MobSpawnSettingsDecoder");
        }
        return Holder.DECODER.decode(minecraftMobSpawnSettings);
    }

    private static MobSpawnSettings noSpawns() { // I don't really like this here
        Builder builder = builder();
        for (MobCategory category : MobCategory.values()) {
            builder.noSpawns(category);
        }
        return builder.build();
    }

    /**
     * Builder for {@link MobSpawnSettings}.
     *
     * @since 4.0.0
     * @version 4.0.0
     * @author Jsinco
     */
    @AsOf("4.0.0")
    final class Builder {
        private final Map<MobCategory, WeightedList.Builder<SpawnerData>> spawnsByCategory = new EnumMap<>(MobCategory.class);
        private final Map<EntityType, MobSpawnCost> mobSpawnCosts = Maps.newLinkedHashMap();

        public Builder() {}

        public Builder(MobSpawnSettings settings) {
            for (MobCategory category : settings.definedCategories()) {
                WeightedList<SpawnerData> spawns = settings.getMobsInCategory(category);
                if (spawns != null) {
                    this.addAllSpawns(category, spawns);
                }
            }
            this.mobSpawnCosts.putAll(settings.allSpawnCosts());
        }

        /**
         * Adds an entity using its natural mob category and an inclusive group-size range.
         * @param type the entity type to spawn
         * @param weight the relative selection weight of the spawn entry
         * @param minCount the minimum group size
         * @param maxCount the maximum group size
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder addSpawn(EntityType type, int weight, int minCount, int maxCount) {
            IntProvider count = minCount == maxCount
                ? IntProvider.constant(minCount)
                : IntProvider.uniform(minCount, maxCount);
            return this.addSpawn(type, weight, count);
        }

        /**
         * Adds an entity using its natural mob category.
         * @param type the entity type to spawn
         * @param weight the relative selection weight of the spawn entry
         * @param count the provider used to select the group size
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder addSpawn(EntityType type, int weight, IntProvider count) {
            SpawnerData spawn = SpawnerData.of(type, count);
            this.forCategory(spawn.naturalCategory()).add(spawn, weight);
            return this;
        }

        /**
         * Adds an entity under an explicitly selected mob category.
         *
         * @param type the entity type to spawn
         * @param category the category under which the entry is defined
         * @param weight the relative selection weight of the spawn entry
         * @param count the provider used to select the group size
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder addSpawn(EntityType type, MobCategory category, int weight, IntProvider count) {
            this.forCategory(category).add(SpawnerData.of(type, count), weight);
            return this;
        }

        /**
         * Adds every spawn entry in a weighted list to a category.
         * @param category the category to update
         * @param spawns the spawn entries to add
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder addAllSpawns(MobCategory category, WeightedList<SpawnerData> spawns) {
            this.forCategory(category).addAll(spawns.unwrap());
            return this;
        }

        /**
         * Explicitly disables natural spawning for a category.
         * @param category the category to disable
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder noSpawns(MobCategory category) {
            this.spawnsByCategory.put(category, WeightedList.builder());
            return this;
        }

        /**
         * Removes an explicit category override.
         * @param category the category that should inherit its surrounding settings
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder dontOverride(MobCategory category) {
            this.spawnsByCategory.remove(category);
            return this;
        }

        /**
         * Defines a mob spawn cost.
         * @param type the entity type to update
         * @param charge the energy charged for each nearby mob of this type
         * @param energyBudget the maximum local energy budget available to the mob
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder addMobSpawnCost(EntityType type, double charge, double energyBudget) {
            this.mobSpawnCosts.put(type, MobSpawnCost.of(energyBudget, charge));
            return this;
        }

        /**
         * Adds every supplied mob spawn cost.
         * @param costs the spawn costs to add
         * @return this builder
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public Builder addAllCosts(Map<EntityType, MobSpawnCost> costs) {
            this.mobSpawnCosts.putAll(costs);
            return this;
        }

        /**
         * Builds the mob spawn settings.
         * @return the built mob spawn settings
         * @since 4.0.0
         */
        @AsOf("4.0.0")
        public MobSpawnSettings build() {
            Map<MobCategory, WeightedList<SpawnerData>> builtSpawns = new EnumMap<>(MobCategory.class);
            this.spawnsByCategory.forEach((category, spawns) -> builtSpawns.put(category, spawns.build()));
            return MobSpawnSettings.of(builtSpawns, this.mobSpawnCosts);
        }

        private WeightedList.Builder<SpawnerData> forCategory(MobCategory category) {
            return this.spawnsByCategory.computeIfAbsent(category, ignored -> WeightedList.builder());
        }
    }
}
