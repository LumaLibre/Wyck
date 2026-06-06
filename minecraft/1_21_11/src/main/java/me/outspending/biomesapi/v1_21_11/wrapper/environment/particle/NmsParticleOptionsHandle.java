package me.outspending.biomesapi.v1_21_11.wrapper.environment.particle;

import me.outspending.biomesapi.wrapper.environment.particle.ParticleOptionsHandle;
import net.minecraft.core.particles.ParticleOptions;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record NmsParticleOptionsHandle(ParticleOptions nms) implements ParticleOptionsHandle {
}