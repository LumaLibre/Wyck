package me.outspending.biomesapi.registry.handlers;

import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.registry.BuilderHandler;
import me.outspending.biomesapi.renderer.ParticleRenderer;
import net.minecraft.world.attribute.AmbientParticle;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ParticleRendererHandler implements BuilderHandler<Biome.BiomeBuilder, ParticleRenderer> {

    @Override
    public void handle(ParticleRenderer value, @NotNull Biome.BiomeBuilder key) {
        if (value == null) return;

        List<AmbientParticle> ambientParticles = AmbientParticle.of(
                value.ambientParticle().getSimpleParticle(),
                value.probability()
        );
        key.setAttribute(EnvironmentAttributes.AMBIENT_PARTICLES, ambientParticles);
    }

    @Override
    public ParticleRenderer build(@NotNull CustomBiome biome) {
        return null;
    }
}
