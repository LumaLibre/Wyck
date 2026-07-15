package dev.wyck.worldgen.feature.configurations;

import dev.wyck.worldgen.feature.configurations.ColumnFeatureConfiguration;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record ColumnFeatureConfigurationImpl(
    @Override IntProvider reach,
    @Override IntProvider height
) implements ColumnFeatureConfiguration {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.configurations.ColumnFeatureConfiguration(
            reach.asHandle(),
            height.asHandle()
        );
    }
}