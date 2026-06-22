package me.outspending.biomesapi.biome;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.exceptions.UnknownBiomeException;
import me.outspending.biomesapi.keys.KeyChains;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.util.Lazy;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Iterator;
import java.util.Set;

/**
 * A collection of all registered custom biomes.
 * <p>
 * Obsolete. Use {@link KeyChains#BIOMES} instead.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
@ApiStatus.Obsolete
public final class RegisteredBiomes implements Iterable<CustomBiome> {

    private RegisteredBiomes() {}

    /**
     * Returns the custom biome with the given resource key, or null if not found.
     * @param resourceKey the resource key of the biome to retrieve
     * @return the custom biome with the given resource key, or null if not found
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public static @Nullable CustomBiome get(ResourceKey resourceKey) {
        return KeyChains.BIOMES.get(resourceKey);
    }

    /**
     * Returns the custom biome with the given resource key or throws an UnknownBiomeException if not found.
     * @param resourceKey the resource key of the biome to retrieve
     * @return the custom biome with the given resource key
     * @throws UnknownBiomeException if the biome is not found
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public static CustomBiome getOrThrow(ResourceKey resourceKey) {
        return KeyChains.BIOMES.getOrThrow(resourceKey);
    }

    /**
     * Returns a lazy version of get.
     * @param resourceKey the resource key of the biome to retrieve
     * @return a lazy version of get
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public static Lazy<@Nullable CustomBiome> getLazily(ResourceKey resourceKey) {
        return KeyChains.BIOMES.getLazily(resourceKey);
    }

    /**
     * Returns a lazy version of getOrThrow.
     * @param resourceKey the resource key of the biome to retrieve
     * @return a lazy version of getOrThrow
     * @throws UnknownBiomeException if the biome is not found
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public static Lazy<CustomBiome> getOrThrowLazily(ResourceKey resourceKey) {
        return KeyChains.BIOMES.getOrThrowLazily(resourceKey);
    }

    /**
     * Checks if a biome with the given resource key is registered.
     * @param resourceKey the resource key of the biome to check
     * @return true if the biome is registered, false otherwise
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public static boolean isRegistered(ResourceKey resourceKey) {
        return KeyChains.BIOMES.isRegistered(resourceKey);
    }

    /**
     * Returns a lazy version of isRegistered.
     * @param resourceKey the resource key of the biome to check
     * @return a lazy version of isRegistered
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public static Lazy<Boolean> isRegisteredLazily(ResourceKey resourceKey) {
        return KeyChains.BIOMES.isRegisteredLazily(resourceKey);
    }

    /**
     * Returns an iterator over the registered custom biomes.
     * @return an iterator over the registered custom biomes
     * @since 2.3.0
     */
    @Override
    @AsOf("2.3.0")
    public Iterator<CustomBiome> iterator() {
        return KeyChains.BIOMES.iterator();
    }

    /**
     * Returns the number of registered custom biomes.
     * @return the number of registered custom biomes
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public static int size() {
        return KeyChains.BIOMES.size();
    }

    /**
     * Returns true if there are no registered custom biomes.
     * @return true if there are no registered custom biomes
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public static boolean isEmpty() {
        return KeyChains.BIOMES.isEmpty();
    }

    /**
     * Returns a copy of the set of registered custom biomes.
     * @return a copy of the set of registered custom biomes
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public static Set<CustomBiome> registeredBiomes() {
        return KeyChains.BIOMES.items();
    }

    @ApiStatus.Internal
    public static void appendBiome(CustomBiome biome) {
        KeyChains.BIOMES.append(biome);
    }

    @ApiStatus.Internal
    public static boolean replaceBiome(ResourceKey resourceKey, CustomBiome newBiome) {
        return KeyChains.BIOMES.replace(resourceKey, newBiome);
    }
}
