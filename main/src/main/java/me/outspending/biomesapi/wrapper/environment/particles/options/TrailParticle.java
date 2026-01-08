package me.outspending.biomesapi.wrapper.environment.particles.options;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particles.ParticleData;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.TrailParticleOption;
import org.bukkit.Location;
import org.bukkit.craftbukkit.util.CraftLocation;
import org.jetbrains.annotations.NotNull;

@AsOf("1.1.0")
public record TrailParticle(Location target, int color, int duration) implements ParticleData<TrailParticleOption> {
    @Override
    public @NotNull ParticleOptions apply(@NotNull ParticleType<@NotNull TrailParticleOption> particleType) {
        return new TrailParticleOption(CraftLocation.toVec3(target), color, duration);
    }

    public static TrailParticle of(@NotNull Location target, @NotNull String hexColor, int duration) {
        return new TrailParticle(target, ParticleData.parseHex(hexColor), duration);
    }
}
