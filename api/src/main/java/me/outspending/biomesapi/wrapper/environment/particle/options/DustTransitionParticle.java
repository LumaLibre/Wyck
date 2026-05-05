package me.outspending.biomesapi.wrapper.environment.particle.options;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleData;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleOptionsHandle;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleTypeHandle;
import org.jetbrains.annotations.NotNull;

@AsOf("2.0.0")
public record DustTransitionParticle(int fromColor, int toColor, float scale) implements ParticleData<DustTransitionParticle> {

    @Override
    public @NotNull ParticleOptionsHandle apply(@NotNull ParticleTypeHandle particleType) {
        return ParticleOptionsFactory.WIRE.get().dustTransition(fromColor, toColor, scale);
    }

    public static DustTransitionParticle of(String fromHexColor, String toHexColor, float scale) {
        return new DustTransitionParticle(ParticleData.parseHex(fromHexColor), ParticleData.parseHex(toHexColor), scale);
    }

    public static DustTransitionParticle of(String fromHexColor, String toHexColor) {
        return of(fromHexColor, toHexColor, 1.0f);
    }
}
