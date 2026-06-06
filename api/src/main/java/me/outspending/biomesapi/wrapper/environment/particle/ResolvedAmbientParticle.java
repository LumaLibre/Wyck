package me.outspending.biomesapi.wrapper.environment.particle;

import me.outspending.biomesapi.annotations.AsOf;
import org.jspecify.annotations.NullMarked;

/**
 * A particle options handle paired with its spawn probability. NMS-side code
 * unwraps the handle to produce the version-specific ambient-particle type.
 *
 * @since 2.0.0
 * @version 2.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.0.0")
public record ResolvedAmbientParticle(ParticleOptionsHandle options, float probability) {}