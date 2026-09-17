package dev.wyck.decode.worldgen.placement;

import dev.wyck.decode.Decoders;
import dev.wyck.worldgen.feature.Feature;
import dev.wyck.worldgen.placement.PlacementModifier;
import dev.wyck.worldgen.placement.PlacedFeature;
import dev.wyck.wrapper.decode.Decodable;
import net.minecraft.core.Holder;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class PlacedFeatureDecoder implements Decodable<PlacedFeature, Object> {

    @Override
    public PlacedFeature decode(Object minecraftObject) {
        if (minecraftObject instanceof Holder<?> holder && holder.unwrapKey().isPresent()) {
            return PlacedFeature.reference(Decoders.referenceKey(holder));
        }

        net.minecraft.world.level.levelgen.placement.PlacedFeature feature = Decoders.value(minecraftObject);
        return PlacedFeature.of(
            Feature.decode(feature.feature()),
            feature.placement().stream().map(PlacementModifier::decode).toList()
        );
    }
}
