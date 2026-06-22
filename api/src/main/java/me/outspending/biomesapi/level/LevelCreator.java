package me.outspending.biomesapi.level;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.level.dimension.Dimension;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.wrapper.level.LevelChunkGenerator;
import me.outspending.biomesapi.wrapper.level.spawner.LevelSpawner;
import org.bukkit.World;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A more in depth representation of a {@link org.bukkit.WorldCreator}.
 *
 * @version 2.4.0
 * @since 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
@ApiStatus.Experimental
public interface LevelCreator {

    /**
     * The key the world and its level stem are registered under.
     * @return the key the world and its level stem is registered under
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    ResourceKey getLevelKey();


    /**
     * A custom dimension to register before creating the world, or {@code null} if {@link #getDimensionType()} is preregistered.
     * @return a custom dimension to register before creating the world, or {@code null} if {@link #getDimensionType()} is preregistered.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    @Nullable Dimension getDimension();


    /**
     * Key of an already-registered dimension type, or {@code null} if {@link #getDimension()} is supplied.
     * @return key of an already-registered dimension type, or {@code null} if {@link #getDimension()} is supplied.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    @Nullable ResourceKey getDimensionType();

    /**
     * The chunk generator to use for the world.
     * @return the chunk generator to use for the world.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    LevelChunkGenerator getGenerator();

    /**
     * The seed to use for the world.
     * @return the seed to use for the world.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    long getSeed();

    /**
     * Whether to generate structures in the world.
     * @return whether to generate structures in the world.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    boolean generateStructures();

    /**
     * Whether to generate a bonus chest in the world.
     * @return whether to generate a bonus chest in the world.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    boolean bonusChest();

    /**
     * The environment of the world.
     * @return the environment of the world.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    World.Environment getEnvironment();

    /**
     * The persistence of the world.
     * @return the persistence of the world.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    StemPersistence getPersistence();

    /**
     * The spawners of the world.
     * @return the spawners of the world.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    List<LevelSpawner> getSpawners();

    /**
     * Converts this {@link LevelCreator} to a {@link World}.
     * @return a {@link World} equivalent to this {@link LevelCreator}
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    @Nullable World asBukkitWorld();

    /**
     * @return a new {@link Builder} instance
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Creates a new {@link Builder} instance with the given level key.
     * @param levelKey the level key to use
     * @return a new {@link Builder} instance with the given level key
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Builder builder(ResourceKey levelKey) {
        Preconditions.checkNotNull(levelKey, "levelKey cannot be null");
        return new Builder().levelKey(levelKey);
    }

    @AsOf("2.4.0")
    final class Builder {

        private @Nullable ResourceKey levelKey = null;
        private @Nullable Dimension dimension = null;
        private @Nullable ResourceKey dimensionType = null;
        private @Nullable LevelChunkGenerator generator = null;
        private long seed = ThreadLocalRandom.current().nextLong();
        private boolean generateStructures = true;
        private boolean bonusChest = false;
        private World.Environment environment = World.Environment.CUSTOM;
        private StemPersistence persistence = StemPersistence.TRANSIENT;
        private List<LevelSpawner> spawners = List.of();

        @AsOf("2.4.0")
        public Builder() {}

        @AsOf("2.4.0")
        public Builder levelKey(ResourceKey levelKey) {
            this.levelKey = levelKey;
            return this;
        }

        @AsOf("2.4.0")
        public Builder dimension(Dimension dimension) {
            this.dimension = dimension;
            this.dimensionType = dimension.getResourceKey();
            return this;
        }

        @AsOf("2.4.0")
        public Builder dimensionType(ResourceKey dimensionType) {
            this.dimensionType = dimensionType;
            return this;
        }

        @AsOf("2.4.0")
        public Builder generator(LevelChunkGenerator generator) {
            this.generator = generator;
            return this;
        }

        @AsOf("2.4.0")
        public Builder seed(long seed) {
            this.seed = seed;
            return this;
        }

        @AsOf("2.4.0")
        public Builder generateStructures(boolean generateStructures) {
            this.generateStructures = generateStructures;
            return this;
        }

        @AsOf("2.4.0")
        public Builder bonusChest(boolean bonusChest) {
            this.bonusChest = bonusChest;
            return this;
        }

        @AsOf("2.4.0")
        public Builder environment(World.Environment environment) {
            this.environment = environment;
            return this;
        }

        @AsOf("2.4.0")
        public Builder persistence(StemPersistence persistence) {
            this.persistence = persistence;
            return this;
        }

        @AsOf("2.4.0")
        public Builder spawners(List<LevelSpawner> spawners) {
            this.spawners = List.copyOf(spawners);
            return this;
        }

        @AsOf("2.4.0")
        public Builder addSpawner(LevelSpawner spawner) {
            List<LevelSpawner> next = new ArrayList<>(this.spawners);
            next.add(spawner);
            this.spawners = List.copyOf(next);
            return this;
        }

        @AsOf("2.4.0")
        public LevelCreator build() {
            Preconditions.checkArgument(levelKey != null, "levelKey must be set");
            Preconditions.checkArgument(generator != null, "generator must be set");
            Preconditions.checkArgument(dimensionType != null, "either a dimension or a preregistered dimensionType must be set");
            return new LevelCreatorImpl(
                levelKey,
                dimension,
                dimensionType,
                generator,
                seed,
                generateStructures,
                bonusChest,
                environment,
                persistence,
                spawners
            );
        }
    }
}