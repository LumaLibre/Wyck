package me.outspending.biomesapi.wrapper;

import me.outspending.biomesapi.annotations.AsOf;
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
}
