package dev.wyck.worldgen.stateproviders;

import dev.wyck.worldgen.synth.NoiseParameters;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public final class NoiseThresholdProviderImpl extends NoiseBasedProviderImpl implements NoiseThresholdProvider {

    private final float threshold;
    private final float highChance;
    private final BlockData defaultState;
    private final List<BlockData> lowStates;
    private final List<BlockData> highStates;

    public NoiseThresholdProviderImpl(long seed, NoiseParameters noise, float scale, float threshold, float highChance, BlockData defaultState, List<BlockData> lowStates, List<BlockData> highStates) {
        super(seed, noise, scale);
        this.threshold = threshold;
        this.highChance = highChance;
        this.defaultState = defaultState;
        this.lowStates = lowStates;
        this.highStates = highStates;
    }

    @Override
    public float threshold() {
        return this.threshold;
    }

    @Override
    public float highChance() {
        return this.highChance;
    }

    @Override
    public BlockData defaultState() {
        return this.defaultState;
    }

    @Override
    public List<BlockData> lowStates() {
        return this.lowStates;
    }

    @Override
    public List<BlockData> highStates() {
        return this.highStates;
    }

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.stateproviders.NoiseThresholdProvider(
            this.seed,
            this.noise.asHandle(),
            this.scale,
            this.threshold,
            this.highChance,
            ((CraftBlockData) this.defaultState).getState(),
            this.lowStates.stream().map(it -> ((CraftBlockData) it).getState()).toList(),
            this.highStates.stream().map(it -> ((CraftBlockData) it).getState()).toList()
        );
    }
}
