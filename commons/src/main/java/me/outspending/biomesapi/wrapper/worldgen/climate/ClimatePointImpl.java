package me.outspending.biomesapi.wrapper.worldgen.climate;

import me.outspending.biomesapi.annotations.AsOf;
import net.minecraft.world.level.biome.Climate;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("2.3.0")
@ApiStatus.Internal
public final class ClimatePointImpl implements ClimatePoint {

    private final ClimateParameter temperature;
    private final ClimateParameter humidity;
    private final ClimateParameter continentalness;
    private final ClimateParameter erosion;
    private final ClimateParameter depth;
    private final ClimateParameter weirdness;
    private final float offset;

    public ClimatePointImpl(ClimateParameter temperature, ClimateParameter humidity, ClimateParameter continentalness, ClimateParameter erosion, ClimateParameter depth, ClimateParameter weirdness, float offset) {
        // TODO: google Preconditions
        if (offset < 0.0f || offset > 1.0f) {
            throw new IllegalArgumentException("offset must be within [0.0, 1.0]: " + offset);
        }
        this.temperature = temperature;
        this.humidity = humidity;
        this.continentalness = continentalness;
        this.erosion = erosion;
        this.depth = depth;
        this.weirdness = weirdness;
        this.offset = offset;
    }

    @Override
    public ClimateParameter temperature() {
        return this.temperature;
    }

    @Override
    public ClimateParameter humidity() {
        return this.humidity;
    }

    @Override
    public ClimateParameter continentalness() {
        return this.continentalness;
    }

    @Override
    public ClimateParameter erosion() {
        return this.erosion;
    }

    @Override
    public ClimateParameter depth() {
        return this.depth;
    }

    @Override
    public ClimateParameter weirdness() {
        return this.weirdness;
    }

    @Override
    public float offset() {
        return this.offset;
    }

    @Override
    public Climate.ParameterPoint toMinecraft() {
        return Climate.parameters(
            (Climate.Parameter) this.temperature.toMinecraft(),
            (Climate.Parameter) this.humidity.toMinecraft(),
            (Climate.Parameter) this.continentalness.toMinecraft(),
            (Climate.Parameter) this.erosion.toMinecraft(),
            (Climate.Parameter) this.depth.toMinecraft(),
            (Climate.Parameter) this.weirdness.toMinecraft(),
            this.offset
        );
    }
}