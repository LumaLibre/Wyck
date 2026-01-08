package me.outspending.biomesapi.wrapper.environment.particles.options;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particles.ParticleData;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import org.jetbrains.annotations.NotNull;

@AsOf("1.1.0")
public record DustTransitionParticle(int fromColor, int toColor, float scale) implements ParticleData<DustColorTransitionOptions> {
    @Override
    public @NotNull ParticleOptions apply(@NotNull ParticleType<@NotNull DustColorTransitionOptions> particleType) {
        return new DustColorTransitionOptions(fromColor, toColor, scale);
    }

    public static DustTransitionParticle of(String fromHexColor, String toHexColor, float scale) {
        return new DustTransitionParticle(ParticleData.parseHex(fromHexColor), ParticleData.parseHex(toHexColor), scale);
    }

    public static DustTransitionParticle of(String fromHexColor, String toHexColor) {
        return of(fromHexColor, toHexColor, 1.0f);
    }
}
