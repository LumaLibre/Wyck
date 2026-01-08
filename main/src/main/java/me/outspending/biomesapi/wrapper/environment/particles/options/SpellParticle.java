package me.outspending.biomesapi.wrapper.environment.particles.options;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particles.ParticleData;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SpellParticleOption;
import org.jetbrains.annotations.NotNull;

@AsOf("1.1.0")
public record SpellParticle(int color, float power) implements ParticleData<SpellParticleOption> {
    @Override
    public @NotNull ParticleOptions apply(@NotNull ParticleType<@NotNull SpellParticleOption> particleType) {
        return SpellParticleOption.create(particleType, color, power);
    }


    public static SpellParticle of(@NotNull String hexColor, float power) {
        return new SpellParticle(ParticleData.parseHex(hexColor), power);
    }

    public static SpellParticle of(@NotNull String hexColor) {
        return of(hexColor, 1.0f);
    }
}
