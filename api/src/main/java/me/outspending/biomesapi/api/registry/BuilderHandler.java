package me.outspending.biomesapi.api.registry;

import me.outspending.biomesapi.api.annotations.AsOf;
import me.outspending.biomesapi.api.biome.CustomBiome;
import org.jetbrains.annotations.NotNull;

/**
 * A handler interface for building and handling biome-related data.
 * @param <K> the type of the key used for handling
 * @param <V> the type of the value being handled
 * @version 0.0.1
 * @since 0.0.1
 * @author Outspending
 */
@AsOf("0.0.1")
public interface BuilderHandler<K, V> {

    @AsOf("0.0.1")
    void handle(V value, @NotNull K key);

    @AsOf("0.0.1")
    V build(@NotNull CustomBiome biome);
}
