package me.outspending.biomesapi.wrapper.internal;

import me.outspending.biomesapi.annotations.AsOf;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Decodes a real Minecraft object back into its wrapper handle.
 * @since 2.4.0
 * @version 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
@ApiStatus.Internal
public interface NmsDecoder<H extends NmsHandle> {
    @AsOf("2.4.0")
    H fromMinecraft(Object nms);
}