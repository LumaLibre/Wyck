package dev.wyck.worldgen.feature.configurations.end;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record EndSpikeImpl(
    @Override int centerX,
    @Override int centerZ,
    @Override int radius,
    @Override int height,
    @Override boolean guarded
) implements EndSpike {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.EndSpikeFeature.EndSpike(centerX, centerZ, radius, height, guarded);
    }
}