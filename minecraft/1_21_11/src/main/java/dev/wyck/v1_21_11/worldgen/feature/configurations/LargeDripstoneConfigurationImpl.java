package dev.wyck.v1_21_11.worldgen.feature.configurations;

import dev.wyck.worldgen.feature.configurations.LargeDripstoneConfiguration;
import dev.wyck.worldgen.valueproviders.FloatProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Set;

@NullMarked
@ApiStatus.Internal
public record LargeDripstoneConfigurationImpl(
    @Override Set<Material> replaceableBlocks,
    @Override int floorToCeilingSearchRange,
    @Override IntProvider columnRadius,
    @Override FloatProvider heightScale,
    @Override float maxColumnRadiusToCaveHeightRatio,
    @Override FloatProvider stalactiteBluntness,
    @Override FloatProvider stalagmiteBluntness,
    @Override FloatProvider windSpeed,
    @Override int minRadiusForWind,
    @Override float minBluntnessForWind
) implements LargeDripstoneConfiguration {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration(
            floorToCeilingSearchRange,
            columnRadius.asHandle(),
            heightScale.asHandle(),
            maxColumnRadiusToCaveHeightRatio,
            stalactiteBluntness.asHandle(),
            stalagmiteBluntness.asHandle(),
            windSpeed.asHandle(),
            minRadiusForWind,
            minBluntnessForWind
        );
    }
}