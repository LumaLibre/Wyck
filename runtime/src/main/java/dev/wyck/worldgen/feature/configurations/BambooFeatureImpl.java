package dev.wyck.worldgen.feature.configurations;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public class BambooFeatureImpl implements BambooFeature {

    protected final float probability;

    public BambooFeatureImpl(float probability) {
        this.probability = probability;
    }

    @Override
    public float probability() {
        return this.probability;
    }

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.BambooFeature(
            this.probability
        );
    }
}
