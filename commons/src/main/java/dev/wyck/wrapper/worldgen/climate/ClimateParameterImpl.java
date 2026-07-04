package dev.wyck.wrapper.worldgen.climate;

import net.minecraft.world.level.biome.Climate;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public class ClimateParameterImpl implements ClimateParameter {

    private final float min;
    private final float max;

    public ClimateParameterImpl(float min, float max) {
        // TODO: google Preconditions
        if (min > max) {
            throw new IllegalArgumentException("min > max: " + min + " " + max);
        }
        if (min < MIN_BOUNDARY || max > MAX_BOUNDARY) {
            throw new IllegalArgumentException("climate values must be within [-2.0, 2.0]: " + min + " " + max);
        }
        this.min = min;
        this.max = max;
    }


    @Override
    public float min() {
        return min;
    }

    @Override
    public float max() {
        return max;
    }

    @Override
    public Object toMinecraft() {
        return Climate.Parameter.span(this.min, this.max);
    }
}
