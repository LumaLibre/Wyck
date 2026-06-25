package me.outspending.biomesapi.wrapper.environment.particle.options;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleData;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleOptionsHandle;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleTypeHandle;
import org.jspecify.annotations.NullMarked;

/**
 * Particle data for dust particles.
 *
 * @param color The color of the dust.
 * @param scale The scale of the dust.
 * @since 2.0.0
 * @version 2.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.0.0")
public record DustParticle(int color, float scale) implements ParticleData {

    public static final Codec<DustParticle> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.INT.fieldOf("color").forGetter(DustParticle::color),
        Codec.FLOAT.fieldOf("scale").forGetter(DustParticle::scale)
    ).apply(instance, DustParticle::new));

    @Override
    @AsOf("2.0.0")
    public ParticleOptionsHandle apply(ParticleTypeHandle particleType) {
        return ParticleOptionsFactory.WIRE.get().dust(color, scale);
    }

    @AsOf("2.0.0")
    public static DustParticle of(String hexColor, float scale) {
        return new DustParticle(ParticleData.parseHex(hexColor), scale);
    }

    @AsOf("2.0.0")
    public static DustParticle of(String hexColor) {
        return of(hexColor, 1.0f);
    }

    @Override
    @AsOf("2.4.0")
    public Codec<DustParticle> codec() {
        return CODEC;
    }
}
