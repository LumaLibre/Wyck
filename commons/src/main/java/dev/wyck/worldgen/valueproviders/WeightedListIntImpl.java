package dev.wyck.worldgen.valueproviders;

import dev.wyck.util.WeightedList;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record WeightedListIntImpl(
    @Override WeightedList<IntProvider> distribution
) implements WeightedListInt {

    @Override
    public Object toMinecraft() {
        net.minecraft.util.random.WeightedList.Builder<net.minecraft.util.valueproviders.IntProvider> builder = net.minecraft.util.random.WeightedList.builder();

        for (WeightedList.Weighted<IntProvider> entry : distribution.unwrap()) {
            net.minecraft.util.valueproviders.IntProvider value = entry.value().asHandle();
            builder.add(value, entry.weight());
        }
        return new net.minecraft.util.valueproviders.WeightedListInt(builder.build());
    }
}
