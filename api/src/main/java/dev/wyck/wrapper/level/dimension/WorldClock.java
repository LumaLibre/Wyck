package dev.wyck.wrapper.level.dimension;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.WireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.internal.Wrapper;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * A reference to a registered world clock.
 *
 * @version 2.4.0
 * @since 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
public interface WorldClock extends Wrapper, Keyed {

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("dev.wyck.wrapper.level.dimension.WorldClockFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        WorldClock reference(ResourceKey key);
    }

    WorldClock OVERWORLD = reference(ResourceKey.minecraft("overworld"));
    WorldClock THE_END = reference(ResourceKey.minecraft("the_end"));

    /**
     * The registry key this clock points at.
     * @return the world clock key
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    @Override
    ResourceKey key();

    /**
     * Creates a reference to a registered world clock.
     * @param key the key of an entry in the world-clock registry
     * @return a reference to that world clock
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static WorldClock reference(ResourceKey key) {
        return WIRE.get().reference(key);
    }
}