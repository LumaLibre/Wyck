package dev.wyck.worldgen.feature.foliageplacers;

import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class MegaPineFoliagePlacerImpl extends FoliagePlacerImpl implements MegaPineFoliagePlacer {

    private final IntProvider crownHeight;

    public MegaPineFoliagePlacerImpl(IntProvider radius, IntProvider offset, IntProvider crownHeight) {
        super(radius, offset);
        this.crownHeight = crownHeight;
    }

    @Override
    public IntProvider crownHeight() {
        return this.crownHeight;
    }

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.foliageplacers.MegaPineFoliagePlacer(
            radius.asHandle(),
            offset.asHandle(),
            crownHeight.asHandle()
        );
    }
}