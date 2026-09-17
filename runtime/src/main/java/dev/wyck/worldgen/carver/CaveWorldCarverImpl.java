package dev.wyck.worldgen.carver;

import dev.wyck.worldgen.heightproviders.HeightProvider;
import dev.wyck.worldgen.valueproviders.FloatProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record CaveWorldCarverImpl(
    @Override float probability,
    @Override HeightProvider y,
    @Override IntProvider count,
    @Override FloatProvider thickness,
    @Override boolean weirdThicknessBias,
    @Override FloatProvider roomVerticalRadiusMultiplier,
    @Override FloatProvider horizontalRadiusMultiplier,
    @Override FloatProvider verticalRadiusMultiplier,
    @Override FloatProvider startVerticalRadiusMultiplier,
    @Override FloatProvider floorLevel
) implements CaveWorldCarver {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.carver.CaveWorldCarver(
            probability, y.asHandle(), count.asHandle(), thickness.asHandle(), weirdThicknessBias,
            roomVerticalRadiusMultiplier.asHandle(), horizontalRadiusMultiplier.asHandle(),
            verticalRadiusMultiplier.asHandle(), startVerticalRadiusMultiplier.asHandle(), floorLevel.asHandle()
        );
    }
}
