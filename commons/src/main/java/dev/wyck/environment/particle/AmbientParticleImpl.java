package dev.wyck.environment.particle;

import com.google.common.base.Preconditions;
import dev.wyck.environment.particle.AmbientParticle;
import dev.wyck.environment.particle.ParticleData;
import dev.wyck.environment.particle.ParticleOptions;
import dev.wyck.environment.particle.ParticleTypes;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
@ApiStatus.Internal
public record AmbientParticleImpl(@Override ParticleTypes type, @Override float probability, @Override @Nullable ParticleData particleData) implements AmbientParticle {

    public AmbientParticleImpl {
        Preconditions.checkArgument(type.isSimple() == (particleData == null), "Simple particles must not have particle data; complex particles must have particle data.");
    }

    public AmbientParticleImpl(ParticleTypes ambientParticle, float probability) {
        this(ambientParticle, probability, null);
    }

    @Override
    public Object toMinecraft() {
        ParticleOptions particleOptions = this.particleOptions();
        return new net.minecraft.world.attribute.AmbientParticle(particleOptions.asHandle(), this.probability);
    }
}
