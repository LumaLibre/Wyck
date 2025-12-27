package me.outspending.biomesapi.packet;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.exceptions.MissingPacketManipulatorLibraryException;
import me.outspending.biomesapi.packet.data.PhonyCustomBiome;
import me.outspending.biomesapi.packet.handlers.PacketEventsPacketHandler;
import me.outspending.biomesapi.packet.handlers.ProtocolLibPacketHandler;
import me.outspending.biomesapi.registry.BiomeResourceKey;
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
 * @version 0.0.19
 * @since 0.0.6
 * @author Jsinco
 */
@ApiStatus.Experimental
@AsOf("0.0.19")
public interface PacketHandler {


    /**
     * Creates a PacketHandler using ProtocolLib as the underlying packet manipulation library.
     * The packet listener priority defaults to NORMAL.
     * @param provider The plugin providing this PacketHandler
     * @return A new PacketHandler instance
     * @since 0.0.6
     * @throws MissingPacketManipulatorLibraryException if ProtocolLib is not installed
     * @deprecated use {@link #of(Plugin, Manipulator)} or {@link #of(Plugin, Manipulator, Priority)} instead
     */
    @Deprecated(since = "0.0.19")
    @AsOf("0.0.6")
    static @NotNull PacketHandler of(@NotNull Plugin provider) {
        return of(provider, Manipulator.PROTOCOLLIB, Priority.NORMAL);
    }

    /**
     * Creates a PacketHandler using ProtocolLib as the underlying packet manipulation library.
     * The packet listener priority defaults to NORMAL.
     * @param provider The plugin providing this PacketHandler
     * @return A new PacketHandler instance
     * @since 0.0.19
     * @throws MissingPacketManipulatorLibraryException if the specified manipulator library is not installed
     */
    @AsOf("0.0.19")
    static @NotNull PacketHandler of(@NotNull Plugin provider, @NotNull Manipulator manipulator) {
        return of(provider, manipulator, Priority.NORMAL);
    }

    /**
     * Creates a PacketHandler using ProtocolLib as the underlying packet manipulation library.
     * @param provider The plugin providing this PacketHandler
     * @param priority The priority of the packet listener
     * @return A new PacketHandler instance
     * @since 0.0.6
     * @throws MissingPacketManipulatorLibraryException if the specified manipulator library is not installed
     */
    @AsOf("0.0.19")
    static @NotNull PacketHandler of(@NotNull Plugin provider, @NotNull Manipulator manipulator, @NotNull PacketHandler.Priority priority) {
        return manipulator.create(provider, priority);
    }

    /**
     * Registers the necessary packet listeners to handle biome injection.
     * Calling this in your plugin's onLoad/onEnable is recommended
     * assuming your plugin soft-depends on PacketEvents and/or ProtocolLib.
     */
    @AsOf("0.0.6")
    void register();

    /**
     * Unregisters the packet listeners handling biome injection.
     * Calling this in your plugin's onDisable is recommended.
     */
    @AsOf("0.0.6")
    void unregister();


    /**
     * Appends a custom biome to the packet handler's list of biomes to inject.
     * @param biome The phony custom biome to append
     */
    @AsOf("0.0.6")
    void appendBiome(@NotNull PhonyCustomBiome biome);


    /**
     * Appends multiple custom biomes to the packet handler's list of biomes to inject.
     * @param biomes The phony custom biomes to append
     */
    @AsOf("0.0.6")
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
    @AsOf("0.0.6")
    boolean removeBiome(@NotNull PhonyCustomBiome biome);

    /**
     * Removes a custom biome from the packet handler's list of biomes to inject by its BiomeResourceKey.
     * @param biomeKey The BiomeResourceKey of the biome to remove
     * @return true if the biome was removed, false if it was not found
     */
    @AsOf("0.0.6")
    boolean removeBiome(@NotNull BiomeResourceKey biomeKey);

    /**
     * Checks if a custom biome is in the packet handler's list of biomes to inject.
     * @param biome The phony custom biome to check
     * @return true if the biome is present, false otherwise
     */
    @AsOf("0.0.10")
    boolean hasBiome(@NotNull PhonyCustomBiome biome);

    /**
     * Checks if a custom biome is in the packet handler's list of biomes to inject by its BiomeResourceKey.
     * @param biomeKey The BiomeResourceKey of the biome to check
     * @return true if the biome is present, false otherwise
     */
    @AsOf("0.0.10")
    boolean hasBiome(@NotNull BiomeResourceKey biomeKey);


    /**
     * Clears all phony custom biomes from the packet handler's list of biomes to inject.
     */
    @AsOf("0.0.6")
    void clearBiomes();


    /**
     * The number of sections in each dimension.
     * @version 0.0.6
     */
    @AsOf("0.0.6")
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
        @AsOf("0.0.6")
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
     * @version 0.0.6
     */
    @AsOf("0.0.6")
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

    /**
     * Enum constant for supported packet manipulation libraries.
     * @version 0.0.19
     * @since 0.0.19
     */
    @AsOf("0.0.19")
    enum Manipulator {
        PROTOCOLLIB(
                "ProtocolLib",
                "com.comphenix.protocol.ProtocolLibrary",
                (provider, priority) -> new ProtocolLibPacketHandler(provider, priority)
        ),
        PACKETEVENTS(
                "PacketEvents",
                "com.github.retrooper.packetevents.PacketEvents",
                (provider, priority) -> new PacketEventsPacketHandler(priority)
        );

        private final String name;
        private final String owningClass;
        private final PacketHandlerFactory factory;

        Manipulator(String name, String owningClass, PacketHandlerFactory factory) {
            this.name = name;
            this.owningClass = owningClass;
            this.factory = factory;
        }

        public String getName() {
            return name;
        }

        public String getOwningClass() {
            return owningClass;
        }

        public boolean isAvailable() {
            try {
                Class.forName(owningClass);
                return true;
            } catch (ClassNotFoundException e) {
                return false;
            }
        }

        public PacketHandler create(@NotNull Plugin provider, @NotNull PacketHandler.Priority priority) throws MissingPacketManipulatorLibraryException {
            if (!isAvailable()) {
                throw new MissingPacketManipulatorLibraryException("Could not find required classes for " + name + ". Please ensure " + name + " is installed.");
            }
            return factory.create(provider, priority);
        }

        @FunctionalInterface
        private interface PacketHandlerFactory {
            @NotNull PacketHandler create(@NotNull Plugin provider, @NotNull PacketHandler.Priority priority);
        }
    }
}
