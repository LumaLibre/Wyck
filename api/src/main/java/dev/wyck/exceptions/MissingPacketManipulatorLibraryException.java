package dev.wyck.exceptions;

import dev.wyck.annotations.AsOf;

/**
 * Exception thrown when the required packet library is missing.
 *
 * @version 0.0.19
 * @since 0.0.6
 * @author Jsinco
 */
@AsOf("0.0.6")
public class MissingPacketManipulatorLibraryException extends RuntimeException {

    @AsOf("0.0.19")
    public MissingPacketManipulatorLibraryException(String message) {
        super(message);
    }

    @AsOf("0.0.6")
    public MissingPacketManipulatorLibraryException(String message, Throwable cause) {
        super(message, cause);
    }
}
