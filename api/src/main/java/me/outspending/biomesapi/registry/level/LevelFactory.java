package me.outspending.biomesapi.registry.level;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.level.LevelCreator;
import me.outspending.biomesapi.factory.WireProvider;
import org.bukkit.World;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Creates live Bukkit worlds backed by a custom dimension type and chunk generator.
 *
 * @apiNote On a region-threaded server (Folia) this must be invoked from the
 * global region thread, mirroring {@code CraftServer#createWorld}.
 *
 * @version 2.4.0
 * @since 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
@ApiStatus.Experimental
public interface LevelFactory {

    @ApiStatus.Internal
    WireProvider<LevelFactory> WIRE = WireProvider.create("me.outspending.biomesapi.*?.registry.level.SimpleLevelFactory");

    @AsOf("2.4.0")
    static LevelFactory factory() {
        return WIRE.get();
    }

    /**
     * Creates and loads a world from the given specification.
     *
     * @param world the world specification
     * @return the loaded Bukkit world
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    World createWorld(LevelCreator world);
}