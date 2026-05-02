package me.outspending.biomesapi.wrapper.environment.particle;

/**
 * A particle options handle paired with its spawn probability. NMS-side code
 * unwraps the handle to produce the version-specific ambient-particle type.
 */
public record ResolvedAmbientParticle(ParticleOptionsHandle options, float probability) {}