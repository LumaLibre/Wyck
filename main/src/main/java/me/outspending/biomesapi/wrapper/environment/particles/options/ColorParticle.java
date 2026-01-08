package me.outspending.biomesapi.wrapper.environment.particles.options;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particles.ParticleData;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import org.jetbrains.annotations.NotNull;

@AsOf("1.1.0")
public record ColorParticle(int color) implements ParticleData<ColorParticleOption> {

    @Override
    public @NotNull ParticleOptions apply(@NotNull ParticleType<@NotNull ColorParticleOption> particleType) {
        return ColorParticleOption.create(particleType, color);
    }

    public static ColorParticle of(@NotNull String hexColor) {
        return new ColorParticle(ParticleData.parseHex(hexColor));
    }
}
