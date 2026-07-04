package dev.wyck.model.level;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.model.level.dimension.Dimension;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.level.LevelFactory;
import dev.wyck.wrapper.level.noise.chunk.ChunkGenerator;
import dev.wyck.wrapper.level.spawner.LevelSpawner;
import org.bukkit.World;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
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
public interface LevelCreator {

    /**
     * The key the world and its level stem are registered under.
     * @return the key the world and its level stem is registered under
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    ResourceKey resourceKey();


    /**
     * The dimension of the world.
     * @return the dimension of the world
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    Dimension dimension();


    /**
     * The chunk generator to use for the world.
     * @return the chunk generator to use for the world.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    ChunkGenerator generator();

    /**
     * The seed to use for the world.
     * @return the seed to use for the world.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    long seed();

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
     * @apiNote Obsolete, only here for bukkit compatibility.
     */
    @AsOf("2.4.0")
    @ApiStatus.Obsolete
    World.Environment environment();

    /**
     * The persistence of the world.
     * @return the persistence of the world.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    StemPersistence persistence();

    /**
     * The spawners of the world.
     * @return the spawners of the world.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    List<LevelSpawner> spawners();

    /**
     * The name of the world.
     * @return the name of the world, as it appears in Bukkit.
     * @since 2.4.0
     */
    @AsOf("3.0.0")
    String name();

    /**
     * Converts this {@link LevelCreator} to a {@link World}.
     * @return a {@link World} equivalent to this {@link LevelCreator}
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    @Nullable World bukkitWorld();

    /**
     * Creates a new bukkit world from this level creator.
     * @return a new {@link World} from this {@link LevelCreator}
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default World create() {
        return LevelFactory.factory().createWorld(this);
    }

    /**
     * Converts this back into a builder.
     * @return a new builder from this instance
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new builder.
     * @return a new builder
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Creates a new builder seeded with the given level key.
     * @param levelKey the level key to seed the builder with
     * @return a new builder seeded with the given level key
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Builder builder(ResourceKey levelKey) {
        return builder().resourceKey(levelKey);
    }

    @AsOf("2.4.0")
    final class Builder {

        private @Nullable ResourceKey levelKey = null;
        private @Nullable Dimension dimension = null;
        private @Nullable ChunkGenerator generator = null;
        private long seed = ThreadLocalRandom.current().nextLong();
        private boolean generateStructures = true;
        private boolean bonusChest = false;
        private World.Environment environment = World.Environment.CUSTOM;
        private StemPersistence persistence = StemPersistence.TRANSIENT;
        private List<LevelSpawner> spawners = new ArrayList<>();
        private @Nullable String name = null;

        @AsOf("2.4.0")
        public Builder() {}

        public Builder(LevelCreator other) {
            this.levelKey = other.resourceKey();
            this.dimension = other.dimension();
            this.generator = other.generator();
            this.seed = other.seed();
            this.generateStructures = other.generateStructures();
            this.bonusChest = other.bonusChest();
            this.environment = other.environment();
            this.persistence = other.persistence();
            this.spawners = new ArrayList<>(other.spawners());
            this.name = other.name();
        }

        /**
         * Sets the resource key of the level stem.
         * @param levelKey the resource key of the world
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder resourceKey(ResourceKey levelKey) {
            this.levelKey = levelKey;
            return this;
        }

        /**
         * Sets the dimension of the world.
         * @param dimension the dimension of the world
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder dimension(Dimension dimension) {
            this.dimension = dimension;
            return this;
        }

        /**
         * Sets the dimension of the world.
         * @param resourceKey the resource key of the dimension
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder dimension(ResourceKey resourceKey) {
            this.dimension = Dimension.reference(resourceKey);
            return this;
        }

        /**
         * Sets the chunk generator of the world.
         * @param generator the chunk generator of the world
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder generator(ChunkGenerator generator) {
            this.generator = generator;
            return this;
        }

        /**
         * Sets the seed of the world.
         * @param seed the seed of the world
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder seed(long seed) {
            this.seed = seed;
            return this;
        }

        /**
         * Sets whether to generate structures in the world.
         * @param generateStructures whether to generate structures in the world
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder generateStructures(boolean generateStructures) {
            this.generateStructures = generateStructures;
            return this;
        }

        /**
         * Sets whether to generate a bonus chest in the world.
         * @param bonusChest whether to generate a bonus chest in the world
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder bonusChest(boolean bonusChest) {
            this.bonusChest = bonusChest;
            return this;
        }

        /**
         * Sets the environment of the world.
         * @param environment the environment of the world
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder environment(World.Environment environment) {
            this.environment = environment;
            return this;
        }

        /**
         * Sets the persistence of the world.
         * @param persistence the persistence of the world
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder persistence(StemPersistence persistence) {
            this.persistence = persistence;
            return this;
        }

        /**
         * Sets the spawners of the world.
         * @param spawners the spawners of the world
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder spawners(List<LevelSpawner> spawners) {
            this.spawners = spawners;
            return this;
        }

        /**
         * Sets the name of the world.
         * @param name the name of the world, or null to use the default name
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder name(@Nullable String name) {
            this.name = name;
            return this;
        }

        // Friendly builder methods

        /**
         * Alias of {@link #resourceKey(ResourceKey)}
         * @param levelKey the resource key of the world
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder levelKey(ResourceKey levelKey) {
            return resourceKey(levelKey);
        }

        /**
         * Adds a spawner to the world.
         * @param spawner the spawner to add
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder spawner(LevelSpawner spawner) {
            this.spawners.add(spawner);
            return this;
        }

        /**
         * Adds spawners to the world.
         * @param spawners the spawners to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder spawners(LevelSpawner... spawners) {
            Collections.addAll(this.spawners, spawners);
            return this;
        }

        @AsOf("2.4.0")
        public LevelCreator build() {
            Preconditions.checkArgument(levelKey != null, "levelKey must be set");
            Preconditions.checkArgument(generator != null, "generator must be set");
            Preconditions.checkArgument(dimension != null,  "dimension must be set");
            return new LevelCreatorImpl(
                levelKey,
                dimension,
                generator,
                seed,
                generateStructures,
                bonusChest,
                environment,
                persistence,
                spawners,
                name
            );
        }

        @AsOf("2.4.0")
        public World create() {
            return build().create();
        }
    }
}