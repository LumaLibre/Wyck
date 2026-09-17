package dev.wyck.worldgen.carver;

import dev.wyck.worldgen.heightproviders.HeightProvider;
import dev.wyck.worldgen.valueproviders.FloatProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record CanyonWorldCarverImpl(
    @Override float probability,
    @Override HeightProvider y,
    @Override FloatProvider verticalRotation,
    @Override Shape shape
) implements CanyonWorldCarver {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.carver.CanyonWorldCarver(
            probability, y.asHandle(), verticalRotation.asHandle(), shape.asHandle()
        );
    }

    public record ShapeImpl(
        @Override FloatProvider distanceFactor,
        @Override FloatProvider thickness,
        @Override int widthSmoothness,
        @Override FloatProvider horizontalRadiusFactor,
        @Override float verticalRadiusDefaultFactor,
        @Override float verticalRadiusCenterFactor,
        @Override FloatProvider yScale
    ) implements Shape {
        @Override
        public Object toMinecraft() {
            return new net.minecraft.world.level.levelgen.carver.CanyonWorldCarver.Shape(
                distanceFactor.asHandle(), thickness.asHandle(), widthSmoothness,
                horizontalRadiusFactor.asHandle(), verticalRadiusDefaultFactor,
                verticalRadiusCenterFactor, yScale.asHandle()
            );
        }
    }
}
