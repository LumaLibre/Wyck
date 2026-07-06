package dev.wyck.wrapper.worldgen.feature.foliageplacers;

import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class BushFoliagePlacerImpl extends FoliagePlacerImpl implements BushFoliagePlacer {

    private final int height;

    public BushFoliagePlacerImpl(IntProvider radius, IntProvider offset, int height) {
        super(radius, offset);
        this.height = height;
    }

    @Override
    public int height() {
        return this.height;
    }

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer(
            radius.asHandle(),
            offset.asHandle(),
            height
        );
    }
}