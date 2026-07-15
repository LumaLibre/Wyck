package dev.wyck.worldgen.stateproviders;

import dev.wyck.worldgen.synth.NoiseParameters;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public final class NoiseProviderImpl extends NoiseBasedProviderImpl implements NoiseProvider {

    private final List<BlockData> states;

    public NoiseProviderImpl(long seed, NoiseParameters noise, float scale, List<BlockData> states) {
        super(seed, noise, scale);
        this.states = states;
    }

    @Override
    public List<BlockData> states() {
        return this.states;
    }

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider(
            this.seed,
            this.noise.asHandle(),
            this.scale,
            this.states.stream().map(it -> ((CraftBlockData) it).getState()).toList()
        );
    }
}
