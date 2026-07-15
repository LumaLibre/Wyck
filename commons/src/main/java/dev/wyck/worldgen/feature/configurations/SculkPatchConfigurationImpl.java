package dev.wyck.worldgen.feature.configurations;

import dev.wyck.worldgen.feature.configurations.SculkPatchConfiguration;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record SculkPatchConfigurationImpl(
    @Override int chargeCount,
    @Override int amountPerCharge,
    @Override int spreadAttempts,
    @Override int growthRounds,
    @Override int spreadRounds,
    @Override IntProvider extraRareGrowths,
    @Override float catalystChance
) implements SculkPatchConfiguration {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.configurations.SculkPatchConfiguration(
            chargeCount,
            amountPerCharge,
            spreadAttempts,
            growthRounds,
            spreadRounds,
            extraRareGrowths.asHandle(),
            catalystChance
        );
    }
}