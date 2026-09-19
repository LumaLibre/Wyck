package dev.wyck.worldgen.stateproviders;

import dev.wyck.util.WorldgenConversions;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record RotatedBlockProviderImpl(
    @Override BlockStateProvider state,
    @Override Optional<BlockFace> direction
) implements RotatedBlockProvider {
    @Override
    public Object toMinecraft() {
        return net.minecraft.core.Holder.direct(
            new net.minecraft.world.level.levelgen.feature.stateproviders.RotatedBlockProvider(
                state.asHandle(),
                direction.map(WorldgenConversions::toNmsDirection)
            )
        );
    }
}
