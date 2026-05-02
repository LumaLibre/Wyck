package me.outspending.biomesapi.wrapper.environment.particle;

import me.outspending.biomesapi.annotations.AsOf;
import org.jetbrains.annotations.NotNull;

/** Opaque handle to NMS ParticleOptions. Impl module wraps the real thing. */
@AsOf("2.0.0")
public interface ParticleOptionsHandle {

    /**
     * The underlying NMS ParticleOptions value, typed as Object so the API module
     * doesn't reference NMS types; Cast on the consuming side.
     */
    @AsOf("2.0.0")
    @NotNull Object nms();
}
