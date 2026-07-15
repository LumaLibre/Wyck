package dev.wyck.worldgen.feature.foliageplacers;

import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class AcaciaFoliagePlacerImpl extends FoliagePlacerImpl implements AcaciaFoliagePlacer {

    public AcaciaFoliagePlacerImpl(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer(
            radius.asHandle(),
            offset.asHandle()
        );
    }
}
