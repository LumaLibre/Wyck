package dev.wyck.worldgen.noise;

import dev.wyck.worldgen.function.DensityFunction;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record AquiferSettingsImpl(
    @Override DensityFunction barrier,
    @Override DensityFunction fluidLevelFloodedness,
    @Override DensityFunction fluidLevelSpread,
    @Override DensityFunction lava,
    @Override DensityFunction exclusion,
    @Override DensityFunction surfaceLevel
) implements AquiferSettings {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.Aquifer.Config(
            barrier.asHandle(),
            fluidLevelFloodedness.asHandle(),
            fluidLevelSpread.asHandle(),
            lava.asHandle(),
            exclusion.asHandle(),
            surfaceLevel.asHandle()
        );
    }
}
