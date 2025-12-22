package me.outspending.biomesapi.exceptions;

import me.outspending.biomesapi.annotations.AsOf;

/**
 * Exception thrown when the required packet library is missing.
 *
 * @version 0.0.4
 * @since 0.0.4
 * @author Jsinco
 */
@AsOf("0.0.4")
public class MissingPacketLibraryException extends RuntimeException {

    @AsOf("0.0.4")
    public MissingPacketLibraryException(String message, Throwable cause) {
        super(message, cause);
    }
}
