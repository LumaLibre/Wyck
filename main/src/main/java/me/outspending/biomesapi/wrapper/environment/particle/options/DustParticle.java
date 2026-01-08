package me.outspending.biomesapi.wrapper.environment.particle.options;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleData;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import org.jetbrains.annotations.NotNull;

@AsOf("1.1.0")
public record DustParticle(int color, float scale) implements ParticleData<DustParticleOptions> {

    @Override
    public @NotNull ParticleOptions apply(@NotNull ParticleType<@NotNull DustParticleOptions> particleType) {
        return new DustParticleOptions(color, scale);
    }

    public static DustParticle of(String hexColor, float scale) {
        return new DustParticle(ParticleData.parseHex(hexColor), scale);
    }

    public static DustParticle of(String hexColor) {
        return of(hexColor, 1.0f);
    }
}
