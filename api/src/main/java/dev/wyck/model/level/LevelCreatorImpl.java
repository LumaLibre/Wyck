package dev.wyck.model.level;

import dev.wyck.annotations.AsOf;
import dev.wyck.model.level.dimension.Dimension;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.level.noise.chunk.ChunkGenerator;
import dev.wyck.wrapper.level.spawner.LevelSpawner;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;

@NullMarked
@AsOf("2.4.0")
@ApiStatus.Internal
public final class LevelCreatorImpl implements LevelCreator {

    private final ResourceKey levelKey;
    private final Dimension dimension;
    private final ChunkGenerator generator;
    private final long seed;
    private final boolean generateStructures;
    private final boolean bonusChest;
    private final World.Environment environment;
    private final StemPersistence persistence;
    private final List<LevelSpawner> spawners;

    public LevelCreatorImpl(
        ResourceKey levelKey,
        Dimension dimension,
        ChunkGenerator generator,
        long seed,
        boolean generateStructures,
        boolean bonusChest,
        World.Environment environment,
        StemPersistence persistence,
        List<LevelSpawner> spawners
    ) {
        this.levelKey = levelKey;
        this.dimension = dimension;
        this.generator = generator;
        this.seed = seed;
        this.generateStructures = generateStructures;
        this.bonusChest = bonusChest;
        this.environment = environment;
        this.persistence = persistence;
        this.spawners = spawners;
    }

    @Override
    public ResourceKey resourceKey() {
        return levelKey;
    }

    @Override
    public Dimension dimension() {
        return dimension;
    }

    @Override
    public ChunkGenerator generator() {
        return generator;
    }

    @Override
    public long seed() {
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
    public World.Environment environment() {
        return environment;
    }

    @Override
    public StemPersistence persistence() {
        return persistence;
    }

    @Override
    public List<LevelSpawner> spawners() {
        return spawners;
    }

    @Override
    public @Nullable World bukkitWorld() {
        return Bukkit.getWorld(levelKey);
    }
}