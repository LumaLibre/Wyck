package me.outspending.biomesapi.wrapper.environment.particle.options;

import me.outspending.biomesapi.api.annotations.AsOf;
import me.outspending.biomesapi.api.wrapper.environment.particle.ParticleData;
import me.outspending.biomesapi.api.wrapper.environment.particle.ParticleOptionsHandle;
import me.outspending.biomesapi.api.wrapper.environment.particle.ParticleTypeHandle;
import me.outspending.biomesapi.api.wrapper.environment.particle.options.ParticleOptionsFactory;
import org.jetbrains.annotations.NotNull;

@AsOf("2.0.0")
public record ColorParticle(int color) implements ParticleData<ColorParticle> {

    @Override
    public @NotNull ParticleOptionsHandle apply(@NotNull ParticleTypeHandle<ColorParticle> particleType) {
        return ParticleOptionsFactory.OPTIONS.get().color(particleType, color);
    }

    public static ColorParticle of(@NotNull String hexColor) {
        return new ColorParticle(ParticleData.parseHex(hexColor));
    }
}
