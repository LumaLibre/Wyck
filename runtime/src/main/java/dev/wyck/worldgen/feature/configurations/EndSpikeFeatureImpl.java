package dev.wyck.worldgen.feature.configurations;

import dev.wyck.worldgen.feature.configurations.end.EndSpike;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@NullMarked
@ApiStatus.Internal
public record EndSpikeFeatureImpl(
    @Override boolean crystalInvulnerable,
    @Override List<EndSpike> spikes,
    @Override @Nullable BlockVector crystalBeamTarget
) implements EndSpikeFeature {
    @Override
    public Object toMinecraft() {
        List<net.minecraft.world.level.levelgen.feature.EndSpikeFeature.EndSpike> nmsSpikes = new ArrayList<>(spikes.size());
        for (EndSpike spike : spikes) {
            net.minecraft.world.level.levelgen.feature.EndSpikeFeature.EndSpike handle = spike.asHandle();
            nmsSpikes.add(handle);
        }

        java.util.Optional<net.minecraft.core.BlockPos> target = java.util.Optional.empty();
        if (crystalBeamTarget != null) {
            target = java.util.Optional.of(new net.minecraft.core.BlockPos(
                crystalBeamTarget.getBlockX(),
                crystalBeamTarget.getBlockY(),
                crystalBeamTarget.getBlockZ()
            ));
        }

        return new net.minecraft.world.level.levelgen.feature.EndSpikeFeature(nmsSpikes, crystalInvulnerable, target);
    }
}
