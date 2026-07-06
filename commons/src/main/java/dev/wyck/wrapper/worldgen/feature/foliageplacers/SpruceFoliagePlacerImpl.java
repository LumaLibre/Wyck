package dev.wyck.wrapper.worldgen.feature.foliageplacers;

import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class SpruceFoliagePlacerImpl extends FoliagePlacerImpl implements SpruceFoliagePlacer {

    private final IntProvider trunkHeight;

    public SpruceFoliagePlacerImpl(IntProvider radius, IntProvider offset, IntProvider trunkHeight) {
        super(radius, offset);
        this.trunkHeight = trunkHeight;
    }

    @Override
    public IntProvider trunkHeight() {
        return this.trunkHeight;
    }

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer(
            radius.asHandle(),
            offset.asHandle(),
            trunkHeight.asHandle()
        );
    }
}