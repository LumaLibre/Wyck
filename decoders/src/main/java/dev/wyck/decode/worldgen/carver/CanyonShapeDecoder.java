package dev.wyck.decode.worldgen.carver;

import dev.wyck.worldgen.carver.CanyonWorldCarver;
import dev.wyck.worldgen.valueproviders.FloatProvider;
import dev.wyck.wrapper.decode.Decodable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class CanyonShapeDecoder implements Decodable<CanyonWorldCarver.Shape, net.minecraft.world.level.levelgen.carver.CanyonWorldCarver.Shape> {
    @Override
    public CanyonWorldCarver.Shape decode(net.minecraft.world.level.levelgen.carver.CanyonWorldCarver.Shape shape) {
        return CanyonWorldCarver.Shape.of(
            FloatProvider.decode(shape.distanceFactor()),
            FloatProvider.decode(shape.thickness()),
            shape.widthSmoothness(),
            FloatProvider.decode(shape.horizontalRadiusFactor()),
            shape.verticalRadiusDefaultFactor(),
            shape.verticalRadiusCenterFactor(),
            FloatProvider.decode(shape.yScale())
        );
    }
}
