package dev.wyck.v26_2.wrapper.environment.particle.options;

import dev.wyck.annotations.WireFactory;
import dev.wyck.v26_2.wrapper.environment.particle.NmsParticleOptionsHandle;
import dev.wyck.v26_2.wrapper.environment.particle.NmsParticleTypeHandle;
import dev.wyck.wrapper.environment.particle.ParticleOptionsHandle;
import dev.wyck.wrapper.environment.particle.ParticleTypeHandle;
import dev.wyck.wrapper.environment.particle.options.ParticleOptionsFactory;
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
        return new NmsParticleOptionsHandle(new ItemParticleOption(nmsType, nmsStack.getItem()));
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
            return new BlockPositionSource(CraftLocation.toBlockPos(block.getLocation()));
        } else if (dest instanceof Vibration.Destination.EntityDestination entityDest) {
            Entity nmsEntity = ((CraftEntity) entityDest.getEntity()).getHandle();
            return new EntityPositionSource(nmsEntity, nmsEntity.getEyeHeight());
        }
        throw new IllegalArgumentException("Unknown vibration destination " + dest);
    }
}

/* TODO
@Override
    public @Nullable ParticleData decode(Object nmsParticleOptions) {
        ParticleOptions options = (ParticleOptions) nmsParticleOptions;

        if (options instanceof DustColorTransitionOptions dct) {
            return new DustTransitionParticle(dct.getFromColor(), dct.getToColor(), dct.getScale());
        } else if (options instanceof DustParticleOptions dust) {
            return new DustParticle(dust.getColor(), dust.getScale());
        } else if (options instanceof BlockParticleOption block) {
            Material material = CraftMagicNumbers.getMaterial(block.getState().getBlock());
            return new BlockParticle(material);
        } else if (options instanceof ItemParticleOption item) {
            ItemStack bukkitStack = CraftItemStack.asBukkitCopy(new net.minecraft.world.item.ItemStack(item.getItem()));
            return new ItemParticle(bukkitStack);
        } else if (options instanceof ColorParticleOption color) {
            int rgb = ((Math.round(color.getRed() * 255.0F) & 0xFF) << 16)
                | ((Math.round(color.getGreen() * 255.0F) & 0xFF) << 8)
                | (Math.round(color.getBlue() * 255.0F) & 0xFF);
            return new ColorParticle(rgb);
        } else if (options instanceof PowerParticleOption power) {
            return new PowerParticle(power.getPower());
        } else if (options instanceof SculkChargeParticleOptions(float roll)) {
            return new SculkChargeParticle(roll);
        } else if (options instanceof SpellParticleOption spell) {
            int rgb = ((Math.round(spell.getRed() * 255.0F) & 0xFF) << 16)
                | ((Math.round(spell.getGreen() * 255.0F) & 0xFF) << 8)
                | (Math.round(spell.getBlue() * 255.0F) & 0xFF);
            return new SpellParticle(rgb, spell.getPower());
        } else if (options instanceof TrailParticleOption trail) {
            Location target = CraftLocation.toBukkit(trail.target());
            return new TrailParticle(target, trail.color(), trail.duration());
        } else if (options instanceof VibrationParticleOption vib) {
            Vibration.Destination destination = toBukkitDestination(vib.getDestination());
            return new VibrationParticle(destination, vib.getArrivalInTicks());
        }

        return null; // simple / unmapped
    }

private static Vibration.Destination toBukkitDestination(PositionSource source) {
        if (source instanceof BlockPositionSource block) {
            BlockPos pos = block.getPosition(null).orElseThrow();
            return new Vibration.Destination.BlockDestination(CraftLocation.toBukkit(pos, null));
        } else if (source instanceof EntityPositionSource entity) {
            // EntityPositionSource holds an entity-or-uuid; no stable public getter to a live Entity
            // without a Level, so this branch can't be reconstructed standalone.
            throw new IllegalArgumentException("Cannot decode entity-based vibration destination without a world context");
        }
        throw new IllegalArgumentException("Unknown position source " + source);
    }
 */