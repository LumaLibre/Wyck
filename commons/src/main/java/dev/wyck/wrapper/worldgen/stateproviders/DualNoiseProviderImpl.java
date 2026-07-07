package dev.wyck.wrapper.worldgen.stateproviders;

import dev.wyck.util.InclusiveRange;
import dev.wyck.wrapper.worldgen.synth.NoiseParameters;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public final class DualNoiseProviderImpl extends NoiseBasedProviderImpl implements DualNoiseProvider {

    private final InclusiveRange<Integer> variety;
    private final NoiseParameters slowNoise;
    private final float slowScale;
    private final List<BlockData> states;

    public DualNoiseProviderImpl(long seed, NoiseParameters noise, float scale, InclusiveRange<Integer> variety, NoiseParameters slowNoise, float slowScale, List<BlockData> states) {
        super(seed, noise, scale);
        this.variety = variety;
        this.slowNoise = slowNoise;
        this.slowScale = slowScale;
        this.states = states;
    }

    @Override
    public InclusiveRange<Integer> variety() {
        return this.variety;
    }

    @Override
    public NoiseParameters slowNoise() {
        return this.slowNoise;
    }

    @Override
    public float slowScale() {
        return this.slowScale;
    }

    @Override
    public List<BlockData> states() {
        return this.states;
    }

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.stateproviders.DualNoiseProvider(
            variety.asHandle(),
            slowNoise.asHandle(),
            slowScale,
            seed,
            noise.asHandle(),
            scale,
            states.stream().map(it -> ((CraftBlockData) it).getState()).toList()
        );
    }
}
