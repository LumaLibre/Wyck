package me.outspending.biomesapi.registry;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.CustomBiome;
import org.jetbrains.annotations.NotNull;

/**
 * An interface for registering and modifying custom biomes on a Minecraft server.
 *
 * @apiNote Biomes can not be removed from the Minecraft registry once they have been added.
 * You may {@link #modify(CustomBiome) modify} existing biomes to reduce memory footprint.
 *
 * @version 0.0.1
 * @since 0.0.1
 * @author Outspending
 */
@AsOf("0.0.1")
public interface BiomeRegistry {

    /**
     * This static method creates a new BiomeRegistry object.
     * It returns a new instance of CustomBiomeRegistry.
     *
     * @version 0.0.1
     * @return a new CustomBiomeRegistry object.
     */
    @AsOf("0.0.1")
    static BiomeRegistry newRegistry() {
        return new CustomBiomeRegistry();
    }

    /**
     * This method registers a custom biome to a Minecraft server.
     * It takes a CustomBiome object as an argument.
     *
     * @version 0.0.1
     * @param biome The CustomBiome object that should be registered to the server.
     */
    @AsOf("0.0.1")
    void register(@NotNull CustomBiome biome);


    /**
     * This method modifies an existing biome on the Minecraft server.
     * Another biome must already exist with the same ResourceKey.
     * It takes a CustomBiome object as an argument.
     *
     * @throws IllegalArgumentException if the biome does not already exist in the registry.
     * @apiNote This method can only change the properties of an existing biome on the <b>server</b>.
     * Clients which have already received the biome will not see any changes until they enter the reconfiguration phase
     * (e.g., by rejoining the server.)
     * @version 0.0.8
     * @param biome The CustomBiome that should internally be used to modify the existing biome.
     */
    @AsOf("0.0.8")
    void modify(@NotNull CustomBiome biome);

}
