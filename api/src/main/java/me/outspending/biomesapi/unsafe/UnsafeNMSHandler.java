package me.outspending.biomesapi.unsafe;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Unsafe NMS versions handler. NMS versions may be variable.
 *
 * @version 1.2.0
 * @since 0.0.1
 * @author Outspending
 */
@NullMarked
@AsOf("1.2.0")
public class UnsafeNMSHandler {

    private static final WireProvider<UnsafeNMS> WIRE = WireProvider.create("me.outspending.biomesapi.*.UnsafeNMS");

    private UnsafeNMSHandler() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated.");
    }

    /**
     * Checks if the NMS version has been loaded.
     *
     * @return true if the NMS version has been loaded, false otherwise
     */
    @AsOf("0.0.1")
    public static boolean isNMSLoaded() {
        return WIRE.isRegistered();
    }

    /**
     * Retrieves the NMS version instance.
     *
     * @return an Optional containing the NMS version instance if it exists, an empty Optional otherwise
     */
    @AsOf("0.0.1")
    public static Optional<UnsafeNMS> getNMS() {
        return Optional.of(WIRE.get());
    }

    /**
     * Executes the given consumer if the NMS version exists.
     *
     * @param consumer the consumer to execute
     */
    @AsOf("0.0.2")
    public static void executeNMS(Consumer<UnsafeNMS> consumer) {
        getNMS().ifPresent(consumer);
    }

}
