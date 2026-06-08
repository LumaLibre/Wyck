package me.outspending.biomesapi.wrapper.worldgen.carver;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.registry.bootstrap.util.BootstrapSafeMinecraftRegistries;
import me.outspending.biomesapi.registry.bootstrap.util.DatapackPromotion;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

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

    private Object resolveReference(ConfiguredWorldCarver.Reference reference) {
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

        net.minecraft.core.Holder<net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?>> holder = switch (custom.type()) {
            case CAVE -> directHolder(WorldCarver.CAVE, nmsConfig);
            case NETHER_CAVE -> directHolder(WorldCarver.NETHER_CAVE, nmsConfig);
            case CANYON -> directHolder(WorldCarver.CANYON, nmsConfig);
        };

        if (DatapackPromotion.isCollectMode()) {
            DatapackPromotion.current().collectCarver(custom, holder.value());
        }
        return holder;
    }


    @SuppressWarnings({"rawtypes", "unchecked"})
    private net.minecraft.core.Holder<net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?>> directHolder(
        net.minecraft.world.level.levelgen.carver.WorldCarver carver,
        net.minecraft.world.level.levelgen.carver.CarverConfiguration config) {

        net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver configured =
            new net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver(carver, config);
        return net.minecraft.core.Holder.direct(configured);
    }
}