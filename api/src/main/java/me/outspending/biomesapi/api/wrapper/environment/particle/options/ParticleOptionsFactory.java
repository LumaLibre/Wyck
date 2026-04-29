package me.outspending.biomesapi.api.wrapper.environment.particle.options;

import me.outspending.biomesapi.api.factory.WireProvider;
import me.outspending.biomesapi.api.wrapper.environment.particle.ParticleOptionsHandle;
import me.outspending.biomesapi.api.wrapper.environment.particle.ParticleTypeHandle;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Vibration;
import org.bukkit.inventory.ItemStack;

public interface ParticleOptionsFactory {

    WireProvider<ParticleOptionsFactory> OPTIONS = WireProvider.create("ParticleOptionsFactory");

    /**
     * Creates a particle options handle for a block particle with the specified material.
     * @param type The particle type handle corresponding to a block particle type.
     * @param material The material to use for the block particle effect.
     * @return A ParticleOptionsHandle configured for the specified block particle type and material.
     */
    ParticleOptionsHandle block(ParticleTypeHandle<?> type, Material material);

    // TODO: javadoc
    ParticleOptionsHandle color(ParticleTypeHandle<?> type, int rgb);

    // TODO: javadoc
    ParticleOptionsHandle dust(int rgb, float size);

    // TODO: javadoc
    ParticleOptionsHandle dustTransition(int fromRgb, int toRgb, float size);

    // TODO: javadoc
    ParticleOptionsHandle item(ParticleTypeHandle<?> type, ItemStack itemStack);

    // TODO: javadoc
    ParticleOptionsHandle power(ParticleTypeHandle<?> type, float power);

    // TODO: javadoc
    ParticleOptionsHandle sculkCharge(float roll);

    // TODO: javadoc
    ParticleOptionsHandle simple(ParticleTypeHandle<?> type);

    // TODO: javadoc
    ParticleOptionsHandle spell(ParticleTypeHandle<?> type, int rgb, float power);

    // TODO: javadoc
    ParticleOptionsHandle trail(Location target, int rgb, int duration);

    // TODO: javadoc
    ParticleOptionsHandle vibration(Vibration.Destination destination, int arrivalInTicks);


    // TODO: javadoc
    <T> ParticleTypeHandle<T> typeByKey(String key);
}