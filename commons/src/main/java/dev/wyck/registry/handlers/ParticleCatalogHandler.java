package dev.wyck.registry.handlers;

import dev.wyck.model.biome.AbstractBiome;
import dev.wyck.registry.internal.BuilderHandler;
import dev.wyck.wrapper.environment.particle.AmbientParticle;
import dev.wyck.wrapper.environment.particle.ParticleCatalog;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public class ParticleCatalogHandler implements BuilderHandler<net.minecraft.world.level.biome.Biome.BiomeBuilder, ParticleCatalog> {

    @Override
    public void handle(@Nullable ParticleCatalog value, net.minecraft.world.level.biome.Biome.BiomeBuilder key) {
        if (value == null) return;

        List<net.minecraft.world.attribute.AmbientParticle> minecraftAmbientParticles = create(value);

        key.setAttribute(net.minecraft.world.attribute.EnvironmentAttributes.AMBIENT_PARTICLES, minecraftAmbientParticles);
    }
    
    @Contract("_ -> new")
    public List<net.minecraft.world.attribute.AmbientParticle> create(ParticleCatalog value) {

        return value.particles().stream()
                .map(AmbientParticle::<net.minecraft.world.attribute.AmbientParticle>asHandle)
                .toList();
    }

    @Override
    public @Nullable ParticleCatalog build(AbstractBiome biome) {
        return null;
    }

}
