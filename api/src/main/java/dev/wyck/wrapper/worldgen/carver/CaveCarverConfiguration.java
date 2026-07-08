package dev.wyck.wrapper.worldgen.carver;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.worldgen.valueproviders.FloatProvider;
import dev.wyck.wrapper.worldgen.valueproviders.HeightProvider;
import dev.wyck.wrapper.worldgen.valueproviders.VerticalAnchor;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Set;

@NullMarked
@AsOf("2.3.0")
public interface CaveCarverConfiguration extends CarverConfiguration {

    @ApiStatus.Internal
    ConstructWireProvider<CaveCarverConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.carver.CaveCarverConfigurationImpl");

    @AsOf("2.3.0")
    FloatProvider horizontalRadiusMultiplier();

    @AsOf("2.3.0")
    FloatProvider verticalRadiusMultiplier();

    @AsOf("2.3.0")
    FloatProvider floorLevel();

    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    @AsOf("3.0.0")
    static CaveCarverConfiguration of(
        float probability,
        HeightProvider y,
        FloatProvider yScale,
        VerticalAnchor lavaLevel,
        CarverDebugSettings debugSettings,
        Set<Material> replaceable,
        FloatProvider horizontalRadiusMultiplier,
        FloatProvider verticalRadiusMultiplier,
        FloatProvider floorLevel
    ) {
        return WIRE.construct(probability, y, yScale, lavaLevel, debugSettings, replaceable, horizontalRadiusMultiplier, verticalRadiusMultiplier, floorLevel);
    }

    @AsOf("2.3.0")
    static Builder builder() {
        return new Builder();
    }

    final class Builder extends CarverConfigurationBuilder<CaveCarverConfiguration, Builder> {
        private @Nullable FloatProvider horizontalRadiusMultiplier;
        private @Nullable FloatProvider verticalRadiusMultiplier;
        private @Nullable FloatProvider floorLevel;

        public Builder() {}

        public Builder(CaveCarverConfiguration configuration) {
            super(configuration);
            this.horizontalRadiusMultiplier = configuration.horizontalRadiusMultiplier();
            this.verticalRadiusMultiplier = configuration.verticalRadiusMultiplier();
            this.floorLevel = configuration.floorLevel();
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

        @Override
        protected CaveCarverConfiguration create() {
            Preconditions.checkNotNull(horizontalRadiusMultiplier, "horizontalRadiusMultiplier must be set");
            Preconditions.checkNotNull(verticalRadiusMultiplier, "verticalRadiusMultiplier must be set");
            Preconditions.checkNotNull(floorLevel, "floorLevel must be set");
            //noinspection ConstantConditions
            return of(probability, y, yScale, lavaLevel, debugSettings, replaceable, horizontalRadiusMultiplier, verticalRadiusMultiplier, floorLevel);
        }
    }
}
