package dev.wyck.decode.worldgen.noise;

import dev.wyck.worldgen.function.DensityFunction;
import dev.wyck.worldgen.noise.AquiferSettings;
import dev.wyck.wrapper.decode.Decodable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class AquiferSettingsDecoder implements Decodable<AquiferSettings, net.minecraft.world.level.levelgen.Aquifer.Config> {

    @Override
    public AquiferSettings decode(net.minecraft.world.level.levelgen.Aquifer.Config config) {
        return AquiferSettings.of(
            DensityFunction.decode(config.barrierNoise()),
            DensityFunction.decode(config.fluidLevelFloodednessNoise()),
            DensityFunction.decode(config.fluidLevelSpreadNoise()),
            DensityFunction.decode(config.lavaNoise()),
            DensityFunction.decode(config.exclusion()),
            DensityFunction.decode(config.surfaceLevel())
        );
    }
}
