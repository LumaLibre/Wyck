package me.outspending.biomesapi.wrapper.worldgen.carver;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.FloatProvider;
import net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public final class CanyonShapeConfigurationFactoryImpl implements CanyonShapeConfiguration.Factory {

    @Override
    public Object toNms(CanyonShapeConfiguration configuration) {
        net.minecraft.util.valueproviders.FloatProvider distanceFactor = (net.minecraft.util.valueproviders.FloatProvider) configuration.distanceFactor().toMinecraft();
        net.minecraft.util.valueproviders.FloatProvider thickness = (net.minecraft.util.valueproviders.FloatProvider) configuration.thickness().toMinecraft();
        net.minecraft.util.valueproviders.FloatProvider horizontalRadiusFactor = (net.minecraft.util.valueproviders.FloatProvider) configuration.horizontalRadiusFactor().toMinecraft();

        return new net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration.CanyonShapeConfiguration(
            distanceFactor,
            thickness,
            configuration.widthSmoothness(),
            horizontalRadiusFactor,
            configuration.verticalRadiusDefaultFactor(),
            configuration.verticalRadiusCenterFactor()
        );
    }

    @Override
    public CanyonShapeConfiguration fromMinecraft(Object nms) {
        CanyonCarverConfiguration.CanyonShapeConfiguration nmsConfig = (CanyonCarverConfiguration.CanyonShapeConfiguration) nms;
        return new CanyonShapeConfiguration(
            FloatProvider.fromMinecraft(nmsConfig.distanceFactor),
            FloatProvider.fromMinecraft(nmsConfig.thickness),
            nmsConfig.widthSmoothness,
            FloatProvider.fromMinecraft(nmsConfig.horizontalRadiusFactor),
            nmsConfig.verticalRadiusDefaultFactor,
            nmsConfig.verticalRadiusCenterFactor
        );
    }
}