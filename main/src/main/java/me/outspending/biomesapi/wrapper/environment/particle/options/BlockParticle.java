package me.outspending.biomesapi.wrapper.environment.particle.options;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleData;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.Material;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.jetbrains.annotations.NotNull;

@AsOf("1.1.0")
public record BlockParticle(Material type) implements ParticleData<BlockParticleOption> {
    @Override
    public @NotNull ParticleOptions apply(@NotNull ParticleType<@NotNull BlockParticleOption> particleType) {
        BlockState minecraftBlockState = CraftMagicNumbers.getBlock(type).defaultBlockState();
        return new BlockParticleOption(particleType, minecraftBlockState);
    }

    public static BlockParticle of(Material material) {
        return new BlockParticle(material);
    }
}
