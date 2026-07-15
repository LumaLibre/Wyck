package dev.wyck.worldgen.climate;

import dev.wyck.worldgen.climate.ClimateParameter;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record ClimateParameterImpl(@Override float min, @Override float max) implements ClimateParameter {

    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.biome.Climate.Parameter.span(this.min, this.max);
    }
}
