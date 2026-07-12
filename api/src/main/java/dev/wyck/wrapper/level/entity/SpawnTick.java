package dev.wyck.wrapper.level.entity;

import dev.wyck.annotations.AsOf;
import org.bukkit.World;
import org.jspecify.annotations.NullMarked;

/**
 * A custom spawn tick handler.
 *
 * @since 2.4.0
 * @version 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
@FunctionalInterface
public interface SpawnTick {

    /**
     * Called during the world's spawn tick.
     *
     * @param world the world being ticked
     * @param spawnEnemies whether hostile spawning is currently enabled for this tick
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    void tick(World world, boolean spawnEnemies);
}