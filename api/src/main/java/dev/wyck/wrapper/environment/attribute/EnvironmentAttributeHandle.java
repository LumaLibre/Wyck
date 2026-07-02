package dev.wyck.wrapper.environment.attribute;

import dev.wyck.annotations.AsOf;
import org.jspecify.annotations.NullMarked;

/**
 * Opaque handle to an NMS EnvironmentAttribute<T>.
 * @param <T> the type of the attribute value
 * @version 2.1.0
 * @since 2.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.1.0")
public interface EnvironmentAttributeHandle<T> {

    /**
     * The default value of this attribute, as specified by Minecraft.
     * @return the default value of this attribute
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    T defaultValue();

    /**
     * Whether the client reacts to this attribute or not.
     * @return whether the client reacts to this attribute or not
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    boolean isClientAware();

    /**
     * The id of this attribute.
     * @return the id of this attribute
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    String key();

}