package dev.wyck.wrapper.internal;

import dev.wyck.annotations.AsOf;

/**
 * Something that can be registered.
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@AsOf("3.0.0")
public interface Registerable<O> {
    @AsOf("3.0.0")
    O register();
}
