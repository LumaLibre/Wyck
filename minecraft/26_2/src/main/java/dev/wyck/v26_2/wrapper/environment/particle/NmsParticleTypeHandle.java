package dev.wyck.v26_2.wrapper.environment.particle;

import dev.wyck.wrapper.environment.particle.ParticleTypeHandle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record NmsParticleTypeHandle<T extends ParticleOptions>(ParticleType<T> nms) implements ParticleTypeHandle {
}