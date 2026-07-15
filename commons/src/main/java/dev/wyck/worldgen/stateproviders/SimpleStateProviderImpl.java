package dev.wyck.worldgen.stateproviders;

import dev.wyck.worldgen.stateproviders.SimpleStateProvider;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record SimpleStateProviderImpl(@Override BlockData state) implements SimpleStateProvider {
    @Override
    public Object toMinecraft() {
        CraftBlockData data = (CraftBlockData) state;
        return net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider.simple(
            data.getState()
        );
    }
}
