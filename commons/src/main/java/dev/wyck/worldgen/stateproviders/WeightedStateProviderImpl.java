package dev.wyck.worldgen.stateproviders;

import dev.wyck.util.WeightedList;
import dev.wyck.worldgen.stateproviders.WeightedStateProvider;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record WeightedStateProviderImpl(@Override WeightedList<BlockData> entries) implements WeightedStateProvider {
    @Override
    public Object toMinecraft() {
        net.minecraft.util.random.WeightedList.Builder<net.minecraft.world.level.block.state.BlockState> builder =
            net.minecraft.util.random.WeightedList.builder();

        for (WeightedList.Weighted<BlockData> entry : this.entries.unwrap()) {
            CraftBlockData data = (CraftBlockData) entry.value();
            builder.add(data.getState(), entry.weight());
        }
        return new net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider(builder.build());
    }
}
