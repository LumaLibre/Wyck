package dev.wyck.registry.internal;

import dev.wyck.annotations.AsOf;
import dev.wyck.model.biome.AbstractBiome;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * A handler interface for building and handling biome-related data.
 * @param <K> the type of the key used for handling
 * @param <V> the type of the value being handled
 * @version 0.0.1
 * @since 0.0.1
 * @author Outspending
 */
@NullMarked
@AsOf("0.0.1")
@ApiStatus.Internal
public interface BuilderHandler<K, V> {

    @AsOf("0.0.1")
    void handle(@Nullable V value, K key);

    @AsOf("0.0.1")
    @Nullable V build(AbstractBiome biome);
}
