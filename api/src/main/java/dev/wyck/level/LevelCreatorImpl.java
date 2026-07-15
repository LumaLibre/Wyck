package dev.wyck.level;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.level.dimension.Dimension;
import dev.wyck.level.entity.LevelSpawner;
import dev.wyck.worldgen.chunk.ChunkGenerator;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;

/**
 * This implementation is here because it does not compile
 * against Minecraft internals as of right now.
 * Do not directly use this class, use {@link LevelCreator}.
 */
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
    private final @Nullable String name;

    public LevelCreatorImpl(
        ResourceKey levelKey,
        Dimension dimension,
        ChunkGenerator generator,
        long seed,
        boolean generateStructures,
        boolean bonusChest,
        World.Environment environment,
        StemPersistence persistence,
        List<LevelSpawner> spawners,
        @Nullable String name
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
        this.name = name;
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
    public String name() {
        return this.name != null ? this.name : this.levelKey.path()
            .replace("/", "_")
            .replace(".", "_");
    }

    @Override
    public @Nullable World bukkitWorld() {
        return Bukkit.getWorld(levelKey);
    }

    @Override
    public String toString() {
        return "LevelCreatorImpl{" +
            "levelKey=" + levelKey +
            ", dimension=" + dimension +
            ", generator=" + generator +
            ", seed=" + seed +
            ", generateStructures=" + generateStructures +
            ", bonusChest=" + bonusChest +
            ", environment=" + environment +
            ", persistence=" + persistence +
            ", spawners=" + spawners +
            ", name='" + name + '\'' +
            '}';
    }
}