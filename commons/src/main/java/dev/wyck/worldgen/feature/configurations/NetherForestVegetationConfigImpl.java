package dev.wyck.worldgen.feature.configurations;

import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record NetherForestVegetationConfigImpl(
    @Override BlockStateProvider stateProvider,
    @Override int spreadWidth,
    @Override int spreadHeight
) implements NetherForestVegetationConfig {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.configurations.NetherForestVegetationConfig(
            stateProvider.asHandle(),
            spreadWidth,
            spreadHeight
        );
    }
}