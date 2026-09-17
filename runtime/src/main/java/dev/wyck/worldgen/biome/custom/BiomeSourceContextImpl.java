package dev.wyck.worldgen.biome.custom;

import dev.wyck.worldgen.climate.ClimatePoint;
import net.minecraft.world.level.biome.Climate;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class BiomeSourceContextImpl implements BiomeSourceContext {
    private final int quartX;
    private final int quartY;
    private final int quartZ;
    private final Climate.Sampler sampler;

    BiomeSourceContextImpl(int quartX, int quartY, int quartZ, Climate.Sampler sampler) {
        this.quartX = quartX;
        this.quartY = quartY;
        this.quartZ = quartZ;
        this.sampler = sampler;
    }

    @Override public int quartX() { return quartX; }
    @Override public int quartY() { return quartY; }
    @Override public int quartZ() { return quartZ; }

    @Override
    public ClimatePoint climate() {
        Climate.TargetPoint point = sampler.sample(quartX, quartY, quartZ);
        return ClimatePoint.of(
            (float) Climate.unquantizeCoord(point.temperature()),
            (float) Climate.unquantizeCoord(point.humidity()),
            (float) Climate.unquantizeCoord(point.continentalness()),
            (float) Climate.unquantizeCoord(point.erosion()),
            (float) Climate.unquantizeCoord(point.depth()),
            (float) Climate.unquantizeCoord(point.weirdness()),
            0.0F
        );
    }
}
