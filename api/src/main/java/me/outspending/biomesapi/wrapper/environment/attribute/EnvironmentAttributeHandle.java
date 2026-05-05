package me.outspending.biomesapi.wrapper.environment.attribute;

import me.outspending.biomesapi.annotations.AsOf;

/**
 * Opaque handle to an NMS EnvironmentAttribute<T>.
 * @param <T> the type of the attribute value
 * @version 2.1.0
 * @since 2.0.0
 * @author Jsinco
 */
@AsOf("2.1.0")
public interface EnvironmentAttributeHandle<T> {

    /** The default value of this attribute, as specified by NMS. */
    @AsOf("2.1.0")
    T defaultValue();

}