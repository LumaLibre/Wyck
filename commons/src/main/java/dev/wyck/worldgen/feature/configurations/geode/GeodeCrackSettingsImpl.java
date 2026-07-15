package dev.wyck.worldgen.feature.configurations.geode;

import dev.wyck.worldgen.feature.configurations.geode.GeodeCrackSettings;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record GeodeCrackSettingsImpl(
    @Override double generateCrackChance,
    @Override double baseCrackSize,
    @Override int crackPointOffset
) implements GeodeCrackSettings {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.GeodeCrackSettings(generateCrackChance, baseCrackSize, crackPointOffset);
    }
}