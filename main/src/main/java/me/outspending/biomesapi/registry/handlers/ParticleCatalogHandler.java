package me.outspending.biomesapi.registry.handlers;

import me.outspending.biomesapi.biome.CustomBiome;
import me.outspending.biomesapi.registry.BuilderHandler;
import me.outspending.biomesapi.wrapper.environment.particles.ParticleCatalog;
import net.minecraft.world.attribute.AmbientParticle;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ParticleCatalogHandler implements BuilderHandler<Biome.BiomeBuilder, ParticleCatalog> {

    @Override
    public void handle(ParticleCatalog value, @NotNull Biome.BiomeBuilder key) {
        if (value == null) return;

        List<AmbientParticle> minecraftAmbientParticles = create(value);

        key.setAttribute(EnvironmentAttributes.AMBIENT_PARTICLES, minecraftAmbientParticles);
    }

    @Contract("null -> null")
    public List<net.minecraft.world.attribute.AmbientParticle> create(ParticleCatalog value) {
        if (value == null) return null;

        return value.delegateParticles();
    }

    @Override
    public @Nullable ParticleCatalog build(@NotNull CustomBiome biome) {
        return null;
    }
}
