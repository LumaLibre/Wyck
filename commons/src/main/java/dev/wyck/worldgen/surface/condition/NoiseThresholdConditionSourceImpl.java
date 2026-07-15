package dev.wyck.worldgen.surface.condition;

import dev.wyck.keys.ResourceKey;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record NoiseThresholdConditionSourceImpl(
    @Override ResourceKey noise,
    @Override double minThreshold,
    @Override double maxThreshold
) implements NoiseThresholdConditionSource {
    @Override
    public Object toMinecraft() {
        net.minecraft.resources.ResourceKey<net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters> key =
            net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.NOISE, this.noise.identifier());
        return net.minecraft.world.level.levelgen.SurfaceRules.noiseCondition2d(key, this.minThreshold, this.maxThreshold);
    }
}
