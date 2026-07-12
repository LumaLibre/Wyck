package dev.wyck.wrapper.level.entity;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.WireProvider;
import dev.wyck.wrapper.internal.ContextWrapper;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("2.4.0")
public interface LevelSpawner extends ContextWrapper<Object> {

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("dev.wyck.wrapper.level.entity.LevelSpawnerFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        LevelSpawner phantom();
        LevelSpawner patrol();
        LevelSpawner cat();
        LevelSpawner villageSiege();
        LevelSpawner wanderingTrader();
        LevelSpawner custom(SpawnTick tick);
    }

    /**
     * @param savedDataStorage the world's {@code net.minecraft.world.level.storage.SavedDataStorage}
     * @return the underlying {@code net.minecraft.world.level.CustomSpawner}
     * @since 2.4.0
     */
    @Override
    @AsOf("2.4.0")
    Object toMinecraft(Object savedDataStorage);

    @AsOf("2.4.0")
    static LevelSpawner phantom() {
        return WIRE.get().phantom();
    }

    @AsOf("2.4.0")
    static LevelSpawner patrol() {
        return WIRE.get().patrol();
    }

    @AsOf("2.4.0")
    static LevelSpawner cat() {
        return WIRE.get().cat();
    }

    @AsOf("2.4.0")
    static LevelSpawner villageSiege() {
        return WIRE.get().villageSiege();
    }

    @AsOf("2.4.0")
    static LevelSpawner wanderingTrader() {
        return WIRE.get().wanderingTrader();
    }

    @AsOf("2.4.0")
    static LevelSpawner custom(SpawnTick tick) {
        return WIRE.get().custom(tick);
    }
}