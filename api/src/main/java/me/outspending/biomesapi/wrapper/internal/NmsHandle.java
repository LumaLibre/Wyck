package me.outspending.biomesapi.wrapper.internal;

import me.outspending.biomesapi.annotations.AsOf;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * A handle to an NMS object.
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public interface NmsHandle {
    /**
     * Convert this handle to the real Minecraft object.
     * @return the real Minecraft object
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    Object toMinecraft();

    @AsOf("2.4.1")
    @SuppressWarnings("unchecked")
    default <T> T asHandle() {
        return (T) this.toMinecraft();
    }

    @AsOf("2.4.0")
    interface Context<C> extends NmsHandle {

        @Override
        @ApiStatus.Internal
        default Object toMinecraft() {
            throw new UnsupportedOperationException("Context handles require a context to convert to Minecraft");
        }

        @AsOf("2.4.0")
        Object toMinecraft(C context);
    }
}
