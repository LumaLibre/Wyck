package dev.wyck.worldgen.feature.types;

import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.Lazy;
import dev.wyck.util.DatapackPromotion;
import dev.wyck.worldgen.feature.FeatureType;
import dev.wyck.worldgen.feature.configurations.FeatureConfiguration;
import net.kyori.adventure.key.Key;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.Feature;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.NoSuchElementException;
import java.util.Optional;

// TODO: fixup imports
@NullMarked
@ApiStatus.Internal
public record ComposedFeatureImpl(
    @Override Optional<ResourceKey> resourceKey,
    @Override FeatureType type,
    @Override FeatureConfiguration config
) implements ComposedFeature {

    private static final Lazy<WyckRegistry> REGISTRY = WyckRegistry.lazy(RegistryId.FEATURE);

    @Override
    public Object toMinecraft() {
        if (DatapackPromotion.isReferenceMode()) {
            return DatapackPromotion.current().reference(this, net.minecraft.core.registries.Registries.FEATURE);
        }
        Feature feature = config.asHandle();
        feature = adaptAmbiguousFeature(feature);
        if (DatapackPromotion.isCollectMode()) {
            DatapackPromotion.current().collectConfiguredFeature(this, feature);
        }
        return Holder.direct(feature);
    }

    private Feature adaptAmbiguousFeature(Feature feature) {
        if (config instanceof dev.wyck.worldgen.feature.configurations.HugeRedMushroomFeature mushroom
            && type == FeatureType.HUGE_BROWN_MUSHROOM) {
            return new net.minecraft.world.level.levelgen.feature.HugeBrownMushroomFeature(
                net.minecraft.core.Holder.direct(mushroom.capProvider().asHandle()),
                net.minecraft.core.Holder.direct(mushroom.stemProvider().asHandle()),
                mushroom.foliageRadius(),
                mushroom.canPlaceOn().asHandle()
            );
        }
        if (config instanceof dev.wyck.worldgen.feature.configurations.OreFeature ore
            && type == FeatureType.SCATTERED_ORE) {
            net.minecraft.world.level.levelgen.feature.OreFeature base = (net.minecraft.world.level.levelgen.feature.OreFeature) feature;
            return new net.minecraft.world.level.levelgen.feature.ScatteredOreFeature(
                base.targetStates(), base.size(), base.discardChanceOnAirExposure()
            );
        }
        if (config instanceof dev.wyck.worldgen.feature.configurations.VegetationPatchFeature vegetation
            && type == FeatureType.WATERLOGGED_VEGETATION_PATCH) {
            return new net.minecraft.world.level.levelgen.feature.WaterloggedVegetationPatchFeature(
                vegetation.replaceable().asHolderSet(),
                vegetation.groundState().asHandle(),
                vegetation.vegetationFeature().asHandle(),
                vegetation.surface().toNms(net.minecraft.world.level.levelgen.placement.CaveSurface.class),
                vegetation.depth().asHandle(),
                vegetation.extraBottomBlockChance(),
                vegetation.verticalRange(),
                vegetation.vegetationChance(),
                vegetation.xzRadius().asHandle(),
                vegetation.extraEdgeColumnChance()
            );
        }
        return feature;
    }

    @Override
    public ComposedFeature register() {
        ResourceKey key = resourceKey.orElseThrow(() -> new NoSuchElementException("Cannot register a configured feature without a resource key."));
        REGISTRY.get().register(key, this.toMinecraft());
        return this;
    }

    @Override
    public Key key() {
        return resourceKey.orElseThrow();
    }
}
