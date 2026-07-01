package me.outspending.biomesapi.wrapper.worldgen.carver;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.registry.bootstrap.util.BootstrapSafeMinecraftRegistries;
import me.outspending.biomesapi.registry.bootstrap.util.DatapackPromotion;
import me.outspending.biomesapi.util.internal.InternalReflectUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public final class ConfiguredWorldCarverFactoryImpl implements ConfiguredWorldCarver.Factory {


    @Override
    public Object toNms(ConfiguredWorldCarver carver) {
        return switch (carver) {
            case ConfiguredWorldCarver.Reference reference -> resolveReference(reference);
            case ConfiguredWorldCarver.Custom custom -> buildCustom(custom);
        };
    }

    @Override
    @SuppressWarnings("unchecked")
    public ConfiguredWorldCarver fromMinecraft(Object nms) {
        Holder<net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?>> holder =
            (Holder<net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?>>) nms;

        Optional<ResourceKey<net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?>>> key =
            holder.unwrapKey();

        if (key.isPresent()) {
            return reverseReference(key.get());
        }

        return reverseCustom(holder.value());
    }

    private Holder.Reference<net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?>> resolveReference(ConfiguredWorldCarver.Reference reference) {
        HolderGetter<net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?>> getter =
            BootstrapSafeMinecraftRegistries.getter(Registries.CONFIGURED_CARVER);

        Identifier location =
            (Identifier) reference.key().resourceLocation();
        ResourceKey<net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?>> resourceKey =
            ResourceKey.create(Registries.CONFIGURED_CARVER, location);

        return getter.getOrThrow(resourceKey);
    }

    private Object buildCustom(ConfiguredWorldCarver.Custom custom) {
        if (DatapackPromotion.isReferenceMode()) {
            return DatapackPromotion.current().reference(custom, Registries.CONFIGURED_CARVER);
        }

        net.minecraft.world.level.levelgen.carver.CarverConfiguration nmsConfig =
            (net.minecraft.world.level.levelgen.carver.CarverConfiguration) custom.config().toMinecraft();

        net.minecraft.world.level.levelgen.carver.WorldCarver<?> carver = custom.type().toNms();
        net.minecraft.core.Holder<net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?>> holder = directHolder(carver, nmsConfig);

        if (DatapackPromotion.isCollectMode()) {
            DatapackPromotion.current().collectCarver(custom, holder.value());
        }
        return holder;
    }

    private ConfiguredWorldCarver reverseReference(ResourceKey<net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?>> key) {
        Identifier location = key.identifier();
        me.outspending.biomesapi.keys.ResourceKey wrapperKey = me.outspending.biomesapi.keys.ResourceKey.of(location.getNamespace(), location.getPath());
        return ConfiguredWorldCarver.reference(wrapperKey);
    }

    private ConfiguredWorldCarver reverseCustom(net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?> configured) {
        net.minecraft.world.level.levelgen.carver.WorldCarver<?> worldCarver =
            InternalReflectUtil.getFieldValue(configured, "worldCarver");

        WorldCarverType type = WorldCarverType.TRANSLATOR.fromNms(worldCarver);

        net.minecraft.world.level.levelgen.carver.CarverConfiguration nmsConfig = configured.config();
        CarverConfiguration abstractConfig = switch (nmsConfig) {
            case net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration _ -> CanyonCarverConfiguration.fromMinecraft(nmsConfig);
            case net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration _ -> CaveCarverConfiguration.fromMinecraft(nmsConfig);
            default -> throw new IllegalArgumentException("unsupported carver configuration " + nmsConfig.getClass().getSimpleName());
        };

        return new ConfiguredWorldCarver.Custom(type, abstractConfig);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private net.minecraft.core.Holder<net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?>> directHolder(net.minecraft.world.level.levelgen.carver.WorldCarver carver, net.minecraft.world.level.levelgen.carver.CarverConfiguration config) {
        net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver configured = new net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver(carver, config);
        return net.minecraft.core.Holder.direct(configured);
    }
}