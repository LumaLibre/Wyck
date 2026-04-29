package me.outspending.biomesapi.wrapper.environment.particle;

import me.outspending.biomesapi.api.wrapper.environment.particle.ParticleOptionsHandle;
import net.minecraft.core.particles.ParticleOptions;

public record NmsParticleOptionsHandle(ParticleOptions nms) implements ParticleOptionsHandle {

}