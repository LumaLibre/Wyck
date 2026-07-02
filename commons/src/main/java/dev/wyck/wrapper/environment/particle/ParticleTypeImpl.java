package dev.wyck.wrapper.environment.particle;

import net.minecraft.core.particles.ParticleOptions;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record ParticleTypeImpl<T extends ParticleOptions>(@Override net.minecraft.core.particles.ParticleType<T> nms) implements ParticleType {
    @Override
    public Object toMinecraft() {
        return this.nms;
    }
}