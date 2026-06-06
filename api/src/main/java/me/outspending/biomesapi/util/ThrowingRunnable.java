package me.outspending.biomesapi.util;

import me.outspending.biomesapi.annotations.AsOf;
import org.jspecify.annotations.NullMarked;

/**
 * A runnable that has a checked exception.
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
@FunctionalInterface
public interface ThrowingRunnable {
    @AsOf("2.3.0")
    void run() throws Throwable;
}