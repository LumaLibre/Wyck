package dev.wyck.wrapper.worldgen.feature.config;

import dev.wyck.annotations.WireFactory;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.ColumnFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.PointedDripstoneConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SculkPatchConfiguration;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@ApiStatus.Internal
public final class FeatureConfigurationFactoryImpl implements FeatureConfiguration.Factory {

    @Override
    public Object toNms(FeatureConfiguration configuration) {
        return switch (configuration) {
            case FeatureConfiguration.NoneConfiguration _ -> NoneFeatureConfiguration.INSTANCE;
            case FeatureConfiguration.ProbabilityConfiguration c -> new ProbabilityFeatureConfiguration(c.probability());
            case FeatureConfiguration.PointedDripstoneConfiguration c -> new PointedDripstoneConfiguration(
                c.chanceOfTallerDripstone(),
                c.chanceOfDirectionalSpread(),
                c.chanceOfSpreadRadius2(),
                c.chanceOfSpreadRadius3()
            );
            case FeatureConfiguration.CountConfiguration c -> new CountConfiguration(
                (IntProvider) c.count().toMinecraft()
            );
            case FeatureConfiguration.ColumnConfiguration c -> new ColumnFeatureConfiguration(
                (IntProvider) c.reach().toMinecraft(),
                (IntProvider) c.height().toMinecraft()
            );
            case FeatureConfiguration.SculkPatchConfiguration c -> new SculkPatchConfiguration(
                c.chargeCount(),
                c.amountPerCharge(),
                c.spreadAttempts(),
                c.growthRounds(),
                c.spreadRounds(),
                (IntProvider) c.extraRareGrowths().toMinecraft(),
                c.catalystChance()
            );
            case FeatureConfiguration.DripstoneClusterConfiguration c -> new DripstoneClusterConfiguration(
                c.floorToCeilingSearchRange(),
                (IntProvider) c.height().toMinecraft(),
                (IntProvider) c.radius().toMinecraft(),
                c.maxStalagmiteStalactiteHeightDiff(),
                c.heightDeviation(),
                (IntProvider) c.dripstoneBlockLayerThickness().toMinecraft(),
                (FloatProvider) c.density().toMinecraft(),
                (FloatProvider) c.wetness().toMinecraft(),
                c.chanceOfDripstoneColumnAtMaxDistanceFromCenter(),
                c.maxDistanceFromEdgeAffectingChanceOfDripstoneColumn(),
                c.maxDistanceFromCenterAffectingHeightBias()
            );
            case FeatureConfiguration.LargeDripstoneConfiguration c -> new LargeDripstoneConfiguration(
                c.floorToCeilingSearchRange(),
                (IntProvider) c.columnRadius().toMinecraft(),
                (FloatProvider) c.heightScale().toMinecraft(),
                c.maxColumnRadiusToCaveHeightRatio(),
                (FloatProvider) c.stalactiteBluntness().toMinecraft(),
                (FloatProvider) c.stalagmiteBluntness().toMinecraft(),
                (FloatProvider) c.windSpeed().toMinecraft(),
                c.minRadiusForWind(),
                c.minBluntnessForWind()
            );
            default -> throw new IllegalArgumentException("External feature configurations should implement their own #toMinecraft() method.");
        };
    }
}