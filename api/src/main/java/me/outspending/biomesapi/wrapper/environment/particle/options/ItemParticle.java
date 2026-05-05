package me.outspending.biomesapi.wrapper.environment.particle.options;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleData;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleOptionsHandle;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleTypeHandle;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@AsOf("2.0.0")
public record ItemParticle(ItemStack bukkitItemStack) implements ParticleData<ItemParticle> {

    @Override
    public @NotNull ParticleOptionsHandle apply(@NotNull ParticleTypeHandle particleType) {
        return ParticleOptionsFactory.WIRE.get().item(particleType, bukkitItemStack);
    }

    public static ItemParticle of(@NotNull ItemStack itemStack) {
        return new ItemParticle(itemStack);
    }

    public static ItemParticle of(@NotNull Material material) {
        return new ItemParticle(ItemStack.of(material));
    }
}
