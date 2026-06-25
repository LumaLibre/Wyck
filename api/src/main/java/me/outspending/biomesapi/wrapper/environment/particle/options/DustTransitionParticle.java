package me.outspending.biomesapi.wrapper.environment.particle.options;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleData;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleOptionsHandle;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleTypeHandle;
import org.jspecify.annotations.NullMarked;

/**
 * Particle data for dust transition particles.
 * 
 * @param fromColor The color of the dust.
 * @param toColor The color of the dust.
 * @param scale The scale of the dust.
 * @since 2.0.0
 * @version 2.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.0.0")
public record DustTransitionParticle(int fromColor, int toColor, float scale) implements ParticleData {

    public static final Codec<DustTransitionParticle> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.INT.fieldOf("fromColor").forGetter(DustTransitionParticle::fromColor),
        Codec.INT.fieldOf("toColor").forGetter(DustTransitionParticle::toColor),
        Codec.FLOAT.fieldOf("scale").forGetter(DustTransitionParticle::scale)
    ).apply(instance, DustTransitionParticle::new));

    @Override
    @AsOf("2.0.0")
    public ParticleOptionsHandle apply(ParticleTypeHandle particleType) {
        return ParticleOptionsFactory.WIRE.get().dustTransition(fromColor, toColor, scale);
    }

    @AsOf("2.0.0")
    public static DustTransitionParticle of(String fromHexColor, String toHexColor, float scale) {
        return new DustTransitionParticle(ParticleData.parseHex(fromHexColor), ParticleData.parseHex(toHexColor), scale);
    }

    @AsOf("2.0.0")
    public static DustTransitionParticle of(String fromHexColor, String toHexColor) {
        return of(fromHexColor, toHexColor, 1.0f);
    }

    @Override
    @AsOf("2.4.0")
    public Codec<DustTransitionParticle> codec() {
        return CODEC;
    }
}
