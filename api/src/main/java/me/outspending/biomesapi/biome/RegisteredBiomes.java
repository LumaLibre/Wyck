package me.outspending.biomesapi.biome;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.exceptions.UnknownBiomeException;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.util.Lazy;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A collection of all registered custom biomes.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public final class RegisteredBiomes implements Iterable<CustomBiome> {

    private static final Set<CustomBiome> REGISTERED_BIOMES = new LinkedHashSet<>();

    private RegisteredBiomes() {}

    /**
     * Returns the custom biome with the given resource key, or null if not found.
     * @param resourceKey the resource key of the biome to retrieve
     * @return the custom biome with the given resource key, or null if not found
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public static @Nullable CustomBiome get(ResourceKey resourceKey) {
        Preconditions.checkNotNull(resourceKey, "resourceKey cannot be null");

        return REGISTERED_BIOMES.stream()
            .filter(biome -> resourceKey.equals(biome.getResourceKey()))
            .findFirst()
            .orElse(null);
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
        CustomBiome biome = get(resourceKey);
        if (biome == null) {
            throw new UnknownBiomeException("Unknown biome: " + resourceKey);
        }
        return biome;
    }

    /**
     * Returns a lazy version of get.
     * @param resourceKey the resource key of the biome to retrieve
     * @return a lazy version of get
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public static Lazy<@Nullable CustomBiome> getLazily(ResourceKey resourceKey) {
        return Lazy.of(() -> get(resourceKey));
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
        return Lazy.of(() -> getOrThrow(resourceKey));
    }

    /**
     * Checks if a biome with the given resource key is registered.
     * @param resourceKey the resource key of the biome to check
     * @return true if the biome is registered, false otherwise
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public static boolean isRegistered(ResourceKey resourceKey) {
        Preconditions.checkNotNull(resourceKey, "resourceKey cannot be null");
        return REGISTERED_BIOMES.stream().anyMatch(biome -> resourceKey.equals(biome.getResourceKey()));
    }

    /**
     * Returns a lazy version of isRegistered.
     * @param resourceKey the resource key of the biome to check
     * @return a lazy version of isRegistered
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public static Lazy<Boolean> isRegisteredLazily(ResourceKey resourceKey) {
        return Lazy.of(() -> isRegistered(resourceKey));
    }

    /**
     * Returns an iterator over the registered custom biomes.
     * @return an iterator over the registered custom biomes
     * @since 2.3.0
     */
    @Override
    @AsOf("2.3.0")
    public Iterator<CustomBiome> iterator() {
        return REGISTERED_BIOMES.iterator();
    }

    /**
     * Returns the number of registered custom biomes.
     * @return the number of registered custom biomes
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public static int size() {
        return REGISTERED_BIOMES.size();
    }

    /**
     * Returns true if there are no registered custom biomes.
     * @return true if there are no registered custom biomes
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public static boolean isEmpty() {
        return REGISTERED_BIOMES.isEmpty();
    }

    /**
     * Returns a copy of the set of registered custom biomes.
     * @return a copy of the set of registered custom biomes
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public static Set<CustomBiome> registeredBiomes() {
        return Set.copyOf(REGISTERED_BIOMES);
    }

    @ApiStatus.Internal
    public static void appendBiome(CustomBiome biome) {
        Preconditions.checkNotNull(biome, "biome cannot be null");
        if (isRegistered(biome.getResourceKey())) {
            throw new IllegalArgumentException("Biome is already appended: " + biome.getResourceKey());
        }
        REGISTERED_BIOMES.add(biome);
    }

    @ApiStatus.Internal
    public static boolean replaceBiome(ResourceKey resourceKey, CustomBiome newBiome) {
        Preconditions.checkNotNull(resourceKey, "resourceKey cannot be null");
        Preconditions.checkNotNull(newBiome, "newBiome cannot be null");

        CustomBiome existing = get(resourceKey);
        if (existing == null) {
            return false;
        }
        REGISTERED_BIOMES.remove(existing);
        REGISTERED_BIOMES.add(newBiome);
        return true;
    }
}
