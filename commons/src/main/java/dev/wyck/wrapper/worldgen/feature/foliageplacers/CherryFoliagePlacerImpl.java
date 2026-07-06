package dev.wyck.wrapper.worldgen.feature.foliageplacers;

import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class CherryFoliagePlacerImpl extends FoliagePlacerImpl implements CherryFoliagePlacer {

    private final IntProvider height;
    private final float wideBottomLayerHoleChance;
    private final float cornerHoleChance;
    private final float hangingLeavesChance;
    private final float hangingLeavesExtensionChance;

    public CherryFoliagePlacerImpl(
        IntProvider radius,
        IntProvider offset,
        IntProvider height,
        float wideBottomLayerHoleChance,
        float cornerHoleChance,
        float hangingLeavesChance,
        float hangingLeavesExtensionChance
    ) {
        super(radius, offset);
        this.height = height;
        this.wideBottomLayerHoleChance = wideBottomLayerHoleChance;
        this.cornerHoleChance = cornerHoleChance;
        this.hangingLeavesChance = hangingLeavesChance;
        this.hangingLeavesExtensionChance = hangingLeavesExtensionChance;
    }

    @Override
    public IntProvider height() {
        return this.height;
    }

    @Override
    public float wideBottomLayerHoleChance() {
        return this.wideBottomLayerHoleChance;
    }

    @Override
    public float cornerHoleChance() {
        return this.cornerHoleChance;
    }

    @Override
    public float hangingLeavesChance() {
        return this.hangingLeavesChance;
    }

    @Override
    public float hangingLeavesExtensionChance() {
        return this.hangingLeavesExtensionChance;
    }

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.foliageplacers.CherryFoliagePlacer(
            radius.asHandle(),
            offset.asHandle(),
            height.asHandle(),
            wideBottomLayerHoleChance,
            cornerHoleChance,
            hangingLeavesChance,
            hangingLeavesExtensionChance
        );
    }
}