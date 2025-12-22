package me.outspending.biomesapi.packet;

import com.comphenix.protocol.events.ListenerPriority;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.biome.CustomBiome;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

@AsOf("0.0.4")
public interface PacketHandler {


    /**
     * Registers the necessary packet listeners to handle biome injection.
     */
    @AsOf("0.0.4")
    void register();

    /**
     * Unregisters the packet listeners handling biome injection.
     */
    @AsOf("0.0.4")
    void unregister();

    /**
     * Have this packet handler always send the given biome to the given player when they receive chunk
     * with light level packets. The packet handler will inject the biome data into the chunk data before sending it
     * to the player.
     *
     * @param player The player to always send the biome to
     * @param biome The custom biome to always send
     * @param condition The condition to check before sending the biome
     */
    @AsOf("0.0.4")
    void registerBiomeOverride(@NotNull Player player, @NotNull CustomBiome biome, @NotNull Predicate<Player> condition);


    /**
     * Unregisters any biome override for the given player.
     *
     * @param player The player to unregister the biome override for
     * @return true if a biome override was unregistered, false otherwise
     */
    @AsOf("0.0.4")
    boolean unregisterBiomeOverride(@NotNull Player player);


    enum DimensionSectionCount {
        OVERWORLD(24), // from y=-64 to y=320, each section is 16 blocks high
        NETHER(16), // from y=0 to y=256
        END(16); // from y=0 to y=256

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

    enum PacketHandlerPriority {
        HIGHEST,
        HIGH,
        NORMAL,
        LOW,
        LOWEST;


        public <E extends Enum<E>> @NotNull E getDelegatePriority(@NotNull Class<E> enumClass) {
            try {
                return Enum.valueOf(enumClass, this.name());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Cannot map PacketHandlerPriority " + this.name() + " to " + enumClass.getName(), e);
            }
        }
    }

}
