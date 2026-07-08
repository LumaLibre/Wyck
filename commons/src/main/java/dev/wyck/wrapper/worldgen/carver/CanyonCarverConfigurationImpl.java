package dev.wyck.wrapper.worldgen.carver;

import dev.wyck.util.WorldgenConversions;
import dev.wyck.wrapper.worldgen.valueproviders.FloatProvider;
import dev.wyck.wrapper.worldgen.valueproviders.HeightProvider;
import dev.wyck.wrapper.worldgen.valueproviders.VerticalAnchor;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Set;

@NullMarked
@ApiStatus.Internal
public final class CanyonCarverConfigurationImpl extends CarverConfigurationImpl implements CanyonCarverConfiguration {

    private final FloatProvider verticalRotation;
    private final CanyonShapeConfiguration shape;

    public CanyonCarverConfigurationImpl(
        float probability,
        HeightProvider y,
        FloatProvider yScale,
        VerticalAnchor lavaLevel,
        CarverDebugSettings debugSettings,
        Set<Material> replaceable,
        FloatProvider verticalRotation,
        CanyonShapeConfiguration shape
    ) {
        super(probability, y, yScale, lavaLevel, debugSettings, replaceable);
        this.verticalRotation = verticalRotation;
        this.shape = shape;
    }

    @Override
    public FloatProvider verticalRotation() {
        return this.verticalRotation;
    }

    @Override
    public CanyonShapeConfiguration shape() {
        return this.shape;
    }

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration(
            probability,
            y.asHandle(),
            yScale.asHandle(),
            lavaLevel.asHandle(),
            debugSettings.asHandle(),
            WorldgenConversions.toBlockHolderSet(replaceable),
            verticalRotation.asHandle(),
            shape.asHandle()
        );
    }

    public record CanyonShapeConfigurationImpl(
        @Override FloatProvider distanceFor,
        @Override FloatProvider thickness,
        @Override int widthSmoothness,
        @Override FloatProvider horizontalRadiusFactor,
        @Override float verticalRadiusDefaultFactor,
        @Override float verticalRadiusCenterFactor
    ) implements CanyonShapeConfiguration {
        @Override
        public Object toMinecraft() {
            return new net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration.CanyonShapeConfiguration(
                distanceFor.asHandle(),
                thickness.asHandle(),
                widthSmoothness,
                horizontalRadiusFactor.asHandle(),
                verticalRadiusDefaultFactor,
                verticalRadiusCenterFactor
            );
        }
    }
}
