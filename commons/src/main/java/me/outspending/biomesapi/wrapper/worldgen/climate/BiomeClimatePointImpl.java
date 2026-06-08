package me.outspending.biomesapi.wrapper.worldgen.climate;

import me.outspending.biomesapi.annotations.AsOf;
import net.minecraft.world.level.biome.Climate;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("2.3.0")
@ApiStatus.Internal
public final class BiomeClimatePointImpl implements BiomeClimatePoint {

    private final BiomeParameter temperature;
    private final BiomeParameter humidity;
    private final BiomeParameter continentalness;
    private final BiomeParameter erosion;
    private final BiomeParameter depth;
    private final BiomeParameter weirdness;
    private final float offset;

    public BiomeClimatePointImpl(BiomeParameter temperature, BiomeParameter humidity, BiomeParameter continentalness, BiomeParameter erosion, BiomeParameter depth, BiomeParameter weirdness, float offset) {
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
    public BiomeParameter temperature() {
        return this.temperature;
    }

    @Override
    public BiomeParameter humidity() {
        return this.humidity;
    }

    @Override
    public BiomeParameter continentalness() {
        return this.continentalness;
    }

    @Override
    public BiomeParameter erosion() {
        return this.erosion;
    }

    @Override
    public BiomeParameter depth() {
        return this.depth;
    }

    @Override
    public BiomeParameter weirdness() {
        return this.weirdness;
    }

    @Override
    public float offset() {
        return this.offset;
    }

    @Override
    public Object toMinecraft() {
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