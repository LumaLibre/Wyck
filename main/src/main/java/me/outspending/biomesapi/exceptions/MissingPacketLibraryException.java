package me.outspending.biomesapi.exceptions;

import me.outspending.biomesapi.annotations.AsOf;

/**
 * Exception thrown when the required packet library is missing.
 *
 * @version 0.0.6
 * @since 0.0.6
 * @author Jsinco
 */
@AsOf("0.0.6")
public class MissingPacketLibraryException extends RuntimeException {

    @AsOf("0.0.6")
    public MissingPacketLibraryException(String message, Throwable cause) {
        super(message, cause);
    }
}
