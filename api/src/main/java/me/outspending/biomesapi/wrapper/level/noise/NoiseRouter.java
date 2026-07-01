package me.outspending.biomesapi.wrapper.level.noise;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import me.outspending.biomesapi.wrapper.level.noise.function.DensityFunction;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.NullUnmarked;

/**
 * Wraps a {@code NoiseRouter} by referencing registered density functions by key.
 *
 * @version 2.4.0
 * @since 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
public interface NoiseRouter extends NmsHandle {

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.level.noise.NoiseRouterFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        NoiseRouter create(Slots slots);
    }

    /**
     * The density-function slots backing this router.
     *
     * @return the slots
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    Slots slots();

    /**
     * Creates a builder for a noise router.
     *
     * @return a new builder
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * The density-function slots backing this router.
     * @since 2.4.0
     */
    @NullUnmarked
    @AsOf("2.4.0")
    record Slots(
        DensityFunction barrier,
        DensityFunction fluidLevelFloodedness,
        DensityFunction fluidLevelSpread,
        DensityFunction lava,
        DensityFunction temperature,
        DensityFunction vegetation,
        DensityFunction continents,
        DensityFunction erosion,
        DensityFunction depth,
        DensityFunction ridges,
        DensityFunction preliminarySurfaceLevel,
        DensityFunction finalDensity,
        DensityFunction veinToggle,
        DensityFunction veinRidged,
        DensityFunction veinGap
    ) {}

    /**
     * Builder for a noise router. Every slot must be set before {@link #build()}.
     *
     * @since 2.4.0
     */
    @NullUnmarked
    @AsOf("2.4.0")
    final class Builder {

        private DensityFunction barrier;
        private DensityFunction fluidLevelFloodedness;
        private DensityFunction fluidLevelSpread;
        private DensityFunction lava;
        private DensityFunction temperature;
        private DensityFunction vegetation;
        private DensityFunction continents;
        private DensityFunction erosion;
        private DensityFunction depth;
        private DensityFunction ridges;
        private DensityFunction preliminarySurfaceLevel;
        private DensityFunction finalDensity;
        private DensityFunction veinToggle;
        private DensityFunction veinRidged;
        private DensityFunction veinGap;

        @AsOf("2.4.0")
        public Builder barrier(DensityFunction barrier) {
            this.barrier = barrier;
            return this;
        }

        @AsOf("2.4.0")
        public Builder fluidLevelFloodedness(DensityFunction fluidLevelFloodedness) {
            this.fluidLevelFloodedness = fluidLevelFloodedness;
            return this;
        }

        @AsOf("2.4.0")
        public Builder fluidLevelSpread(DensityFunction fluidLevelSpread) {
            this.fluidLevelSpread = fluidLevelSpread;
            return this;
        }

        @AsOf("2.4.0")
        public Builder lava(DensityFunction lava) {
            this.lava = lava;
            return this;
        }

        @AsOf("2.4.0")
        public Builder temperature(DensityFunction temperature) {
            this.temperature = temperature;
            return this;
        }

        @AsOf("2.4.0")
        public Builder vegetation(DensityFunction vegetation) {
            this.vegetation = vegetation;
            return this;
        }

        @AsOf("2.4.0")
        public Builder continents(DensityFunction continents) {
            this.continents = continents;
            return this;
        }

        @AsOf("2.4.0")
        public Builder erosion(DensityFunction erosion) {
            this.erosion = erosion;
            return this;
        }

        @AsOf("2.4.0")
        public Builder depth(DensityFunction depth) {
            this.depth = depth;
            return this;
        }

        @AsOf("2.4.0")
        public Builder ridges(DensityFunction ridges) {
            this.ridges = ridges;
            return this;
        }

        @AsOf("2.4.0")
        public Builder preliminarySurfaceLevel(DensityFunction preliminarySurfaceLevel) {
            this.preliminarySurfaceLevel = preliminarySurfaceLevel;
            return this;
        }

        @AsOf("2.4.0")
        public Builder finalDensity(DensityFunction finalDensity) {
            this.finalDensity = finalDensity;
            return this;
        }

        @AsOf("2.4.0")
        public Builder veinToggle(DensityFunction veinToggle) {
            this.veinToggle = veinToggle;
            return this;
        }

        @AsOf("2.4.0")
        public Builder veinRidged(DensityFunction veinRidged) {
            this.veinRidged = veinRidged;
            return this;
        }

        @AsOf("2.4.0")
        public Builder veinGap(DensityFunction veinGap) {
            this.veinGap = veinGap;
            return this;
        }

        @AsOf("2.4.0")
        public NoiseRouter build() {
            Slots slots = new Slots(
                this.barrier,
                this.fluidLevelFloodedness,
                this.fluidLevelSpread,
                this.lava,
                this.temperature,
                this.vegetation,
                this.continents,
                this.erosion,
                this.depth,
                this.ridges,
                this.preliminarySurfaceLevel,
                this.finalDensity,
                this.veinToggle,
                this.veinRidged,
                this.veinGap
            );
            return WIRE.get().create(slots);
        }
    }
}