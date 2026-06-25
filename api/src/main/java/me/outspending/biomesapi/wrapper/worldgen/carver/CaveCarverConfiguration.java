package me.outspending.biomesapi.wrapper.worldgen.carver;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.serialization.Codecs;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.FloatProvider;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.HeightProvider;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.VerticalAnchor;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Collection;
import java.util.List;

/**
 * Wraps Minecraft's CaveCarverConfiguration, the configuration consumed by both
 * the CAVE and NETHER_CAVE carvers.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public record CaveCarverConfiguration(
    float probability,
    HeightProvider y,
    FloatProvider yScale,
    VerticalAnchor lavaLevel,
    CarverDebugSettings debugSettings,
    Collection<Material> replaceable,
    FloatProvider horizontalRadiusMultiplier,
    FloatProvider verticalRadiusMultiplier,
    FloatProvider floorLevel
) implements CarverConfiguration {

    public static final Codec<CaveCarverConfiguration> CODEC = RecordCodecBuilder.create(i -> i.group(
        Codec.FLOAT.fieldOf("probability").forGetter(CaveCarverConfiguration::probability),
        HeightProvider.CODEC.fieldOf("y").forGetter(CaveCarverConfiguration::y),
        FloatProvider.CODEC.fieldOf("y_scale").forGetter(CaveCarverConfiguration::yScale),
        VerticalAnchor.CODEC.fieldOf("lava_level").forGetter(CaveCarverConfiguration::lavaLevel),
        CarverDebugSettings.CODEC.fieldOf("debug_settings").forGetter(CaveCarverConfiguration::debugSettings),
        Codec.list(Codecs.MATERIAL_CODEC).fieldOf("replaceable").xmap(java.util.ArrayList::new, List::copyOf).forGetter(c -> new java.util.ArrayList<>(c.replaceable())),
        FloatProvider.CODEC.fieldOf("horizontal_radius_multiplier").forGetter(CaveCarverConfiguration::horizontalRadiusMultiplier),
        FloatProvider.CODEC.fieldOf("vertical_radius_multiplier").forGetter(CaveCarverConfiguration::verticalRadiusMultiplier),
        FloatProvider.CODEC.fieldOf("floor_level").forGetter(CaveCarverConfiguration::floorLevel)
    ).apply(i, CaveCarverConfiguration::new));

    @ApiStatus.Internal
    private static final WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.worldgen.carver.CaveCarverConfigurationFactoryImpl");

    @ApiStatus.Internal
    protected interface Factory {
        Object toNms(CaveCarverConfiguration configuration);
    }

    @AsOf("2.3.0")
    public CaveCarverConfiguration {
        Preconditions.checkNotNull(y, "y");
        Preconditions.checkNotNull(yScale, "yScale");
        Preconditions.checkNotNull(lavaLevel, "lavaLevel");
        Preconditions.checkNotNull(debugSettings, "debugSettings");
        Preconditions.checkNotNull(horizontalRadiusMultiplier, "horizontalRadiusMultiplier");
        Preconditions.checkNotNull(verticalRadiusMultiplier, "verticalRadiusMultiplier");
        Preconditions.checkNotNull(floorLevel, "floorLevel");
        replaceable = List.copyOf(replaceable);
    }

    @AsOf("2.3.0")
    public static Builder builder() {
        return new Builder();
    }

    @Override
    @AsOf("2.3.0")
    public Object toMinecraft() {
        return WIRE.get().toNms(this);
    }

    /**
     * A builder for creating a CaveCarverConfiguration. The debug settings default
     * to {@link CarverDebugSettings#defaultSettings()} if left unset.
     * @since 2.3.0
     * @version 2.3.0
     * @author Jsinco
     */
    @AsOf("2.3.0")
    public static final class Builder {

        private Float probability;
        private HeightProvider y;
        private FloatProvider yScale;
        private VerticalAnchor lavaLevel;
        private CarverDebugSettings debugSettings;
        private Collection<Material> replaceable;
        private FloatProvider horizontalRadiusMultiplier;
        private FloatProvider verticalRadiusMultiplier;
        private FloatProvider floorLevel;

        @AsOf("2.3.0")
        public Builder() {
            this.debugSettings = CarverDebugSettings.defaultSettings();
        }

        @AsOf("2.3.0")
        public Builder probability(float probability) {
            this.probability = probability;
            return this;
        }

        @AsOf("2.3.0")
        public Builder y(HeightProvider y) {
            this.y = y;
            return this;
        }

        @AsOf("2.3.0")
        public Builder yScale(FloatProvider yScale) {
            this.yScale = yScale;
            return this;
        }

        @AsOf("2.3.0")
        public Builder lavaLevel(VerticalAnchor lavaLevel) {
            this.lavaLevel = lavaLevel;
            return this;
        }

        @AsOf("2.3.0")
        public Builder debugSettings(CarverDebugSettings debugSettings) {
            this.debugSettings = debugSettings;
            return this;
        }

        @AsOf("2.3.0")
        public Builder replaceable(Collection<Material> replaceable) {
            this.replaceable = replaceable;
            return this;
        }

        @AsOf("2.3.0")
        public Builder horizontalRadiusMultiplier(FloatProvider horizontalRadiusMultiplier) {
            this.horizontalRadiusMultiplier = horizontalRadiusMultiplier;
            return this;
        }

        @AsOf("2.3.0")
        public Builder verticalRadiusMultiplier(FloatProvider verticalRadiusMultiplier) {
            this.verticalRadiusMultiplier = verticalRadiusMultiplier;
            return this;
        }

        @AsOf("2.3.0")
        public Builder floorLevel(FloatProvider floorLevel) {
            this.floorLevel = floorLevel;
            return this;
        }

        @AsOf("2.3.0")
        public CaveCarverConfiguration build() {
            Preconditions.checkState(this.probability != null, "probability must be set");
            Preconditions.checkState(this.y != null, "y must be set");
            Preconditions.checkState(this.yScale != null, "yScale must be set");
            Preconditions.checkState(this.lavaLevel != null, "lavaLevel must be set");
            Preconditions.checkState(this.replaceable != null, "replaceable must be set");
            Preconditions.checkState(this.horizontalRadiusMultiplier != null, "horizontalRadiusMultiplier must be set");
            Preconditions.checkState(this.verticalRadiusMultiplier != null, "verticalRadiusMultiplier must be set");
            Preconditions.checkState(this.floorLevel != null, "floorLevel must be set");
            Preconditions.checkArgument(
                    this.floorLevel.minValue() >= -1.0F && this.floorLevel.maxValue() <= 1.0F,
                    "floorLevel must stay within [-1, 1]"
            );

            return new CaveCarverConfiguration(
                    this.probability,
                    this.y,
                    this.yScale,
                    this.lavaLevel,
                    this.debugSettings,
                    this.replaceable,
                    this.horizontalRadiusMultiplier,
                    this.verticalRadiusMultiplier,
                    this.floorLevel
            );
        }
    }
}