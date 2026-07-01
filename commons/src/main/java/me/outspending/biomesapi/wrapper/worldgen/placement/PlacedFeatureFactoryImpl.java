package me.outspending.biomesapi.wrapper.worldgen.placement;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.registry.bootstrap.util.BootstrapSafeMinecraftRegistries;
import me.outspending.biomesapi.registry.bootstrap.util.DatapackPromotion;
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
import java.util.Optional;

@NullMarked
@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public final class PlacedFeatureFactoryImpl implements PlacedFeature.Factory {

    @Override
    public Object toNms(PlacedFeature feature) {
        return switch (feature) {
            case PlacedFeature.Reference reference -> resolveReference(reference);
            case PlacedFeature.Custom custom -> buildCustom(custom);
        };
    }

    @Override
    public PlacedFeature fromMinecraft(Object nms) {
        Holder<net.minecraft.world.level.levelgen.placement.PlacedFeature> holder =
            (Holder<net.minecraft.world.level.levelgen.placement.PlacedFeature>) nms;

        Optional<ResourceKey<net.minecraft.world.level.levelgen.placement.PlacedFeature>> key = holder.unwrapKey();
        if (key.isPresent()) {
            return reverseReference(key.get());
        }

        return reverseCustom(holder.value());
    }

    private Object resolveReference(PlacedFeature.Reference reference) {
        HolderGetter<net.minecraft.world.level.levelgen.placement.PlacedFeature> getter =
            BootstrapSafeMinecraftRegistries.getter(Registries.PLACED_FEATURE);

        Identifier location = (Identifier) reference.key().resourceLocation();
        ResourceKey<net.minecraft.world.level.levelgen.placement.PlacedFeature> resourceKey = ResourceKey.create(Registries.PLACED_FEATURE, location);

        return getter.getOrThrow(resourceKey);
    }

    @SuppressWarnings("unchecked")
    private Object buildCustom(PlacedFeature.Custom custom) {
        // datapack reference pass, return the promoted reference without rebuilding
        if (DatapackPromotion.isReferenceMode()) {
            return DatapackPromotion.current().reference(custom, Registries.PLACED_FEATURE);
        }

        Holder<ConfiguredFeature<?, ?>> featureHolder = (Holder<ConfiguredFeature<?, ?>>) custom.feature().toMinecraft();

        List<net.minecraft.world.level.levelgen.placement.PlacementModifier> placement = new ArrayList<>(custom.placement().size());
        for (PlacementModifier modifier : custom.placement()) {
            placement.add((net.minecraft.world.level.levelgen.placement.PlacementModifier) modifier.toMinecraft());
        }

        net.minecraft.world.level.levelgen.placement.PlacedFeature placed =
            new net.minecraft.world.level.levelgen.placement.PlacedFeature(featureHolder, placement);

        // datapack collect pass, record the built value so it can be promoted to a file
        if (DatapackPromotion.isCollectMode()) {
            DatapackPromotion.current().collectPlacedFeature(custom, placed);
        }
        return net.minecraft.core.Holder.direct(placed);
    }

    private PlacedFeature reverseReference(ResourceKey<net.minecraft.world.level.levelgen.placement.PlacedFeature> key) {
        Identifier location = key.identifier();
        me.outspending.biomesapi.keys.ResourceKey wrapperKey = me.outspending.biomesapi.keys.ResourceKey.of(location.getNamespace(), location.getPath());
        return PlacedFeature.reference(wrapperKey);
    }

    private PlacedFeature reverseCustom(net.minecraft.world.level.levelgen.placement.PlacedFeature placed) {
        me.outspending.biomesapi.wrapper.worldgen.feature.ConfiguredFeature feature =
            me.outspending.biomesapi.wrapper.worldgen.feature.ConfiguredFeature.fromMinecraft(placed.feature());

        List<PlacementModifier> modifiers = new ArrayList<>(placed.placement().size());
        for (net.minecraft.world.level.levelgen.placement.PlacementModifier nmsModifier : placed.placement()) {
            modifiers.add(PlacementModifier.fromMinecraft(nmsModifier));
        }

        return new PlacedFeature.Custom(feature, modifiers);
    }
}