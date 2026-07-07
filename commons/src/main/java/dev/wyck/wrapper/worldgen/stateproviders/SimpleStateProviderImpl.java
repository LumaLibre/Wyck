package dev.wyck.wrapper.worldgen.stateproviders;

import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record SimpleStateProviderImpl(@Override BlockData state) implements SimpleStateProvider {
    @Override
    public Object toMinecraft() {
        return this.state;
    }
}
