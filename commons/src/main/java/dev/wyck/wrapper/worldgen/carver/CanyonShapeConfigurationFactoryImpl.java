package dev.wyck.wrapper.worldgen.carver;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.WireFactory;
import net.minecraft.util.valueproviders.FloatProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public final class CanyonShapeConfigurationFactoryImpl implements CanyonShapeConfiguration.Factory {

    @Override
    public Object toNms(CanyonShapeConfiguration configuration) {
        FloatProvider distanceFactor = (FloatProvider) configuration.distanceFactor().toMinecraft();
        FloatProvider thickness = (FloatProvider) configuration.thickness().toMinecraft();
        FloatProvider horizontalRadiusFactor = (FloatProvider) configuration.horizontalRadiusFactor().toMinecraft();

        return new net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration.CanyonShapeConfiguration(
                distanceFactor,
                thickness,
                configuration.widthSmoothness(),
                horizontalRadiusFactor,
                configuration.verticalRadiusDefaultFactor(),
                configuration.verticalRadiusCenterFactor()
        );
    }
}