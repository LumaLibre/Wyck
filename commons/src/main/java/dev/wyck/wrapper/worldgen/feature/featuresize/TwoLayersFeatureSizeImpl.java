package dev.wyck.wrapper.worldgen.feature.featuresize;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.OptionalInt;

@NullMarked
@ApiStatus.Internal
@SuppressWarnings("OptionalUsedAsFieldOrParameterType") // vanilla
public final class TwoLayersFeatureSizeImpl extends FeatureSizeImpl implements TwoLayersFeatureSize {

    private final int limit;
    private final int lowerSize;
    private final int upperSize;

    public TwoLayersFeatureSizeImpl(OptionalInt minClippedHeight, int limit, int lowerSize, int upperSize) {
        super(minClippedHeight);
        this.limit = limit;
        this.lowerSize = lowerSize;
        this.upperSize = upperSize;
    }

    @Override
    public int limit() {
        return limit;
    }

    @Override
    public int lowerSize() {
        return lowerSize;
    }

    @Override
    public int upperSize() {
        return upperSize;
    }

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize(
            limit,
            lowerSize,
            upperSize,
            minClippedHeight
        );
    }
}
