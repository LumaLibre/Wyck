package dev.wyck.wrapper.worldgen.placement;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.WireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.internal.Wrapper;
import dev.wyck.wrapper.worldgen.feature.ConfiguredFeature;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Wraps Minecraft's PlacedFeature, a configured feature paired with the ordered
 * list of placement modifiers applied to it. A placed feature may either reference
 * one already present in the {@code PLACED_FEATURE} registry, or be authored from a
 * configured feature plus a list of modifiers.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public sealed interface PlacedFeature extends Wrapper, Keyed permits PlacedFeature.Reference, PlacedFeature.Custom {

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("dev.wyck.wrapper.worldgen.placement.PlacedFeatureFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        Object toNms(PlacedFeature feature);
    }

    /**
     * References a placed feature already registered under the given key.
     * @param key the registry key of the placed feature
     * @return a reference to the registered placed feature
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static PlacedFeature reference(ResourceKey key) {
        return new Reference(key);
    }

    /**
     * Authors a placed feature from a configured feature and a list of modifiers.
     * @param feature the configured feature to place
     * @param placements the ordered placement modifiers
     * @return an authored placed feature
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static PlacedFeature of(ConfiguredFeature feature, List<PlacementModifier> placements) {
        return new Custom(feature, placements);
    }

    /**
     * Authors a placed feature from a configured feature and a list of modifiers.
     * @param feature the configured feature to place
     * @param placements the ordered placement modifiers
     * @return an authored placed feature
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static PlacedFeature of(ConfiguredFeature feature, PlacementModifier... placements) {
        return new Custom(feature, List.of(placements));
    }

    @Override
    @AsOf("2.3.0")
    default Object toMinecraft() {
        return WIRE.get().toNms(this);
    }

    @AsOf("2.3.0")
    static FeatureBuilder builder() {
        return new FeatureBuilder();
    }

    /**
     * A reference to an already-registered placed feature.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    record Reference(ResourceKey key) implements PlacedFeature {}

    /**
     * A placed feature authored from a configured feature and a modifier list.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    record Custom(ConfiguredFeature feature, List<PlacementModifier> placement) implements PlacedFeature {

        @AsOf("2.3.0")
        public Custom {
            Preconditions.checkNotNull(feature, "feature");
            placement = List.copyOf(placement);
        }

        @Override
        public Key key() {
            return this.feature.key();
        }
    }

    final class FeatureBuilder {
        private @Nullable ConfiguredFeature feature;
        private final List<PlacementModifier> placements = new ArrayList<>();


        public FeatureBuilder feature(ConfiguredFeature feature) {
            this.feature = feature;
            return this;
        }

        public FeatureBuilder modifier(PlacementModifier placement) {
            this.placements.add(placement);
            return this;
        }

        public FeatureBuilder placements(List<PlacementModifier> placements) {
            this.placements.addAll(placements);
            return this;
        }

        public FeatureBuilder placements(PlacementModifier... placements) {
            this.placements.addAll(List.of(placements));
            return this;
        }

        public PlacedFeature build() {
            Preconditions.checkNotNull(feature, "feature must be set");
            return PlacedFeature.of(feature, placements);
        }
    }
}