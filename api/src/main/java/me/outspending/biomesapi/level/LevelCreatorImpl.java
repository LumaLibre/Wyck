package me.outspending.biomesapi.level;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.level.dimension.Dimension;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.serialization.Codecs;
import me.outspending.biomesapi.wrapper.level.noise.chunk.ChunkGenerator;
import me.outspending.biomesapi.wrapper.level.spawner.LevelSpawner;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

@NullMarked
@AsOf("2.4.0")
public final class LevelCreatorImpl implements LevelCreator {

    public static final MapCodec<LevelCreatorImpl> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        ResourceKey.CODEC.fieldOf("level_key").forGetter(LevelCreatorImpl::getLevelKey),
        Dimension.CODEC.optionalFieldOf("dimension").forGetter(c -> Optional.ofNullable(c.getDimension())),
        ResourceKey.CODEC.fieldOf("dimension_type").forGetter(LevelCreatorImpl::getDimensionType),
        ChunkGenerator.CODEC.fieldOf("generator").forGetter(LevelCreatorImpl::getGenerator),
        Codec.LONG.fieldOf("seed").forGetter(LevelCreatorImpl::getSeed),
        Codec.BOOL.optionalFieldOf("generate_structures", true).forGetter(LevelCreatorImpl::generateStructures),
        Codec.BOOL.optionalFieldOf("bonus_chest", false).forGetter(LevelCreatorImpl::bonusChest),
        Codecs.WORLD_ENVIRONMENT_CODEC.fieldOf("environment").forGetter(LevelCreatorImpl::getEnvironment),
        StemPersistence.CODEC.fieldOf("persistence").forGetter(LevelCreatorImpl::getPersistence),
        Codec.list(LevelSpawner.CODEC).optionalFieldOf("spawners", List.of()).forGetter(LevelCreatorImpl::getSpawners)
    ).apply(instance, (levelKey, dimension, dimensionType, generator, seed, generateStructures, bonusChest, environment, persistence, spawners) ->
        new LevelCreatorImpl(
            levelKey,
            dimension.orElse(null),
            dimensionType,
            generator,
            seed,
            generateStructures,
            bonusChest,
            environment,
            persistence,
            spawners
        )
    ));

    private final ResourceKey levelKey;
    private final @Nullable Dimension dimension;
    private final ResourceKey dimensionType;
    private final ChunkGenerator generator;
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
    public ChunkGenerator getGenerator() {
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