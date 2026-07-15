package dev.wyck.worldgen.climate;

import net.minecraft.world.level.biome.Climate;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record ClimatePointImpl(
    @Override ClimateParameter temperature,
    @Override ClimateParameter humidity,
    @Override ClimateParameter continentalness,
    @Override ClimateParameter erosion,
    @Override ClimateParameter depth,
    @Override ClimateParameter weirdness,
    @Override float offset
) implements ClimatePoint {

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