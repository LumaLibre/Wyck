package me.outspending.biomesapi.renderer.packet;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.exceptions.MissingPacketManipulatorLibraryException;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.renderer.AbstractBiomeRenderer;
import me.outspending.biomesapi.renderer.packet.data.PhonyCustomBiome;
import me.outspending.biomesapi.renderer.updater.BiomeUpdater;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * An interface for handling packet manipulation related to biome injection.
 * It is recommended to use the {@link BiomeUpdater} for updating
 * chunks after appending a biome for immediate changes to a player.
 *
 * <p>
 * An external packet handling library (either ProtocolLib or PacketEvents) is required
 * for this interface.
 * </p>
 * @version 2.1.0
 * @since 0.0.6
 * @author Jsinco
 */
@NullMarked
@AsOf("2.1.0")
public interface PacketHandler extends AbstractBiomeRenderer {

    /**
     * The wire-resolved factory that constructs concrete PacketHandler instances.
     * The implementation lives in the commons module; this points to its canonical class name.
     */
    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.renderer.packet.PacketHandlerFactoryImpl");

    /**
     * Factory contract for constructing PacketHandler instances. Implemented by the commons module.
     */
    @ApiStatus.Internal
    interface Factory {
        /**
         * Constructs a PacketHandler backed by the given manipulator library.
         * @param provider The plugin providing this PacketHandler
         * @param manipulator The packet manipulation library to use
         * @param priority The priority of the packet listener
         * @return A new PacketHandler instance
         * @throws MissingPacketManipulatorLibraryException if the manipulator library is not installed
         */
        PacketHandler create(Plugin provider, Injector manipulator, Priority priority);
    }

    /**
     * Creates a PacketHandler using ProtocolLib as the underlying packet manipulation library.
     * The packet listener priority defaults to NORMAL.
     * @param provider The plugin providing this PacketHandler
     * @return A new PacketHandler instance
     * @since 0.0.6
     * @throws MissingPacketManipulatorLibraryException if ProtocolLib is not installed
     */
    @AsOf("2.1.0")
    static PacketHandler of(Plugin provider) {
        return of(provider, Injector.NETTY, Priority.NORMAL);
    }


    /**
     * Creates a PacketHandler using the specified packet manipulation library.
     * The packet listener priority defaults to NORMAL.
     * @param provider The plugin providing this PacketHandler
     * @param injector The packet manipulation library to use
     * @return A new PacketHandler instance
     * @since 2.1.0
     * @throws MissingPacketManipulatorLibraryException if the specified manipulator library is not installed
     */
    @AsOf("2.1.0")
    static PacketHandler of(Plugin provider, Injector injector) {
        return of(provider, injector, Priority.NORMAL);
    }

    /**
     * Creates a PacketHandler using the specified packet manipulation library.
     * The packet listener priority defaults to NORMAL.
     * @param provider The plugin providing this PacketHandler
     * @param manipulator The packet manipulation library to use
     * @return A new PacketHandler instance
     * @since 0.0.19
     * @throws MissingPacketManipulatorLibraryException if the specified manipulator library is not installed
     */
    @AsOf("0.0.19")
    @Deprecated(since = "2.1.0", forRemoval = true)
    static PacketHandler of(Plugin provider, Manipulator manipulator) {
        return of(provider, manipulator.injector, Priority.NORMAL);
    }

    /**
     * Creates a PacketHandler using the specified packet manipulation library.
     * @param provider The plugin providing this PacketHandler
     * @param injector The packet manipulation library to use
     * @param priority The priority of the packet listener
     * @return A new PacketHandler instance
     * @since 2.1.0
     * @throws MissingPacketManipulatorLibraryException if the specified injector library is not installed
     */
    @AsOf("2.1.0")
    static PacketHandler of(Plugin provider, Injector injector, PacketHandler.Priority priority) {
        return WIRE.get().create(provider, injector, priority);
    }

    /**
     * Creates a PacketHandler using the specified packet manipulation library.
     * @param provider The plugin providing this PacketHandler
     * @param manipulator The packet manipulation library to use
     * @param priority The priority of the packet listener
     * @return A new PacketHandler instance
     * @since 0.0.19
     * @throws MissingPacketManipulatorLibraryException if the specified injector library is not installed
     */
    @AsOf("0.0.19")
    static PacketHandler of(Plugin provider, Manipulator manipulator, PacketHandler.Priority priority) {
        return WIRE.get().create(provider, manipulator.injector, priority);
    }

    /**
     * Registers the necessary packet listeners to handle biome injection.
     * Calling this in your plugin's onLoad/onEnable is recommended
     * assuming your plugin soft-depends on PacketEvents and/or ProtocolLib.
     * @return the registered PacketHandler instance
     */
    @AsOf("0.0.6")
    PacketHandler register();

    /**
     * Unregisters the packet listeners handling biome injection.
     * Calling this in your plugin's onDisable is recommended.
     * @return the unregistered PacketHandler instance
     */
    @AsOf("0.0.6")
    PacketHandler unregister();

    /**
     * Appends a custom biome to the packet handler's list of biomes to inject.
     * @param biome The phony custom biome to append
     * @return the PacketHandler instance for chaining
     */
    @AsOf("0.0.6")
    PacketHandler appendBiome(PhonyCustomBiome biome);

    /**
     * Appends multiple custom biomes to the packet handler's list of biomes to inject.
     * @param biomes The phony custom biomes to append
     * @return the PacketHandler instance for chaining
     */
    @AsOf("0.0.6")
    default PacketHandler appendBiome(PhonyCustomBiome... biomes) {
        for (PhonyCustomBiome biome : biomes) {
            appendBiome(biome);
        }
        return this;
    }

    /**
     * Removes a custom biome from the packet handler's list of biomes to inject.
     * @param biome The phony custom biome to remove
     * @return true if the biome was removed, false if it was not found
     */
    @AsOf("0.0.6")
    boolean removeBiome(PhonyCustomBiome biome);

    /**
     * Removes a custom biome from the packet handler's list of biomes to inject.
     * @param biome The phony custom biome to remove
     * @return the PacketHandler instance for chaining
     */
    @AsOf("2.1.0")
    default PacketHandler dismissBiome(PhonyCustomBiome biome) {
        removeBiome(biome);
        return this;
    }

    /**
     * Removes a custom biome from the packet handler's list of biomes to inject by its BiomeResourceKey.
     * @param biomeKey The BiomeResourceKey of the biome to remove
     * @return true if the biome was removed, false if it was not found
     */
    @AsOf("0.0.6")
    boolean removeBiome(BiomeResourceKey biomeKey);

    /**
     * Removes a custom biome from the packet handler's list of biomes to inject by its BiomeResourceKey.
     * @param biomeKey The BiomeResourceKey of the biome to remove
     * @return the PacketHandler instance for chaining
     */
    @AsOf("2.1.0")
    default PacketHandler dismissBiome(BiomeResourceKey biomeKey) {
        removeBiome(biomeKey);
        return this;
    }

    /**
     * Checks if a custom biome is in the packet handler's list of biomes to inject.
     * @param biome The phony custom biome to check
     * @return true if the biome is present, false otherwise
     */
    @AsOf("0.0.10")
    boolean hasBiome(PhonyCustomBiome biome);

    /**
     * Checks if a custom biome is in the packet handler's list of biomes to inject by its BiomeResourceKey.
     * @param biomeKey The BiomeResourceKey of the biome to check
     * @return true if the biome is present, false otherwise
     */
    @AsOf("0.0.10")
    boolean hasBiome(BiomeResourceKey biomeKey);

    /**
     * Clears all phony custom biomes from the packet handler's list of biomes to inject.
     * @return the PacketHandler instance for chaining
     */
    @AsOf("0.0.6")
    PacketHandler clearBiomes();

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
        public static DimensionSectionCount fromBukkitEnvironment(World.Environment environment) {
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
         * @throws IllegalArgumentException if the enum class does not have a constant with the same name
         */
        @ApiStatus.Internal
        public <E extends Enum<E>> E getDelegatePriority(Class<E> enumClass) {
            try {
                return Enum.valueOf(enumClass, this.name());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Cannot map PacketHandlerPriority " + this.name() + " to " + enumClass.getName(), e);
            }
        }
    }

    /**
     * Enum constant for supported packet manipulation libraries.
     * @version 2.1.0
     * @since 2.1.0
     * @author Jsinco
     */
    @AsOf("2.1.0")
    enum Injector {
        /** Requires ProtocolLib to be installed. */
        PROTOCOLLIB("ProtocolLib", "com.comphenix.protocol.ProtocolLibrary"),
        /** Requires PacketEvents to be installed.
         * By default, PacketEvents does NOT re-sync registries.
         * The server administrator needs to add '-Dpacketevents.force-per-user-registries=true' to their JVM arguments. */
        PACKETEVENTS("PacketEvents", "com.github.retrooper.packetevents.PacketEvents"),
        /** Standalone implementation. */
        NETTY("Netty", null);

        private final String name;
        private final @Nullable String className;

        Injector(String name, @Nullable String className) {
            this.name = name;
            this.className = className;
        }

        public String getName() {
            return name;
        }

        public boolean isAvailable() {
            if (className == null) return true;
            try {
                Class.forName(className);
                return true;
            } catch (ClassNotFoundException e) {
                return false;
            }
        }

    }

    /**
     * Enum constant for supported packet manipulation libraries.
     * @version 0.0.19
     * @since 0.0.19
     * @author Jsinco
     * @deprecated Use {@link Injector} instead.
     */
    @AsOf("0.0.19")
    @Deprecated(since = "2.1.0", forRemoval = true)
    enum Manipulator {
        PROTOCOLLIB(Injector.PROTOCOLLIB),
        PACKETEVENTS(Injector.PACKETEVENTS);

        private final Injector injector;

        Manipulator(Injector injector) {
            this.injector = injector;
        }

        public String getName() {
            return injector.getName();
        }

        public boolean isAvailable() {
            return injector.isAvailable();
        }
    }
}