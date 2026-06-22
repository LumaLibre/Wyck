package me.outspending.biomesapi.wrapper.level.noise;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.NullUnmarked;

// TODO: Add support for authoring custom density functions
/**
 * Wraps a {@code NoiseRouter} by referencing registered density functions by key. Each slot points at
 * a density function in the {@code worldgen/density_function} registry.
 *
 * @apiNote These references registered density functions only. Authoring density functions from
 * scratch is not yet supported, point at vanilla functions (e.g. {@code minecraft:overworld/final_density})
 * or your own datapack-registered ones.
 *
 * @version 2.4.0
 * @since 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
@ApiStatus.Experimental
public interface LevelNoiseRouter extends NmsHandle {

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.level.noise.LevelNoiseRouterFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        LevelNoiseRouter create(Slots slots);
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
        ResourceKey barrier,
        ResourceKey fluidLevelFloodedness,
        ResourceKey fluidLevelSpread,
        ResourceKey lava,
        ResourceKey temperature,
        ResourceKey vegetation,
        ResourceKey continents,
        ResourceKey erosion,
        ResourceKey depth,
        ResourceKey ridges,
        ResourceKey preliminarySurfaceLevel,
        ResourceKey finalDensity,
        ResourceKey veinToggle,
        ResourceKey veinRidged,
        ResourceKey veinGap
    ) {}

    /**
     * Builder for a noise router. Every slot must be set before {@link #build()}.
     *
     * @since 2.4.0
     */
    @NullUnmarked
    @AsOf("2.4.0")
    final class Builder {

        private ResourceKey barrier;
        private ResourceKey fluidLevelFloodedness;
        private ResourceKey fluidLevelSpread;
        private ResourceKey lava;
        private ResourceKey temperature;
        private ResourceKey vegetation;
        private ResourceKey continents;
        private ResourceKey erosion;
        private ResourceKey depth;
        private ResourceKey ridges;
        private ResourceKey preliminarySurfaceLevel;
        private ResourceKey finalDensity;
        private ResourceKey veinToggle;
        private ResourceKey veinRidged;
        private ResourceKey veinGap;

        @AsOf("2.4.0")
        public Builder barrier(ResourceKey barrier) {
            this.barrier = barrier;
            return this;
        }

        @AsOf("2.4.0")
        public Builder fluidLevelFloodedness(ResourceKey fluidLevelFloodedness) {
            this.fluidLevelFloodedness = fluidLevelFloodedness;
            return this;
        }

        @AsOf("2.4.0")
        public Builder fluidLevelSpread(ResourceKey fluidLevelSpread) {
            this.fluidLevelSpread = fluidLevelSpread;
            return this;
        }

        @AsOf("2.4.0")
        public Builder lava(ResourceKey lava) {
            this.lava = lava;
            return this;
        }

        @AsOf("2.4.0")
        public Builder temperature(ResourceKey temperature) {
            this.temperature = temperature;
            return this;
        }

        @AsOf("2.4.0")
        public Builder vegetation(ResourceKey vegetation) {
            this.vegetation = vegetation;
            return this;
        }

        @AsOf("2.4.0")
        public Builder continents(ResourceKey continents) {
            this.continents = continents;
            return this;
        }

        @AsOf("2.4.0")
        public Builder erosion(ResourceKey erosion) {
            this.erosion = erosion;
            return this;
        }

        @AsOf("2.4.0")
        public Builder depth(ResourceKey depth) {
            this.depth = depth;
            return this;
        }

        @AsOf("2.4.0")
        public Builder ridges(ResourceKey ridges) {
            this.ridges = ridges;
            return this;
        }

        @AsOf("2.4.0")
        public Builder preliminarySurfaceLevel(ResourceKey preliminarySurfaceLevel) {
            this.preliminarySurfaceLevel = preliminarySurfaceLevel;
            return this;
        }

        @AsOf("2.4.0")
        public Builder finalDensity(ResourceKey finalDensity) {
            this.finalDensity = finalDensity;
            return this;
        }

        @AsOf("2.4.0")
        public Builder veinToggle(ResourceKey veinToggle) {
            this.veinToggle = veinToggle;
            return this;
        }

        @AsOf("2.4.0")
        public Builder veinRidged(ResourceKey veinRidged) {
            this.veinRidged = veinRidged;
            return this;
        }

        @AsOf("2.4.0")
        public Builder veinGap(ResourceKey veinGap) {
            this.veinGap = veinGap;
            return this;
        }

        @AsOf("2.4.0")
        public LevelNoiseRouter build() {
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