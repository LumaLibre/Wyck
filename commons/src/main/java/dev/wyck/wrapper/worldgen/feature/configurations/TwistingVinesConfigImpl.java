package dev.wyck.wrapper.worldgen.feature.configurations;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record TwistingVinesConfigImpl(
    @Override int spreadWidth,
    @Override int spreadHeight,
    @Override int maxHeight
) implements TwistingVinesConfig {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.configurations.TwistingVinesConfig(
            spreadWidth,
            spreadHeight,
            maxHeight
        );
    }
}