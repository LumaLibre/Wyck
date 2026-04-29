package me.outspending.biomesapi;

import me.outspending.biomesapi.api.ChunkLocation;
import me.outspending.biomesapi.api.wrapper.environment.particle.options.ParticleOptionsFactory;
import me.outspending.biomesapi.wrapper.environment.particle.options.NmsParticleOptionsFactory;

public class Provider_v1_21_11 {

    public static void init() {
        ChunkLocation.FACTORY.set(ChunkLocationImpl::new);
        ParticleOptionsFactory.OPTIONS.set(new NmsParticleOptionsFactory());
    }
}
