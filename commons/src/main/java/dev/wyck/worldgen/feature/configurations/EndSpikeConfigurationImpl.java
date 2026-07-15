package dev.wyck.worldgen.feature.configurations;

import dev.wyck.worldgen.feature.configurations.EndSpikeConfiguration;
import dev.wyck.worldgen.feature.configurations.end.EndSpike;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@NullMarked
@ApiStatus.Internal
public record EndSpikeConfigurationImpl(
    @Override boolean crystalInvulnerable,
    @Override List<EndSpike> spikes,
    @Override @Nullable BlockVector crystalBeamTarget
) implements EndSpikeConfiguration {
    @Override
    public Object toMinecraft() {
        List<net.minecraft.world.level.levelgen.feature.EndSpikeFeature.EndSpike> nmsSpikes = new ArrayList<>(spikes.size());
        for (EndSpike spike : spikes) {
            net.minecraft.world.level.levelgen.feature.EndSpikeFeature.EndSpike handle = spike.asHandle();
            nmsSpikes.add(handle);
        }

        net.minecraft.core.BlockPos target = null;
        if (crystalBeamTarget != null) {
            target = new net.minecraft.core.BlockPos(
                crystalBeamTarget.getBlockX(),
                crystalBeamTarget.getBlockY(),
                crystalBeamTarget.getBlockZ()
            );
        }

        return new net.minecraft.world.level.levelgen.feature.configurations.EndSpikeConfiguration(crystalInvulnerable, nmsSpikes, target);
    }
}