package me.outspending.biomesapi.wrapper.environment.attribute;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
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
public interface EnvironmentAttributeHandle<T> extends NmsHandle {

    Codec<EnvironmentAttributeHandle<?>> CODEC = Codec.stringResolver(
        EnvironmentAttributeHandle::key,
        key -> Preconditions.checkNotNull(EnvironmentAttributeFactory.WIRE.get().byKey(key), "Unknown environment attribute: " + key)
    );

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

    /**
     * The codec used to serialize and deserialize this attribute's value.
     * @return the codec used to serialize and deserialize this attribute's value
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    Codec<T> valueCodec();
}