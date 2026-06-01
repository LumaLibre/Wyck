package me.outspending.biomesapi.wrapper.environment.particle;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.NmsHandle;
import org.jetbrains.annotations.NotNull;

/**
 * Opaque handle to NMS ParticleOptions.
 * Impl module wraps the real thing.
 * @version 2.3.0
 * @since 2.0.0
 * @author Jsinco
 */
@AsOf("2.3.0")
public interface ParticleOptionsHandle extends NmsHandle {

    /**
     * The underlying NMS ParticleOptions value, typed as Object so the API module
     * doesn't reference NMS types; Cast on the consuming side.
     */
    @AsOf("2.0.0")
    @NotNull Object nms();

    @Override
    @AsOf("2.3.0")
    default @NotNull Object toMinecraft() {
        return nms();
    }
}
