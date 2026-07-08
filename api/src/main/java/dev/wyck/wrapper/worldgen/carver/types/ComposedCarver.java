package dev.wyck.wrapper.worldgen.carver.types;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.worldgen.carver.CarverConfiguration;
import dev.wyck.wrapper.worldgen.carver.ConfiguredWorldCarver;
import dev.wyck.wrapper.worldgen.carver.WorldCarverType;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
@AsOf("3.0.0")
public interface ComposedCarver extends ConfiguredWorldCarver {

    @ApiStatus.Internal
    ConstructWireProvider<ComposedCarver> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.carver.types.ComposedCarverImpl");

    @AsOf("3.0.0")
    WorldCarverType type();

    @AsOf("3.0.0")
    CarverConfiguration config();

    default Builder toBuilder() {
        return new Builder(this);
    }

    @AsOf("3.0.0")
    static ComposedCarver of(WorldCarverType type, CarverConfiguration config) {
        return WIRE.construct(type, config);
    }

    static Builder builder() {
        return new Builder();
    }

    // Friendlies

    @AsOf("3.0.0")
    static Builder cave() {
        return new Builder(WorldCarverType.CAVE);
    }

    @AsOf("3.0.0")
    static Builder netherCave() {
        return new Builder(WorldCarverType.NETHER_CAVE);
    }

    @AsOf("3.0.0")
    static Builder canyon() {
        return new Builder(WorldCarverType.CANYON);
    }

    @AsOf("3.0.0")
    final class Builder {
        private @Nullable WorldCarverType type;
        private @Nullable CarverConfiguration config;

        public Builder() {}

        public Builder(WorldCarverType type) {
            this.type = type;
        }

        public Builder(ComposedCarver carver) {
            this.type = carver.type();
            this.config = carver.config();
        }

        @AsOf("3.0.0")
        public Builder type(WorldCarverType type) {
            this.type = type;
            return this;
        }

        @AsOf("3.0.0")
        public Builder config(CarverConfiguration config) {
            this.config = config;
            return this;
        }

        @AsOf("3.0.0")
        public ComposedCarver build() {
            Preconditions.checkNotNull(type, "type must be set");
            Preconditions.checkNotNull(config, "config must be set");
            return of(type, config);
        }
    }
}
