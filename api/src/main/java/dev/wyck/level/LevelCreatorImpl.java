package dev.wyck.level;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.level.dimension.LevelStem;
import dev.wyck.level.entity.LevelSpawner;
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
    private final LevelStem levelStem;
    private final long seed;
    private final boolean generateStructures;
    private final boolean bonusChest;
    private final World.Environment environment;
    @SuppressWarnings("removal")
    private final StemPersistence persistence;
    private final List<LevelSpawner> spawners;
    private final @Nullable String name;
    private final LevelType type;

    @SuppressWarnings("removal")
    public LevelCreatorImpl(
        ResourceKey levelKey,
        LevelStem levelStem,
        long seed,
        boolean generateStructures,
        boolean bonusChest,
        World.Environment environment,
        StemPersistence persistence,
        List<LevelSpawner> spawners,
        @Nullable String name,
        LevelType type
    ) {
        this.levelKey = levelKey;
        this.levelStem = levelStem;
        this.seed = seed;
        this.generateStructures = generateStructures;
        this.bonusChest = bonusChest;
        this.environment = environment;
        this.persistence = persistence;
        this.spawners = spawners;
        this.name = name;
        this.type = type;
    }

    @Override
    public ResourceKey resourceKey() {
        return levelKey;
    }

    @Override
    public LevelStem levelStem() {
        return levelStem;
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
    @SuppressWarnings("removal")
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
    public LevelType type() {
        return this.type;
    }

    @Override
    public @Nullable World bukkitWorld() {
        return Bukkit.getWorld(levelKey);
    }

    @Override
    public String toString() {
        return "LevelCreatorImpl{" +
            "levelKey=" + levelKey +
            ", levelStem=" + levelStem +
            ", seed=" + seed +
            ", generateStructures=" + generateStructures +
            ", bonusChest=" + bonusChest +
            ", environment=" + environment +
            ", persistence=" + persistence +
            ", spawners=" + spawners +
            ", name='" + name + '\'' +
            ", type=" + type +
            '}';
    }
}