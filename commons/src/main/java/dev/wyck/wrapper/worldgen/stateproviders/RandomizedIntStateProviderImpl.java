package dev.wyck.wrapper.worldgen.stateproviders;

import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record RandomizedIntStateProviderImpl(
    @Override BlockStateProvider source,
    @Override String property,
    @Override IntProvider values
) implements RandomizedIntStateProvider {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider(
            source.asHandle(),
            property,
            values.asHandle()
        );
    }
}
