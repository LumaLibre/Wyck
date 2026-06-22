package me.outspending.biomesapi.level;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.level.dimension.Dimension;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.wrapper.level.LevelChunkGenerator;
import me.outspending.biomesapi.wrapper.level.spawner.LevelSpawner;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;

@NullMarked
@AsOf("2.4.0")
public final class LevelCreatorImpl implements LevelCreator {

    private final ResourceKey levelKey;
    private final @Nullable Dimension dimension;
    private final ResourceKey dimensionType;
    private final LevelChunkGenerator generator;
    private final long seed;
    private final boolean generateStructures;
    private final boolean bonusChest;
    private final World.Environment environment;
    private final StemPersistence persistence;
    private final List<LevelSpawner> spawners;

    LevelCreatorImpl(
        ResourceKey levelKey,
        @Nullable Dimension dimension,
        ResourceKey dimensionType,
        LevelChunkGenerator generator,
        long seed,
        boolean generateStructures,
        boolean bonusChest,
        World.Environment environment,
        StemPersistence persistence,
        List<LevelSpawner> spawners
    ) {
        this.levelKey = levelKey;
        this.dimension = dimension;
        this.dimensionType = dimensionType;
        this.generator = generator;
        this.seed = seed;
        this.generateStructures = generateStructures;
        this.bonusChest = bonusChest;
        this.environment = environment;
        this.persistence = persistence;
        this.spawners = spawners;
    }

    @Override
    public ResourceKey getLevelKey() {
        return levelKey;
    }

    @Override
    public @Nullable Dimension getDimension() {
        return dimension;
    }

    @Override
    public ResourceKey getDimensionType() {
        return dimensionType;
    }

    @Override
    public LevelChunkGenerator getGenerator() {
        return generator;
    }

    @Override
    public long getSeed() {
        return seed;
    }

    @Override
    public boolean generateStructures() {
        return generateStructures;
    }

    @Override
    public boolean bonusChest() {
        return bonusChest;
    }

    @Override
    public World.Environment getEnvironment() {
        return environment;
    }

    @Override
    public StemPersistence getPersistence() {
        return persistence;
    }

    @Override
    public List<LevelSpawner> getSpawners() {
        return spawners;
    }

    @Override
    public @Nullable World asBukkitWorld() {
        return Bukkit.getWorld(levelKey);
    }
}