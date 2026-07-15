package dev.wyck.environment.particle;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.Wrapper;
import org.jspecify.annotations.NullMarked;

/**
 * Opaque handle to a particle type.
 * Impl module wraps the real thing.
 *
 * @since 2.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.0.0")
public interface ParticleType extends Wrapper {
}
