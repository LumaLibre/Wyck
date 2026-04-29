package me.outspending.biomesapi.wrapper.environment.particle.options;

import me.outspending.biomesapi.api.annotations.AsOf;
import me.outspending.biomesapi.api.wrapper.environment.particle.ParticleData;
import me.outspending.biomesapi.api.wrapper.environment.particle.ParticleOptionsHandle;
import me.outspending.biomesapi.api.wrapper.environment.particle.ParticleTypeHandle;
import me.outspending.biomesapi.api.wrapper.environment.particle.options.ParticleOptionsFactory;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.TrailParticleOption;
import org.bukkit.Location;
import org.bukkit.craftbukkit.util.CraftLocation;
import org.jetbrains.annotations.NotNull;

@AsOf("2.0.0")
public record TrailParticle(Location target, int color, int duration) implements ParticleData<TrailParticle> {

    @Override
    public @NotNull ParticleOptionsHandle apply(@NotNull ParticleTypeHandle<TrailParticle> particleType) {
        return ParticleOptionsFactory.OPTIONS.get().trail(target, color, duration);
    }

    public static TrailParticle of(@NotNull Location target, @NotNull String hexColor, int duration) {
        return new TrailParticle(target, ParticleData.parseHex(hexColor), duration);
    }
}