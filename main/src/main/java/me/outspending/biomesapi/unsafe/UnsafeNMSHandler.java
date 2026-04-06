package me.outspending.biomesapi.unsafe;

import me.outspending.biomesapi.UnsafeNMS_v1_21_11;
import me.outspending.biomesapi.UnsafeNMS_v26_1_1;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.exceptions.UnknownNMSVersionException;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Utility class for handling NMS (Net Minecraft Server) related operations.
 * This class is annotated with @UtilityClass from the Lombok library, which indicates that this is a utility class and hence, cannot be instantiated.
 * It also generates a private no-args constructor, which throws an exception when invoked.
 *
 * @version 1.2.0
 * @since 0.0.1
 * @author Outspending
 */
@AsOf("1.2.0")
public class UnsafeNMSHandler {

    /**
     * Holds the NMS version instance.
     */
    private static UnsafeNMS NMS_VERSION;

    public UnsafeNMSHandler() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated.");
    }

    // throws UnknownNMSVersionException
    static {
        init();
    }

    /**
     * Initializes the NMS version based on the server's version.
     * The server's version is retrieved from the package name of the server class.
     * The version is then used in a switch statement to instantiate the appropriate NMS version.
     * If the server's version is not supported, a RuntimeException is thrown.
     *
     * @throws UnknownNMSVersionException if the server's version is not supported
     * @since 0.0.1
     */
    @AsOf("1.0.0")
    static void init() {
        if (isNMSLoaded()) return;

        String version = Bukkit.getMinecraftVersion();
        switch (version) {
            case "1.21.11", "1.21.10", "1.21.9" -> NMS_VERSION = new UnsafeNMS_v1_21_11();
            case "26.1.1", "26.1" -> NMS_VERSION = new UnsafeNMS_v26_1_1();
            default -> throw new UnknownNMSVersionException("The version " + version + " is not supported by BiomesAPI. Make sure you are up-to-date with the latest version of BiomesAPI.");
        }
    }

    /**
     * Checks if the NMS version has been loaded.
     *
     * @return true if the NMS version has been loaded, false otherwise
     * @version 0.0.1
     */
    @AsOf("0.0.1")
    public static boolean isNMSLoaded() {
        return NMS_VERSION != null;
    }

    /**
     * Retrieves the NMS version instance.
     *
     * @return an Optional containing the NMS version instance if it exists, an empty Optional otherwise
     * @version 0.0.1
     */
    @AsOf("0.0.1")
    public static Optional<UnsafeNMS> getNMS() {
        return Optional.ofNullable(NMS_VERSION);
    }

    /**
     * Executes the given consumer if the NMS version exists.
     *
     * @param consumer the consumer to execute
     * @version 0.0.2
     */
    @AsOf("0.0.2")
    public static void executeNMS(@NotNull Consumer<UnsafeNMS> consumer) {
        getNMS().ifPresent(consumer);
    }

}
