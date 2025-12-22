package me.outspending.biomesapi.packet;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.exceptions.MissingPacketLibraryException;
import me.outspending.biomesapi.packet.data.PhonyCustomBiome;
import me.outspending.biomesapi.packet.handlers.ProtocolLibPacketHandler;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * An interface for handling packet manipulation related to biome injection.
 * It is recommended to use the {@link me.outspending.biomesapi.BiomeUpdater} for updating
 * chunks after appending a biome for immediate changes to a player.
 *
 * <p>
 * An external packet handling library (either ProtocolLib or PacketEvents) is required
 * for this interface.
 * </p>
 * @version 0.0.4
 * @author Jsinco
 */
@me.outspending.biomesapi.annotations.Experimental
@ApiStatus.Experimental
@AsOf("0.0.4")
public interface PacketHandler {


    /**
     * Creates a PacketHandler using ProtocolLib as the underlying packet manipulation library.
     * The packet listener priority defaults to NORMAL.
     * @param provider The plugin providing this PacketHandler
     * @return A new PacketHandler instance
     */
    @AsOf("0.0.4")
    static @NotNull PacketHandler of(@NotNull Plugin provider) {
        return of(provider, Priority.NORMAL);
    }

    /**
     * Creates a PacketHandler using ProtocolLib as the underlying packet manipulation library.
     * @param provider The plugin providing this PacketHandler
     * @param priority The priority of the packet listener
     * @return A new PacketHandler instance
     */
    @AsOf("0.0.4")
    static @NotNull PacketHandler of(@NotNull Plugin provider, @NotNull PacketHandler.Priority priority) {
        try {
            return new ProtocolLibPacketHandler(provider, priority);
        } catch (ClassNotFoundException e) {
            throw new MissingPacketLibraryException("Could not find ProtocolLib classes. Please ensure ProtocolLib is installed.", e);
        }
    }

    /**
     * Registers the necessary packet listeners to handle biome injection.
     * Calling this in your plugin's onLoad/onEnable is recommended
     * assuming your plugin soft-depends on PacketEvents and/or ProtocolLib.
     */
    @AsOf("0.0.4")
    void register();

    /**
     * Unregisters the packet listeners handling biome injection.
     * Calling this in your plugin's onDisable is recommended.
     */
    @AsOf("0.0.4")
    void unregister();


    /**
     * Appends a custom biome to the packet handler's list of biomes to inject.
     * @param biome The phony custom biome to append
     */
    @AsOf("0.0.4")
    void appendBiome(@NotNull PhonyCustomBiome biome);


    /**
     * Appends multiple custom biomes to the packet handler's list of biomes to inject.
     * @param biomes The phony custom biomes to append
     */
    @AsOf("0.0.4")
    default void appendBiome(@NotNull PhonyCustomBiome... biomes) {
        for (PhonyCustomBiome biome : biomes) {
            appendBiome(biome);
        }
    }


    /**
     * Removes a custom biome from the packet handler's list of biomes to inject.
     * @param biome The phony custom biome to remove
     * @return true if the biome was removed, false if it was not found
     */
    @AsOf("0.0.4")
    boolean removeBiome(@NotNull PhonyCustomBiome biome);

    /**
     * Removes a custom biome from the packet handler's list of biomes to inject by its NamespacedKey.
     * @param biomeKey The NamespacedKey of the biome to remove
     * @return true if the biome was removed, false if it was not found
     */
    @AsOf("0.0.4")
    boolean removeBiome(@NotNull NamespacedKey biomeKey);


    /**
     * Clears all phony custom biomes from the packet handler's list of biomes to inject.
     */
    @AsOf("0.0.4")
    void clearBiomes();


    /**
     * The number of sections in each dimension.
     * @version 0.0.4
     */
    @AsOf("0.0.4")
    enum DimensionSectionCount {
        /** from y=-64 to y=320 = 24, each section is 16 blocks high */
        OVERWORLD(24),
        /** from y=0 to y=256 = 16 */
        NETHER(16),
        /** from y=0 to y=256 = 16 */
        END(16);

        private final int sectionCount;

        DimensionSectionCount(int sectionCount) {
            this.sectionCount = sectionCount;
        }

        public int getSectionCount() {
            return sectionCount;
        }

        /**
         * Get the DimensionSectionCount from a Bukkit World.Environment
         * @param environment The Bukkit World.Environment
         * @return The corresponding DimensionSectionCount
         */
        @AsOf("0.0.4")
        public static @NotNull DimensionSectionCount fromBukkitEnvironment(@NotNull World.Environment environment) {
            return switch (environment) {
                case NORMAL, CUSTOM -> OVERWORLD;
                case NETHER -> NETHER;
                case THE_END -> END;
            };
        }
    }

    /**
     * Enum constant for priority levels related to BiomesAPI packet handling.
     *
     * @see PhonyCustomBiome
     * @see PacketHandler
     * @see ProtocolLibPacketHandler
     * @see me.outspending.biomesapi.packet.handlers.PacketEventsPacketHandler
     * @version 0.0.4
     */
    @AsOf("0.0.4")
    enum Priority {
        LOWEST(0),
        LOW(1),
        NORMAL(2),
        HIGH(3),
        HIGHEST(4);

        private final int level;

        Priority(int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }

        /**
         * Maps this PacketHandler.Priority to another enum class with the same names.
         * @param enumClass The enum class to map to
         * @return The mapped enum constant
         * @param <E> The enum type
         * @throws IllegalArgumentException if the enum class does not have a constant with the same
         */
        @ApiStatus.Internal
        public <E extends Enum<E>> @NotNull E getDelegatePriority(@NotNull Class<E> enumClass) {
            try {
                return Enum.valueOf(enumClass, this.name());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Cannot map PacketHandlerPriority " + this.name() + " to " + enumClass.getName(), e);
            }
        }

    }

}
