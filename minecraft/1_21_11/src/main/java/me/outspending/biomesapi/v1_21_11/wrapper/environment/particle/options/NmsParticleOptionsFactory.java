package me.outspending.biomesapi.v1_21_11.wrapper.environment.particle.options;

import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.v1_21_11.wrapper.environment.particle.NmsParticleOptionsHandle;
import me.outspending.biomesapi.v1_21_11.wrapper.environment.particle.NmsParticleTypeHandle;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleOptionsHandle;
import me.outspending.biomesapi.wrapper.environment.particle.ParticleTypeHandle;
import me.outspending.biomesapi.wrapper.environment.particle.options.ParticleOptionsFactory;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.PowerParticleOption;
import net.minecraft.core.particles.SculkChargeParticleOptions;
import net.minecraft.core.particles.SpellParticleOption;
import net.minecraft.core.particles.TrailParticleOption;
import net.minecraft.core.particles.VibrationParticleOption;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.PositionSource;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Vibration;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.craftbukkit.util.CraftLocation;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@ApiStatus.Internal
@SuppressWarnings("unchecked")
public final class NmsParticleOptionsFactory implements ParticleOptionsFactory {

    @Override
    public ParticleOptionsHandle block(ParticleTypeHandle type, Material material) {
        ParticleType<BlockParticleOption> nmsType = (ParticleType<BlockParticleOption>) ((NmsParticleTypeHandle<?>) type).nms();
        BlockState blockState = CraftMagicNumbers.getBlock(material).defaultBlockState();
        return new NmsParticleOptionsHandle(new BlockParticleOption(nmsType, blockState));
    }

    @Override
    public ParticleOptionsHandle color(ParticleTypeHandle type, int rgb) {
        ParticleType<ColorParticleOption> nmsType = (ParticleType<ColorParticleOption>) ((NmsParticleTypeHandle<?>) type).nms();
        return new NmsParticleOptionsHandle(ColorParticleOption.create(nmsType, rgb));
    }

    @Override
    public ParticleOptionsHandle dust(int rgb, float size) {
        return new NmsParticleOptionsHandle(new DustParticleOptions(rgb, size));
    }

    @Override
    public ParticleOptionsHandle dustTransition(int fromRgb, int toRgb, float size) {
        return new NmsParticleOptionsHandle(new DustColorTransitionOptions(fromRgb, toRgb, size));
    }

    @Override
    public ParticleOptionsHandle item(ParticleTypeHandle type, ItemStack bukkitItemStack) {
        ParticleType<ItemParticleOption> nmsType = (ParticleType<ItemParticleOption>) ((NmsParticleTypeHandle<?>) type).nms();
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asCraftCopy(bukkitItemStack).handle;
        return new NmsParticleOptionsHandle(new ItemParticleOption(nmsType, nmsStack));
    }

    @Override
    public ParticleOptionsHandle power(ParticleTypeHandle type, float power) {
        ParticleType<PowerParticleOption> nmsType = (ParticleType<PowerParticleOption>) ((NmsParticleTypeHandle<?>) type).nms();
        return new NmsParticleOptionsHandle(PowerParticleOption.create(nmsType, power));
    }

    @Override
    public ParticleOptionsHandle sculkCharge(float roll) {
        return new NmsParticleOptionsHandle(new SculkChargeParticleOptions(roll));
    }


    @Override
    public ParticleOptionsHandle simple(ParticleTypeHandle type) {
        ParticleType<?> nms = ((NmsParticleTypeHandle<?>) type).nms();
        return new NmsParticleOptionsHandle((ParticleOptions) nms);
    }

    @Override
    public ParticleOptionsHandle spell(ParticleTypeHandle type, int rgb, float power) {
        ParticleType<SpellParticleOption> nmsType = (ParticleType<SpellParticleOption>) ((NmsParticleTypeHandle<?>) type).nms();
        return new NmsParticleOptionsHandle(SpellParticleOption.create(nmsType, rgb, power));
    }

    @Override
    public ParticleOptionsHandle trail(Location target, int rgb, int duration) {
        return new NmsParticleOptionsHandle(new TrailParticleOption(CraftLocation.toVec3(target), rgb, duration));
    }


    @Override
    public ParticleOptionsHandle vibration(Vibration.Destination destination, int arrivalInTicks) {
        return new NmsParticleOptionsHandle(new VibrationParticleOption(toNmsPositionSource(destination), arrivalInTicks));
    }


    @Override
    @SuppressWarnings("unchecked")
    public ParticleTypeHandle typeByKey(String key) {
        Identifier id = Identifier.withDefaultNamespace(key);
        ParticleType<?> nms = BuiltInRegistries.PARTICLE_TYPE.getValue(id);
        if (nms == null) {
            throw new IllegalArgumentException("Unknown particle type: " + key);
        }
        return wrap(nms);
    }

    @SuppressWarnings("unchecked")
    private static <O extends ParticleOptions> NmsParticleTypeHandle<O> wrap(ParticleType<?> nms) {
        return new NmsParticleTypeHandle<>((ParticleType<O>) nms);
    }


    private static PositionSource toNmsPositionSource(Vibration.Destination dest) {
        if (dest instanceof Vibration.Destination.BlockDestination block) {
            return new BlockPositionSource(CraftLocation.toBlockPosition(block.getLocation()));
        } else if (dest instanceof Vibration.Destination.EntityDestination entityDest) {
            Entity nmsEntity = ((CraftEntity) entityDest.getEntity()).getHandle();
            return new EntityPositionSource(nmsEntity, nmsEntity.getEyeHeight());
        }
        throw new IllegalArgumentException("Unknown vibration destination " + dest);
    }
}