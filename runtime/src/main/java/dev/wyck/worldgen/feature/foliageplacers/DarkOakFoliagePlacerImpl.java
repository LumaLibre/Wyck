package dev.wyck.worldgen.feature.foliageplacers;

import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class DarkOakFoliagePlacerImpl extends FoliagePlacerImpl implements DarkOakFoliagePlacer {

    public DarkOakFoliagePlacerImpl(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer(
            radius.asHandle(),
            offset.asHandle()
        );
    }
}