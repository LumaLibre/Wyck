package me.outspending.biomesapi.wrapper.worldgen.placement;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.wrapper.internal.NmsDecoder;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import me.outspending.biomesapi.wrapper.worldgen.feature.ConfiguredFeature;
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
public sealed interface PlacedFeature extends NmsHandle, Keyed permits PlacedFeature.Reference, PlacedFeature.Custom {

    Codec<PlacedFeature> CODEC = Codec.STRING.dispatch(
        "type",
        feature -> feature instanceof Reference ? "reference" : "custom",
        type -> switch (type) {
            case "reference" -> ResourceKey.CODEC.fieldOf("key")
                .xmap(PlacedFeature::reference, f -> ((Reference) f).key());
            case "custom" -> RecordCodecBuilder.mapCodec(i -> i.group(
                ConfiguredFeature.CODEC.fieldOf("feature").forGetter(f -> ((Custom) f).feature()),
                Codec.list(PlacementModifier.CODEC).fieldOf("placement").forGetter(f -> ((Custom) f).placement())
            ).apply(i, (feat, mods) -> new Custom(feat, mods)));
            default -> throw new IllegalStateException("unknown placed feature: " + type);
        }
    );

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.worldgen.placement.PlacedFeatureFactoryImpl");

    @ApiStatus.Internal
    interface Factory extends NmsDecoder<PlacedFeature> {
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

    @AsOf("2.4.0")
    static PlacedFeature fromMinecraft(Object nms) {
        return WIRE.get().fromMinecraft(nms);
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