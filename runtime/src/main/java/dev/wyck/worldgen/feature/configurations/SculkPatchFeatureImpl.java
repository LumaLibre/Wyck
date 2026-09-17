package dev.wyck.worldgen.feature.configurations;

import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record SculkPatchFeatureImpl(
    @Override int chargeCount,
    @Override int amountPerCharge,
    @Override int spreadAttempts,
    @Override int growthRounds,
    @Override int spreadRounds
) implements SculkPatchFeature {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.SculkPatchFeature(
            chargeCount,
            amountPerCharge,
            spreadAttempts,
            growthRounds,
            spreadRounds
        );
    }
}
