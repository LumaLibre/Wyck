package dev.wyck.worldgen.feature.featuresize;

import dev.wyck.worldgen.feature.featuresize.FeatureSize;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.OptionalInt;

@NullMarked
@ApiStatus.Internal
@SuppressWarnings("OptionalUsedAsFieldOrParameterType") // vanilla
public abstract class FeatureSizeImpl implements FeatureSize {

    protected final OptionalInt minClippedHeight;

    public FeatureSizeImpl(OptionalInt minClippedHeight) {
        this.minClippedHeight = minClippedHeight;
    }

    @Override
    public OptionalInt minClippedHeight() {
        return minClippedHeight;
    }

}
