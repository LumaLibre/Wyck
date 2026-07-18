package dev.wyck.v26_1.worldgen.feature.configurations;

import dev.wyck.worldgen.feature.configurations.PointedDripstoneConfiguration;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record PointedDripstoneConfigurationImpl(
    @Override float chanceOfTallerDripstone,
    @Override float chanceOfDirectionalSpread,
    @Override float chanceOfSpreadRadius2,
    @Override float chanceOfSpreadRadius3
) implements PointedDripstoneConfiguration {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.configurations.PointedDripstoneConfiguration(
            chanceOfTallerDripstone,
            chanceOfDirectionalSpread,
            chanceOfSpreadRadius2,
            chanceOfSpreadRadius3
        );
    }
}