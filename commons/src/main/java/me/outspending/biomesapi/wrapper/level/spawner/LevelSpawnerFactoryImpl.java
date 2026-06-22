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
        return new VanillaLevelSpawnerImpl(VanillaLevelSpawnerImpl.Kind.PHANTOM);
    }

    @Override
    public LevelSpawner patrol() {
        return new VanillaLevelSpawnerImpl(VanillaLevelSpawnerImpl.Kind.PATROL);
    }

    @Override
    public LevelSpawner cat() {
        return new VanillaLevelSpawnerImpl(VanillaLevelSpawnerImpl.Kind.CAT);
    }

    @Override
    public LevelSpawner villageSiege() {
        return new VanillaLevelSpawnerImpl(VanillaLevelSpawnerImpl.Kind.VILLAGE_SIEGE);
    }

    @Override
    public LevelSpawner wanderingTrader() {
        return new VanillaLevelSpawnerImpl(VanillaLevelSpawnerImpl.Kind.WANDERING_TRADER);
    }

    @Override
    public LevelSpawner custom(SpawnTick tick) {
        return new CustomLevelSpawnerImpl(tick);
    }
}