package dev.wyck.worldgen.feature.configurations;

import dev.wyck.worldgen.feature.configurations.EndGatewayConfiguration;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record EndGatewayConfigurationImpl(
    @Override Optional<BlockVector> exit,
    @Override boolean exact
) implements EndGatewayConfiguration {
    @Override
    public Object toMinecraft() {
        return exit
            .map(v -> net.minecraft.world.level.levelgen.feature.configurations.EndGatewayConfiguration.knownExit(
                new net.minecraft.core.BlockPos(v.getBlockX(), v.getBlockY(), v.getBlockZ()), exact))
            .orElseGet(net.minecraft.world.level.levelgen.feature.configurations.EndGatewayConfiguration::delayedExitSearch);
    }
}