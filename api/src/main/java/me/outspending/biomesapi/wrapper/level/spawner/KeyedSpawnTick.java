package me.outspending.biomesapi.wrapper.level.spawner;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.keys.KeyChains;
import net.kyori.adventure.key.Keyed;

/**
 * A spawn tick handler that can be registered with a key.
 *
 * @since 2.4.0
 * @version 2.4.0
 * @author Jsinco
 */
@AsOf("2.4.0")
public interface KeyedSpawnTick extends SpawnTick, Keyed {

    default KeyedSpawnTick register() {
        KeyChains.CUSTOM_LEVEL_SPAWNERS.append(this);
        return this;
    }
}
