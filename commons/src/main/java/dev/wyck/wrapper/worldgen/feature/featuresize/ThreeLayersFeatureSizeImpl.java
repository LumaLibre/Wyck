package dev.wyck.wrapper.worldgen.feature.featuresize;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.OptionalInt;

@NullMarked
@ApiStatus.Internal
@SuppressWarnings("OptionalUsedAsFieldOrParameterType") // vanilla
public final class ThreeLayersFeatureSizeImpl extends FeatureSizeImpl implements ThreeLayersFeatureSize {

    private final int limit;
    private final int upperLimit;
    private final int lowerSize;
    private final int middleSize;
    private final int upperSize;

    public ThreeLayersFeatureSizeImpl(OptionalInt minClippedHeight, int limit, int upperLimit, int lowerSize, int middleSize, int upperSize) {
        super(minClippedHeight);
        this.limit = limit;
        this.upperLimit = upperLimit;
        this.lowerSize = lowerSize;
        this.middleSize = middleSize;
        this.upperSize = upperSize;
    }

    @Override
    public int limit() {
        return limit;
    }

    @Override
    public int upperLimit() {
        return upperLimit;
    }

    @Override
    public int lowerSize() {
        return lowerSize;
    }

    @Override
    public int middleSize() {
        return middleSize;
    }

    @Override
    public int upperSize() {
        return upperSize;
    }

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize(
            limit,
            upperLimit,
            lowerSize,
            middleSize,
            upperSize,
            minClippedHeight
        );
    }
}
