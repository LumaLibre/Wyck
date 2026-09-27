package dev.wyck.v1_21_11.decode.worldgen.feature;

import dev.wyck.worldgen.feature.configurations.end.EndSpike;
import dev.wyck.wrapper.decode.Decodable;
import net.minecraft.world.level.levelgen.feature.SpikeFeature;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class EndSpikeDecoder implements Decodable<EndSpike, SpikeFeature.EndSpike> {

    @Override
    public EndSpike decode(SpikeFeature.EndSpike spike) {
        return EndSpike.of(spike.getCenterX(), spike.getCenterZ(), spike.getRadius(), spike.getHeight(), spike.isGuarded());
    }
}
