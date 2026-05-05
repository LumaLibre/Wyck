package me.outspending.biomesapi.wrapper.environment.particle.options;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleData;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleOptionsHandle;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleTypeHandle;
import org.jetbrains.annotations.NotNull;

@AsOf("2.0.0")
public record DustParticle(int color, float scale) implements ParticleData<DustParticle> {

    @Override
    public @NotNull ParticleOptionsHandle apply(@NotNull ParticleTypeHandle particleType) {
        return ParticleOptionsFactory.WIRE.get().dust(color, scale);
    }

    public static DustParticle of(String hexColor, float scale) {
        return new DustParticle(ParticleData.parseHex(hexColor), scale);
    }

    public static DustParticle of(String hexColor) {
        return of(hexColor, 1.0f);
    }
}
