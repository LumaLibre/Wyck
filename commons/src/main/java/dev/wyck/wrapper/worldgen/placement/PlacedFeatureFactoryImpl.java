package dev.wyck.wrapper.worldgen.placement;

import dev.wyck.annotations.WireFactory;
import dev.wyck.registry.bootstrap.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.registry.bootstrap.util.DatapackPromotion;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
@WireFactory
@ApiStatus.Internal
public final class PlacedFeatureFactoryImpl implements PlacedFeature.Factory {

    @Override
    public Object toMinecraft(PlacedFeature feature) {
        return switch (feature) {
            case PlacedFeature.Reference reference -> reference(reference);
            case PlacedFeature.Composed composed -> compose(composed);
        };
    }

    private Object reference(PlacedFeature.Reference reference) {
        HolderGetter<net.minecraft.world.level.levelgen.placement.PlacedFeature> getter =
            BootstrapSafeMinecraftRegistries.getter(Registries.PLACED_FEATURE);

        Identifier location = reference.key().identifier();
        ResourceKey<net.minecraft.world.level.levelgen.placement.PlacedFeature> resourceKey = ResourceKey.create(Registries.PLACED_FEATURE, location);

        return getter.getOrThrow(resourceKey);
    }

    private Object compose(PlacedFeature.Composed composed) {
        // datapack reference pass, return the promoted reference without rebuilding
        if (DatapackPromotion.isReferenceMode()) {
            return DatapackPromotion.current().reference(composed, Registries.PLACED_FEATURE);
        }

        Holder<ConfiguredFeature<?, ?>> featureHolder = composed.feature().asHandle();

        List<net.minecraft.world.level.levelgen.placement.PlacementModifier> placement = new ArrayList<>(composed.placement().size());
        for (PlacementModifier modifier : composed.placement()) {
            placement.add(modifier.asHandle());
        }

        net.minecraft.world.level.levelgen.placement.PlacedFeature placed = new net.minecraft.world.level.levelgen.placement.PlacedFeature(featureHolder, placement);

        // datapack collect pass, record the built value so it can be promoted to a file
        if (DatapackPromotion.isCollectMode()) {
            DatapackPromotion.current().collectPlacedFeature(composed, placed);
        }
        return net.minecraft.core.Holder.direct(placed);
    }
}