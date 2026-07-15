package dev.wyck.worldgen.feature.configurations;

import dev.wyck.worldgen.feature.configurations.GeodeConfiguration;
import dev.wyck.worldgen.feature.configurations.geode.GeodeBlockSettings;
import dev.wyck.worldgen.feature.configurations.geode.GeodeCrackSettings;
import dev.wyck.worldgen.feature.configurations.geode.GeodeLayerSettings;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record GeodeConfigurationImpl(
    @Override GeodeBlockSettings geodeBlockSettings,
    @Override GeodeLayerSettings geodeLayerSettings,
    @Override GeodeCrackSettings geodeCrackSettings,
    @Override double usePotentialPlacementsChance,
    @Override double useAlternateLayer0Chance,
    @Override boolean placementsRequireLayer0Alternate,
    @Override IntProvider outerWallDistance,
    @Override IntProvider distributionPoints,
    @Override IntProvider pointOffset,
    @Override int minGenOffset,
    @Override int maxGenOffset,
    @Override double noiseMultiplier,
    @Override int invalidBlocksThreshold
) implements GeodeConfiguration {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration(
            geodeBlockSettings.asHandle(),
            geodeLayerSettings.asHandle(),
            geodeCrackSettings.asHandle(),
            usePotentialPlacementsChance,
            useAlternateLayer0Chance,
            placementsRequireLayer0Alternate,
            outerWallDistance.asHandle(),
            distributionPoints.asHandle(),
            pointOffset.asHandle(),
            minGenOffset,
            maxGenOffset,
            noiseMultiplier,
            invalidBlocksThreshold
        );
    }
}