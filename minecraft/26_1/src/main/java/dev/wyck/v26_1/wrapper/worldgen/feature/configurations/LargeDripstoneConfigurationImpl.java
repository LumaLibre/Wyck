package dev.wyck.v26_1.wrapper.worldgen.feature.configurations;

import dev.wyck.wrapper.worldgen.feature.configurations.LargeDripstoneConfiguration;
import dev.wyck.wrapper.worldgen.valueproviders.FloatProvider;
import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
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