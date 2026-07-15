package dev.wyck.environment.particle;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record ParticleOptionsImpl(net.minecraft.core.particles.ParticleOptions nms) implements ParticleOptions {
    @Override
    public Object toMinecraft() {
        return this.nms;
    }
}