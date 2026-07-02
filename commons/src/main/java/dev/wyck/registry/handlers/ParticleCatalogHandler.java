package dev.wyck.registry.handlers;

import dev.wyck.model.biome.AbstractBiome;
import dev.wyck.registry.internal.BuilderHandler;
import dev.wyck.wrapper.environment.particle.ParticleCatalog;
import dev.wyck.wrapper.environment.particle.ParticleOptionsHandle;
import dev.wyck.wrapper.environment.particle.ResolvedAmbientParticle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.attribute.AmbientParticle;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public class ParticleCatalogHandler implements BuilderHandler<Biome.BiomeBuilder, ParticleCatalog> {

    @Override
    public void handle(@Nullable ParticleCatalog value, Biome.BiomeBuilder key) {
        if (value == null) return;

        List<AmbientParticle> minecraftAmbientParticles = create(value);

        key.setAttribute(EnvironmentAttributes.AMBIENT_PARTICLES, minecraftAmbientParticles);
    }
    
    @Contract("_ -> new")
    public List<net.minecraft.world.attribute.AmbientParticle> create(ParticleCatalog value) {

        return value.resolveParticles()
                .stream()
                .map(ParticleCatalogHandler::toNms)
                .toList();
    }

    @Override
    public @Nullable ParticleCatalog build(AbstractBiome biome) {
        return null;
    }

    private static AmbientParticle toNms(ResolvedAmbientParticle resolved) {
        ParticleOptionsHandle handle = resolved.options();
        return new AmbientParticle((ParticleOptions) handle.nms(), resolved.probability());
    }
}
