package me.outspending.biomesapi.wrapper.environment.particle.options;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleData;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleOptionsHandle;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleTypeHandle;
import org.bukkit.Location;
import org.jspecify.annotations.NullMarked;

import static me.outspending.biomesapi.serialization.Codecs.LOCATION_CODEC;

/**
 * Particle data for trail particles.
 *
 * @param target The target location of the trail.
 * @param color The color of the trail.
 * @param duration The duration of the trail.
 * @since 2.0.0
 * @version 2.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.0.0")
public record TrailParticle(Location target, int color, int duration) implements ParticleData {

    public static final Codec<TrailParticle> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        LOCATION_CODEC.fieldOf("target").forGetter(TrailParticle::target),
        Codec.INT.fieldOf("color").forGetter(TrailParticle::color),
        Codec.INT.fieldOf("duration").forGetter(TrailParticle::duration
    )).apply(instance, TrailParticle::new));

    @Override
    @AsOf("2.0.0")
    public ParticleOptionsHandle apply(ParticleTypeHandle particleType) {
        return ParticleOptionsFactory.WIRE.get().trail(target, color, duration);
    }

    @AsOf("2.0.0")
    public static TrailParticle of(Location target, String hexColor, int duration) {
        return new TrailParticle(target, ParticleData.parseHex(hexColor), duration);
    }

    @Override
    @AsOf("2.4.0")
    public Codec<TrailParticle> codec() {
        return CODEC;
    }
}