package me.outspending.biomesapi.wrapper.environment.particle.options;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleData;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleOptionsHandle;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleTypeHandle;
import org.bukkit.Material;
import org.jspecify.annotations.NullMarked;

import static me.outspending.biomesapi.serialization.Codecs.MATERIAL_CODEC;

/**
 * Particle data for particles that require a block type.
 *
 * @param type The block type.
 * @since 2.0.0
 * @version 2.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.0.0")
public record BlockParticle(Material type) implements ParticleData {

    public static final Codec<BlockParticle> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        MATERIAL_CODEC.fieldOf("block").forGetter(BlockParticle::type)
    ).apply(instance, BlockParticle::new));

    @Override
    @AsOf("2.0.0")
    public ParticleOptionsHandle apply(ParticleTypeHandle particleType) {
        return ParticleOptionsFactory.WIRE.get().block(particleType, type);
    }

    @AsOf("2.0.0")
    public static BlockParticle of(Material material) {
        return new BlockParticle(material);
    }

    @Override
    @AsOf("2.4.0")
    public Codec<BlockParticle> codec() {
        return CODEC;
    }
}
