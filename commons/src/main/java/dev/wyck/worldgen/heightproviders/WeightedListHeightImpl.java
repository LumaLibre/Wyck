package dev.wyck.worldgen.heightproviders;

import dev.wyck.util.WeightedList;
import dev.wyck.worldgen.heightproviders.HeightProvider;
import dev.wyck.worldgen.heightproviders.WeightedListHeight;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record WeightedListHeightImpl(
    @Override WeightedList<HeightProvider> distribution
) implements WeightedListHeight {

    @Override
    public Object toMinecraft() {
        net.minecraft.util.random.WeightedList.Builder<net.minecraft.world.level.levelgen.heightproviders.HeightProvider> builder = net.minecraft.util.random.WeightedList.builder();

        for (WeightedList.Weighted<HeightProvider> entry : distribution.unwrap()) {
            net.minecraft.world.level.levelgen.heightproviders.HeightProvider value = entry.value().asHandle();
            builder.add(value, entry.weight());
        }
        return new net.minecraft.world.level.levelgen.heightproviders.WeightedListHeight(builder.build());
    }
}
