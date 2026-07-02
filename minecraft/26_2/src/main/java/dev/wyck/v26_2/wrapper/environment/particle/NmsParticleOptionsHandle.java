package dev.wyck.v26_2.wrapper.environment.particle;

import dev.wyck.wrapper.environment.particle.ParticleOptionsHandle;
import net.minecraft.core.particles.ParticleOptions;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record NmsParticleOptionsHandle(ParticleOptions nms) implements ParticleOptionsHandle {
}