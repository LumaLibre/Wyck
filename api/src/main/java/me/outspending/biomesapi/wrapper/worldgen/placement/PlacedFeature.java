package me.outspending.biomesapi.wrapper.worldgen.placement;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.registry.BiomeResourceKey;
import me.outspending.biomesapi.wrapper.NmsHandle;
import me.outspending.biomesapi.wrapper.worldgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.Objects;

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
public sealed interface PlacedFeature extends NmsHandle permits PlacedFeature.Reference, PlacedFeature.Custom {

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.worldgen.placement.PlacedFeatureFactoryImpl");

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
    static PlacedFeature reference(BiomeResourceKey key) {
        return new Reference(key);
    }

    /**
     * Authors a placed feature from a configured feature and a list of modifiers.
     * @param feature the configured feature to place
     * @param placement the ordered placement modifiers
     * @return an authored placed feature
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static PlacedFeature of(ConfiguredFeature feature, List<PlacementModifier> placement) {
        return new Custom(feature, placement);
    }

    /**
     * Authors a placed feature from a configured feature and a list of modifiers.
     * @param feature the configured feature to place
     * @param placement the ordered placement modifiers
     * @return an authored placed feature
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static PlacedFeature of(ConfiguredFeature feature, PlacementModifier... placement) {
        return new Custom(feature, List.of(placement));
    }

    @Override
    @AsOf("2.3.0")
    default Object toMinecraft() {
        return WIRE.get().toNms(this);
    }

    /**
     * A reference to an already-registered placed feature.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    record Reference(BiomeResourceKey key) implements PlacedFeature {}

    /**
     * A placed feature authored from a configured feature and a modifier list.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    record Custom(ConfiguredFeature feature, List<PlacementModifier> placement) implements PlacedFeature {

        @AsOf("2.3.0")
        public Custom {
            Objects.requireNonNull(feature, "feature");
            placement = List.copyOf(placement);
        }
    }
}