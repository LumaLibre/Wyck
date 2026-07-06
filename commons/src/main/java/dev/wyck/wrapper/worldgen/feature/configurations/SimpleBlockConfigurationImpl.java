package dev.wyck.wrapper.worldgen.feature.configurations;

import dev.wyck.wrapper.worldgen.stateproviders.BlockStateProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record SimpleBlockConfigurationImpl(
    @Override BlockStateProvider toPlace,
    @Override boolean scheduleTick
) implements SimpleBlockConfiguration {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration(
            toPlace.asHandle(),
            scheduleTick
        );
    }
}