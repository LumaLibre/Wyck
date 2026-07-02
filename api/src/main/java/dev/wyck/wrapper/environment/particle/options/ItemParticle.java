package dev.wyck.wrapper.environment.particle.options;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.environment.particle.ParticleData;
import dev.wyck.wrapper.environment.particle.ParticleOptionsHandle;
import dev.wyck.wrapper.environment.particle.ParticleTypeHandle;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NullMarked;

/**
 * Particle data for item particles.
 * 
 * @param itemStack The item stack to display in the particle.
 * @since 2.0.0
 * @version 2.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.0.0")
public record ItemParticle(ItemStack itemStack) implements ParticleData {

    @Override
    @AsOf("2.0.0")
    public ParticleOptionsHandle apply(ParticleTypeHandle particleType) {
        return ParticleOptionsFactory.WIRE.get().item(particleType, itemStack);
    }

    @AsOf("2.0.0")
    public static ItemParticle of(ItemStack itemStack) {
        return new ItemParticle(itemStack);
    }

    @AsOf("2.0.0")
    public static ItemParticle of(Material material) {
        return new ItemParticle(ItemStack.of(material));
    }
}
