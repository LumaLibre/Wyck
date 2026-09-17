package dev.wyck.worldgen.feature.configurations;

import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record EndGatewayFeatureImpl(
    @Override Optional<BlockVector> exit,
    @Override boolean exact
) implements EndGatewayFeature {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.EndGatewayFeature(
            exit.map(v -> new net.minecraft.core.BlockPos(v.getBlockX(), v.getBlockY(), v.getBlockZ())),
            exact
        );
    }
}
