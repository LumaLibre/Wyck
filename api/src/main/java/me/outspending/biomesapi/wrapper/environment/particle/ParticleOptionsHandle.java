package me.outspending.biomesapi.wrapper.environment.particle;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import org.jspecify.annotations.NullMarked;

/**
 * Opaque handle to NMS ParticleOptions.
 * Impl module wraps the real thing.
 * @version 2.3.0
 * @since 2.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public interface ParticleOptionsHandle extends NmsHandle {

    /**
     * The underlying NMS ParticleOptions value, typed as Object so the API module
     * doesn't reference NMS types; Cast on the consuming side.
     */
    @AsOf("2.0.0")
    Object nms();

    @Override
    @AsOf("2.3.0")
    default Object toMinecraft() {
        return nms();
    }
}
