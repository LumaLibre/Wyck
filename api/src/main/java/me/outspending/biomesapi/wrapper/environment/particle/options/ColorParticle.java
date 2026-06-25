package me.outspending.biomesapi.wrapper.environment.particle.options;

import com.mojang.serialization.Codec;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleData;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleOptionsHandle;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleTypeHandle;
import org.jspecify.annotations.NullMarked;

/**
 * Particle data for particles that require a color.
 *
 * @param color The color of the particle.
 * @since 2.0.0
 * @version 2.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.0.0")
public record ColorParticle(int color) implements ParticleData {

    public static final Codec<ColorParticle> CODEC = Codec.INT.xmap(ColorParticle::new, ColorParticle::color);

    @Override
    @AsOf("2.0.0")
    public ParticleOptionsHandle apply(ParticleTypeHandle particleType) {
        return ParticleOptionsFactory.WIRE.get().color(particleType, color);
    }

    @AsOf("2.0.0")
    public static ColorParticle of(String hexColor) {
        return new ColorParticle(ParticleData.parseHex(hexColor));
    }

    @Override
    @AsOf("2.4.0")
    public Codec<ColorParticle> codec() {
        return CODEC;
    }
}
