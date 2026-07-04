package dev.wyck.wrapper.level.spawner;

import net.minecraft.world.level.CustomSpawner;
import org.jspecify.annotations.NullMarked;

@NullMarked
public record CustomLevelSpawnerImpl(SpawnTick tick) implements LevelSpawner {

    @Override
    public Object toMinecraft(Object savedDataStorage) {
        return (CustomSpawner) (level, spawnEnemies) ->
            this.tick.tick(level.getWorld(), spawnEnemies);
    }
}