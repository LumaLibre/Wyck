package dev.wyck.worldgen.feature.foliageplacers;

import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class PineFoliagePlacerImpl extends FoliagePlacerImpl implements PineFoliagePlacer {

    private final IntProvider height;

    public PineFoliagePlacerImpl(IntProvider radius, IntProvider offset, IntProvider height) {
        super(radius, offset);
        this.height = height;
    }

    @Override
    public IntProvider height() {
        return this.height;
    }

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.foliageplacers.PineFoliagePlacer(
            radius.asHandle(),
            offset.asHandle(),
            height.asHandle()
        );
    }
}