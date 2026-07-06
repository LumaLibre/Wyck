package dev.wyck.wrapper.worldgen.feature.foliageplacers;

import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class FancyFoliagePlacerImpl extends FoliagePlacerImpl implements FancyFoliagePlacer {

    private final int height;

    public FancyFoliagePlacerImpl(IntProvider radius, IntProvider offset, int height) {
        super(radius, offset);
        this.height = height;
    }

    @Override
    public int height() {
        return this.height;
    }

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer(
            radius.asHandle(),
            offset.asHandle(),
            height
        );
    }
}