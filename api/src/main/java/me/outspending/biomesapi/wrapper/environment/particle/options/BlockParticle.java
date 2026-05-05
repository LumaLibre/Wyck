package me.outspending.biomesapi.wrapper.environment.particle.options;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleData;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleOptionsHandle;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleTypeHandle;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

@AsOf("2.0.0")
public record BlockParticle(Material type) implements ParticleData<BlockParticle> {

    @Override
    public @NotNull ParticleOptionsHandle apply(@NotNull ParticleTypeHandle particleType) {
        return ParticleOptionsFactory.WIRE.get().block(particleType, type);
    }

    public static BlockParticle of(Material material) {
        return new BlockParticle(material);
    }
}
