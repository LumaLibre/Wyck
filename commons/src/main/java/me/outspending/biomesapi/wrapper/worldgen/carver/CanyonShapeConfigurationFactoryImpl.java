package me.outspending.biomesapi.wrapper.worldgen.carver;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import net.minecraft.util.valueproviders.FloatProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public final class CanyonShapeConfigurationFactoryImpl implements CanyonShapeConfiguration.Factory {

    @Override
    public @NotNull Object toNms(@NotNull CanyonShapeConfiguration configuration) {
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