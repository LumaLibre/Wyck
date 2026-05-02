package me.outspending.biomesapi.v26_1.wrapper.environment.particle;

import me.outspending.biomesapi.wrapper.environment.particle.ParticleTypeHandle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import org.jetbrains.annotations.NotNull;

public record NmsParticleTypeHandle<T extends ParticleOptions>(ParticleType<@NotNull T> nms) implements ParticleTypeHandle<T> {

}