package dev.wyck.wrapper.worldgen.carver.types;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.worldgen.carver.ConfiguredWorldCarver;
import dev.wyck.wrapper.worldgen.carver.custom.CustomCarver;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
@AsOf("3.0.0")
public interface ComposedCustomCarver<C> extends ConfiguredWorldCarver {

    @ApiStatus.Internal
    ConstructWireProvider<ComposedCustomCarver<?>> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.carver.types.ComposedCustomCarverImpl");

    @AsOf("3.0.0")
    CustomCarver<C> carver();

    @AsOf("3.0.0")
    C config();

    @AsOf("3.0.0")
    default Builder<C> toBuilder() {
        return new Builder<>(this);
    }

    @AsOf("3.0.0")
    @SuppressWarnings("unchecked")
    static <C> ComposedCustomCarver<C> of(CustomCarver<C> carver, C config) {
        return (ComposedCustomCarver<C>) WIRE.construct(carver, config);
    }

    @AsOf("3.0.0")
    static <C> Builder<C> builder() {
        return new Builder<>();
    }

    @AsOf("3.0.0")
    final class Builder<C> {
        private @Nullable CustomCarver<C> carver;
        private @Nullable C config;

        public Builder() {}

        public Builder(ComposedCustomCarver<C> carver) {
            this.carver = carver.carver();
            this.config = carver.config();
        }

        @AsOf("3.0.0")
        public Builder<C> carver(CustomCarver<C> carver) {
            this.carver = carver;
            return this;
        }

        @AsOf("3.0.0")
        public Builder<C> config(C config) {
            this.config = config;
            return this;
        }

        @AsOf("3.0.0")
        public ComposedCustomCarver<C> build() {
            Preconditions.checkNotNull(carver, "carver must be set");
            Preconditions.checkNotNull(config, "config must be set");
            return of(carver, config);
        }
    }
}
