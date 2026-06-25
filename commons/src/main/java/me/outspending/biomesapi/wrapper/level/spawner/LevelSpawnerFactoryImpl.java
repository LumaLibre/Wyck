package me.outspending.biomesapi.wrapper.level.spawner;

import me.outspending.biomesapi.annotations.WireFactory;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@ApiStatus.Internal
public class LevelSpawnerFactoryImpl implements LevelSpawner.Factory {

    @Override
    public LevelSpawner phantom() {
        return new VanillaLevelSpawnerImpl(LevelSpawner.Kind.PHANTOM);
    }

    @Override
    public LevelSpawner patrol() {
        return new VanillaLevelSpawnerImpl(LevelSpawner.Kind.PATROL);
    }

    @Override
    public LevelSpawner cat() {
        return new VanillaLevelSpawnerImpl(LevelSpawner.Kind.CAT);
    }

    @Override
    public LevelSpawner villageSiege() {
        return new VanillaLevelSpawnerImpl(LevelSpawner.Kind.VILLAGE_SIEGE);
    }

    @Override
    public LevelSpawner wanderingTrader() {
        return new VanillaLevelSpawnerImpl(LevelSpawner.Kind.WANDERING_TRADER);
    }

    @Override
    public LevelSpawner custom(SpawnTick tick) {
        return new CustomLevelSpawnerImpl(tick);
    }
}