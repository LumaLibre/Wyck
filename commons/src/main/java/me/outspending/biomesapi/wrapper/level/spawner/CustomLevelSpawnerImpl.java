package me.outspending.biomesapi.wrapper.level.spawner;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import me.outspending.biomesapi.keys.KeyChains;
import net.kyori.adventure.key.InvalidKeyException;
import net.kyori.adventure.key.Key;
import net.minecraft.world.level.CustomSpawner;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public record CustomLevelSpawnerImpl(SpawnTick tick) implements LevelSpawner {

    public static final Codec<CustomLevelSpawnerImpl> CODEC = Codec.STRING.comapFlatMap(
        string -> {
            try {
                KeyedSpawnTick resolved = KeyChains.customLevelSpawners().get(Key.key(string));
                return resolved == null
                    ? DataResult.error(() -> "no custom level spawner registered under '" + string + "'")
                    : DataResult.success(new CustomLevelSpawnerImpl(resolved));
            } catch (InvalidKeyException e) {
                return DataResult.error(() -> "invalid key '" + string + "': " + e.getMessage());
            }
        },
        spawner -> {
            KeyedSpawnTick keyed = spawner.keyedTick();
            if (keyed == null) {
                throw new IllegalStateException(
                    "this custom level spawner was created with a plain SpawnTick and cannot be serialized; "
                        + "use a KeyedSpawnTick registered in KeyChains.customLevelSpawners() for serialization");
            }
            return keyed.key().asString();
        }
    );

    @Override
    public Object toMinecraft(Object savedDataStorage) {
        return (CustomSpawner) (level, spawnEnemies) ->
            this.tick.tick(level.getWorld(), spawnEnemies);
    }

    @Override
    public LevelSpawner.@Nullable Kind kind() {
        return null;
    }

    @Override
    public @Nullable KeyedSpawnTick keyedTick() {
        return this.tick instanceof KeyedSpawnTick keyed ? keyed : null;
    }
}