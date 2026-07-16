package dev.wyck.level.entity;

import dev.wyck.annotations.WireFactory;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@ApiStatus.Internal
public class LevelSpawnerFactoryImpl implements LevelSpawner.Factory {

    @Override
    public LevelSpawner vanilla(LevelSpawner.Kind kind) {
        return new VanillaLevelSpawnerImpl(kind);
    }

    @Override
    public LevelSpawner custom(SpawnTick tick) {
        return new CustomLevelSpawnerImpl(tick);
    }
}