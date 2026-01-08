package me.outspending.biomesapi.wrapper.environment.particles.options;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.environment.particles.ParticleData;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@AsOf("1.1.0")
public record ItemParticle(ItemStack bukkitItemStack) implements ParticleData<ItemParticleOption> {
    @Override
    public @NotNull ParticleOptions apply(@NotNull ParticleType<@NotNull ItemParticleOption> particleType) {
        CraftItemStack craftItemStack = CraftItemStack.asCraftCopy(bukkitItemStack);
        net.minecraft.world.item.ItemStack minecraftItemStack = craftItemStack.handle;
        return new ItemParticleOption(particleType, minecraftItemStack);
    }


    public static ItemParticle of(@NotNull ItemStack itemStack) {
        return new ItemParticle(itemStack);
    }

    public static ItemParticle of(@NotNull Material material) {
        return new ItemParticle(ItemStack.of(material));
    }
}
