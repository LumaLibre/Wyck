package dev.wyck.worldgen.stateproviders;

import dev.wyck.worldgen.stateproviders.NoiseBasedProvider;
import dev.wyck.worldgen.synth.NoiseParameters;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public abstract class NoiseBasedProviderImpl implements NoiseBasedProvider {

    protected final long seed;
    protected final NoiseParameters noise;
    protected final float scale;

    public NoiseBasedProviderImpl(long seed, NoiseParameters noise, float scale) {
        this.seed = seed;
        this.noise = noise;
        this.scale = scale;
    }

    @Override
    public long seed() {
        return this.seed;
    }

    @Override
    public NoiseParameters noise() {
        return this.noise;
    }

    @Override
    public float scale() {
        return this.scale;
    }

}
