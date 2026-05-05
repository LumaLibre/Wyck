package me.outspending.biomesapi.wrapper.environment.particle.options;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleData;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleOptionsHandle;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleTypeHandle;
import org.jetbrains.annotations.NotNull;

@AsOf("2.0.0")
public record SpellParticle(int color, float power) implements ParticleData<SpellParticle> {

    @Override
    public @NotNull ParticleOptionsHandle apply(@NotNull ParticleTypeHandle particleType) {
        return ParticleOptionsFactory.WIRE.get().spell(particleType, color, power);
    }

    public static SpellParticle of(@NotNull String hexColor, float power) {
        return new SpellParticle(ParticleData.parseHex(hexColor), power);
    }

    public static SpellParticle of(@NotNull String hexColor) {
        return of(hexColor, 1.0f);
    }
}
