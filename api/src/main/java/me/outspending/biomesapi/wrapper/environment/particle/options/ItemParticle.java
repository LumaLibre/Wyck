package me.outspending.biomesapi.wrapper.environment.particle.options;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleData;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleOptionsHandle;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleTypeHandle;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NullMarked;

import static me.outspending.biomesapi.serialization.Codecs.ITEM_STACK_CODEC;

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

    public static final Codec<ItemParticle> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        ITEM_STACK_CODEC.fieldOf("itemStack").forGetter(ItemParticle::itemStack)
    ).apply(instance, ItemParticle::new));

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

    @Override
    @AsOf("2.4.0")
    public Codec<ItemParticle> codec() {
        return CODEC;
    }
}
