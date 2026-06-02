package me.outspending.biomesapi.biome;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.exceptions.UnknownBiomeException;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * This is a utility class that provides static methods to interact with the biome registry in a Minecraft server.
 * It provides methods to retrieve a Biome object from the registry and to check if a biome exists in the registry.
 *
 * @version 0.0.1
 * @since 0.0.1
 * @author Outspending
 * @deprecated Use {@link RegisteredBiomes} instead.
 */
@Deprecated
@AsOf("0.0.1")
public class BiomeHandler {

    private BiomeHandler() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated.");
    }

    /**
     * This method gets the registered biomes list
     *
     * @since 0.0.1
     * @return a list of all registered biomes
     * @deprecated Use {@link RegisteredBiomes#registeredBiomes()} instead.
     */
    @Deprecated
    @AsOf("0.0.1")
    public static List<CustomBiome> getRegisteredBiomes() {
        return List.copyOf(RegisteredBiomes.registeredBiomes());
    }

    /**
     * This method retrieves a Biome object from the Minecraft server's biome registry.
     * It uses the Bukkit API to get the server instance and then accesses the server's biome registry.
     * If the biome registry exists, it retrieves the Biome object corresponding to the provided BiomeResourceKey.
     * If the biome registry does not exist or the Biome object does not exist in the registry, it returns null.
     *
     * @param resourceKey The BiomeResourceKey for the biome that needs to be retrieved.
     * @return Biome object corresponding to the provided BiomeResourceKey, or null if the biome does not exist.
     *
     * @throws UnknownBiomeException if the biome does not exist in the registry.
     * @since 0.0.1
     * @deprecated Use {@link RegisteredBiomes#getOrThrow(BiomeResourceKey)} instead.
     */
    @Deprecated
    @AsOf("0.0.18")
    public static @Nullable CustomBiome getBiome(@NotNull BiomeResourceKey resourceKey) {
        return RegisteredBiomes.get(resourceKey);
    }

    /**
     * This method checks if a biome exists in the Minecraft server's biome registry.
     * It uses the getBiome method to retrieve the Biome object corresponding to the provided BiomeResourceKey.
     * If the Biome object exists, it returns true. Otherwise, it returns false.
     *
     * @param resourceKey The BiomeResourceKey for the biome that needs to be checked.
     * @return true if the biome exists in the registry, false otherwise.
     *
     * @since 0.0.1
     * @deprecated Use {@link RegisteredBiomes#isRegistered(BiomeResourceKey)} instead.
     */
    @Deprecated
    @AsOf("0.0.18")
    public static boolean isBiome(@NotNull BiomeResourceKey resourceKey) {
        return RegisteredBiomes.isRegistered(resourceKey);
    }

    /**
     * Replaces an existing {@link CustomBiome} in the registered biomes list with a new one.
     * @param resourceKey The resource key of the biome to be replaced.
     * @param newBiome The new {@link CustomBiome} to replace the existing one.
     * @return true if the biome was successfully replaced, false if no matching biome was found.
     * @since 0.0.8
     * @deprecated This method is not supported and will always return false.
     */
    @AsOf("0.0.8")
    @Deprecated
    public static boolean replaceBiome(@NotNull BiomeResourceKey resourceKey, @NotNull CustomBiome newBiome) {
        throw new UnsupportedOperationException("Unsupported");
    }
}
