package dev.wyck.worldgen.feature.configurations.geode;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record GeodeLayerSettingsImpl(
    @Override double filling,
    @Override double innerLayer,
    @Override double middleLayer,
    @Override double outerLayer
) implements GeodeLayerSettings {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.GeodeLayerSettings(filling, innerLayer, middleLayer, outerLayer);
    }
}