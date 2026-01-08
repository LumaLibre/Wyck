package me.outspending.biomesapi.wrapper.environment.particles.options;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particles.ParticleData;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.VibrationParticleOption;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.PositionSource;
import org.bukkit.Location;
import org.bukkit.Vibration;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.util.CraftLocation;
import org.jetbrains.annotations.NotNull;

@AsOf("1.1.0")
public record VibrationParticle(Vibration.Destination destination, int arrivalInTicks) implements ParticleData<VibrationParticleOption> {

    @Override
    public @NotNull ParticleOptions apply(@NotNull ParticleType<@NotNull VibrationParticleOption> particleType) {
        PositionSource source = toNMSPositionSource(destination);
        return new VibrationParticleOption(source, arrivalInTicks);
    }

    private @NotNull PositionSource toNMSPositionSource(Vibration.Destination dest) {
        // This code was ripped from Bukkit
        PositionSource source;
        if (dest instanceof Vibration.Destination.BlockDestination) {
            Location destination = ((Vibration.Destination.BlockDestination) dest).getLocation();
            source = new BlockPositionSource(CraftLocation.toBlockPosition(destination));
        } else if (dest instanceof Vibration.Destination.EntityDestination) {
            Entity destination = ((CraftEntity) ((Vibration.Destination.EntityDestination) dest).getEntity()).getHandle();
            source = new EntityPositionSource(destination, destination.getEyeHeight());
        } else {
            throw new IllegalArgumentException("Unknown vibration destination " + dest);
        }
        return source;
    }

    public static VibrationParticle of(@NotNull Vibration.Destination destination, int arrivalInTicks) {
        return new VibrationParticle(destination, arrivalInTicks);
    }
}
