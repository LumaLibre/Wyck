package me.outspending.biomesapi.registry.handlers;

import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.registry.BuilderHandler;
import me.outspending.biomesapi.renderer.ParticleRenderer;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ParticleRendererHandler implements BuilderHandler<Biome.BiomeBuilder, ParticleRenderer> {

    @Override
    public void handle(ParticleRenderer value, @NotNull Biome.BiomeBuilder key) {
        if (value == null) return;

        List<net.minecraft.world.attribute.AmbientParticle> minecraftAmbientParticles = create(value);

        key.setAttribute(EnvironmentAttributes.AMBIENT_PARTICLES, minecraftAmbientParticles);
    }

    @Contract("null -> null")
    public List<net.minecraft.world.attribute.AmbientParticle> create(ParticleRenderer value) {
        if (value == null) return null;

        List<net.minecraft.world.attribute.AmbientParticle> minecraftAmbientParticles = new ArrayList<>();

        for (var entry : value.ambientParticles().entrySet()) {
            minecraftAmbientParticles.add(
                    new net.minecraft.world.attribute.AmbientParticle(entry.getKey().getSimpleParticle(), entry.getValue())
            );
        }

        return minecraftAmbientParticles;
    }

    @Override
    public ParticleRenderer build(@NotNull CustomBiome biome) {
        return null;
    }
}
